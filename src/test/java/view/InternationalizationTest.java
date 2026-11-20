package view;

import java.util.Locale;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternationalizationTest {

    @Test
    public void ResourceBundle_EnglishLocale_ReturnEnglishWelcomeMessage() {
        ResourceBundle labels = ResourceBundle.getBundle(
                "labels",
                Locale.ENGLISH
        );

        assertEquals(
                "Welcome to our Chess Game!",
                labels.getString("welcomeMessage")
        );
    }
}