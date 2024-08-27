package dev.lapinski.persinate.model.translation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnderscoreTranslationStrategyTest {
    @ParameterizedTest
    @CsvSource({
        "InternalUser,internal_user",
        "ApplePie,apple_pie",
        "SugarApplePie,sugar_apple_pie",
        "helloworld,helloworld",
        "helloWorld,hello_world",
        "HELLO,h_e_l_l_o",
        "A,a"
    })
    void underscoreTest(String input, String expectedOutput) {
        assertEquals(expectedOutput, new UnderscoreTranslationStrategy().translateName(input));
    }
}