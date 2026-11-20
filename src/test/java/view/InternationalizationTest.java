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

    @Test
    public void ResourceBundle_SpanishLocale_ReturnSpanishWelcomeMessage() {
        ResourceBundle labels = ResourceBundle.getBundle(
                "labels",
                Locale.forLanguageTag("es")
        );

        assertEquals(
                "¡Bienvenido a nuestro juego de ajedrez!",
                labels.getString("welcomeMessage")
        );
    }
}