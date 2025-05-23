import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@code Crawler} class
 */
public class CrawlerTest {

    /**
     * Test a {@code Crawler} with its typical use
     */
    @Test
    void testCrawler() {
        Crawler crawler = new Crawler("https://example.com");
        Set<String> emails = crawler.findEmails(1);
        assertTrue(emails.contains("dee3@us.ibm.com"));
    }

}
