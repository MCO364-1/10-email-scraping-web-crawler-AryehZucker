import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@code Crawler} class
 */
public class CrawlerTest {

    /**
     * Test a {@code Crawler} with its typical use
     * 
     * @throws InterruptedException if the crawler is interrupted
     */
    @Test
    void testCrawler() throws InterruptedException {
        Crawler crawler = new Crawler("https://example.com");
        Set<String> emails = crawler.findEmails(5);
        assertTrue(emails.contains("dee3@us.ibm.com"));
    }

}
