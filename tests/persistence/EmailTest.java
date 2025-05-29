package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class EmailTest {
    
    String emailAddress = "email.example.com", link = "https://example.com";

    /**
     * Test {@code Email} contains proper values
     */
    @Test
    void testProperValues() {
        Email email = new Email(emailAddress, link);
        assertAll(
            () -> assertEquals(emailAddress, email.getEmailAddress()),
            () -> assertEquals(link, email.getSource()),
            () -> assertTrue(Instant.now().toEpochMilli() - email.getTimeStamp().toEpochMilli() < 100)
        );
    }

    /**
     * Test that {@code Email} can be used in a {@code HashSet}
     */
    @Test
    void testHash() {
        HashSet<Email> hashSet = new HashSet<>();
        Email email = new Email(emailAddress, link);
        assertTrue(hashSet.add(email));
        assertFalse(hashSet.add(email));
        assertFalse(hashSet.add(new Email(emailAddress, null)));
    }
}
