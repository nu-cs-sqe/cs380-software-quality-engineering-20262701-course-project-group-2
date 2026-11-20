package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationTest {

    // L1
    @Test
    public void Equals_SameInstance_ReturnTrue() {
        Location location = new Location(3, 4);

        boolean actual = location.equals(location);

        assertTrue(actual);
    }

    // L2
    @Test
    public void Equals_Null_ReturnFalse() {
        Location location = new Location(3, 4);

        boolean actual = location.equals(null);

        assertFalse(actual);
    }
}
