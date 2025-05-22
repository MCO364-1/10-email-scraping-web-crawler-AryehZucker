import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class WebContentScraperTest {

    Set<String> emails = new HashSet<>();
    UniqueBlockingQueue<String> links = new UniqueBlockingQueue<>();

    /**
     * Test that the scraper extracts valid emails
     */
    @ParameterizedTest
    @ValueSource(strings = { "foo_37.bar@website.org", "foo37+bar@website.org", "foo37@web-site.org" })
    void testExtractBasicEmail(String email) {
        scrape(email);
        assertTrue(emails.contains(email));
    }

    /**
     * Test that the scraper extracts an email from context
     */
    @Test
    void testExtractEmailFromContext() {
        String email = "foo37@website.org";
        String content = "context \"" + email + ",context";
        scrape(content);
        assertEquals(1, emails.size());
        assertTrue(emails.contains(email));
    }

    /**
     * Test that the scraper does not extract an invalid email
     */
    @ParameterizedTest
    @ValueSource(strings = { ".foo37@website.org", "_foo37@website.org", "-foo37@website.org", "foo37@org",
            "foo37website.org", "foo37@website.o-rg" })
    void testNotExtractBadEmail(String email) {
        scrape(email);
        assertFalse(emails.contains(email));
    }

    /**
     * Test that the scraper extracts multiple emails
     */
    @Test
    void testExtractMultipleEmails() {
        String email1 = "foo37@website.org",
                email2 = "joedoe@example.co";
        scrape(email1 + " " + email2);
        assertTrue(emails.contains(email1));
        assertTrue(emails.contains(email2));
    }

    /**
     * Test that the scraper extracts links
     * 
     * @throws InterruptedException if interrupted while accessing the queue
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testExtractLink() throws InterruptedException {
        String link = "example.com";
        String content = "sample text... <a other=\"attributes\" href=\"" + link
                + "\" and-more=\"attributes\">some content</a> rest of page...";
        scrape(content);
        assertEquals(link, links.take());
    }

    private void scrape(String content) {
        Website website = new MockWebsite(content);
        WebContentScraper scraper = new WebContentScraper(website, emails, links);
        scraper.run();
    }

}
