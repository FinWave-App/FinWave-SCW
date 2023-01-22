package su.knst.scw.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import su.knst.scw.RootConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

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

        System.out.println(Arrays.toString(rootConfig
                .subNode("test3")
                .getAs(TEST_3.getClass())
                .orElse(null)
        ));

        System.out.println(rootConfig
                .subNode("test4")
                .subNode("subnode")
                .getAs(TestClass.class)
                .map(r -> r.mew).orElse("null")
        );

        System.out.println(Arrays.toString(rootConfig
                .subNode("test4")
                .subNode("subnode")
                .getAs(TestClass.class)
                .map(r -> r.array).orElse(null))
        );

        System.out.println(rootConfig
                .subNode("test4")
                .subNode("subnode")
                .getString("mew")
        );
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
