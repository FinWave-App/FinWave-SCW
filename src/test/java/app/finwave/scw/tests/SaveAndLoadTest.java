package app.finwave.scw.tests;

import org.junit.jupiter.api.Test;
import app.finwave.scw.RootConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SaveAndLoadTest {

    public static final String TEST_1 = "Hello, world!";
    public static final String TEST_2 = "Goodbye, world!";
    public static final String[] TEST_3 = new String[] {
            "One", "Two", "And", "Otter"
    };

    public void save() throws IOException {
        File file = new File("test.conf");

        RootConfig rootConfig = new RootConfig(file, true);

        rootConfig.subNode("test").setValue("hi", TEST_1);
        rootConfig.subNode("test2").setValue("bye", TEST_2);
        rootConfig.subNode("test2").setValue("number", UUID.randomUUID());
        rootConfig.subNode("test3").setAs(TEST_3);
        rootConfig.subNode("test4").subNode("subnode").setAs(new TestClass());
    }

    public void load() throws IOException {
        File file = new File("test.conf");

        RootConfig rootConfig = new RootConfig(file);
        rootConfig.load();

        String[] loadedTest3 = rootConfig
                .subNode("test3")
                .getAs(TEST_3.getClass())
                .orElse(null);

        assertNotNull(loadedTest3);
        assertArrayEquals(TEST_3, loadedTest3);

        TestClass loadedTestClass = rootConfig
                .subNode("test4")
                .subNode("subnode")
                .getAs(TestClass.class)
                .orElse(null);
        assertNotNull(loadedTestClass);
        assertEquals("Hi", loadedTestClass.mew);
        assertArrayEquals(new String[]{"A1", "A2", "A3"}, loadedTestClass.array);

        Optional<String> loadedMew = rootConfig
                .subNode("test4")
                .subNode("subnode")
                .getString("mew");

        assertTrue(loadedMew.isPresent());
        assertEquals("Hi", loadedMew.get());
    }

    @Test()
    public void test() throws IOException {
        save();
        load();
    }

    static class TestClass {
        String[] array = new String[] {
                "A1", "A2", "A3"
        };
        String mew = "Hi";
        String moo = "Bye";
    }
}
