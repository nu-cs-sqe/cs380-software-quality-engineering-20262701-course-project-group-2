package view;

import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WelcomeViewTest {

    @Test
    public void GetSupportedLocales_FromResourceBundle_ReturnConfiguredLocales() {
        List<Locale> expected = List.of(
                Locale.ENGLISH,
                Locale.forLanguageTag("es"),
                Locale.FRENCH
        );

        assertEquals(expected, WelcomeView.getSupportedLocales());
    }
}
