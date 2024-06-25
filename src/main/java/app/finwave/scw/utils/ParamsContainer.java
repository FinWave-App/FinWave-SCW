package app.finwave.scw.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class ParamsContainer {
    protected Map<String, String> rawParams;

    public ParamsContainer(Map<String, String> rawParams) {
        this.rawParams = rawParams;
    }

    protected Optional<String> rawValue(String key) {
        return Optional.ofNullable(rawParams.getOrDefault(key, null));
    }

    protected <T> Optional<T> mappedValue(String key, Function<? super String, T> mapper) {
        try {
            return rawValue(key).map(mapper);
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> getString(String key) {
        return rawValue(key);
    }

    public Optional<Integer> getInt(String key) {
        return mappedValue(key, Integer::parseInt);
    }

    public Optional<Float> getFloat(String key) {
        return mappedValue(key, Float::parseFloat);
    }

    public Optional<Double> getDouble(String key) {
        return mappedValue(key, Double::parseDouble);
    }

    public Optional<UUID> getUuid(String key) {
        return mappedValue(key, UUID::fromString);
    }

    public Optional<BigDecimal> getBigDecimal(String key) {
        return mappedValue(key, BigDecimal::new);
    }

    public Optional<BigInteger> getBigInteger(String key) {
        return mappedValue(key, BigInteger::new);
    }
}
