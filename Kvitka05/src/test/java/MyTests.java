import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MyTests {

    // 1. Simple test
    @Tag("math")
    @Test
    public void simpleTest() {
        assertEquals(2, 1 + 1, "1 + 1 equals 2");
    }

    // 2. Parameterized test with 1 static parameter
    @Tag("stringCheck")
    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "level", "merge"})
    void oneParamTest(String word) {
        assertTrue(word.length() > 3, "Words should more than 3 characters");
    }

    // 3. Parameterized test with a static set of parameters
    @Tag("math")
    @ParameterizedTest
    @MethodSource("provideNumbers")
    void multiParamTest(int number) {
        assertTrue(number % 2 == 0, "Number should be even");
    }

    static Stream<Integer> provideNumbers() {
        return Stream.of(2, 4, 32526, 8);
    }

    @Tag("stringCheck")
    @TestFactory
    Stream<DynamicTest> dynamicTests() {
        return Stream.of("A", "null", "B")
                .map(input -> DynamicTest.dynamicTest("Тест для: " + input, () -> {
                    assertNotNull(input);
                }));
    }
}
