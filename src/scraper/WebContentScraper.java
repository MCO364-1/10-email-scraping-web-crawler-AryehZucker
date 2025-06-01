package scraper;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import persistence.Email;
import util.UniqueBlockingQueue;

/**
 * Scraper for extracting emails and links from a website
 */
public class WebContentScraper implements Runnable {

    private static final Logger logger = Logger.getLogger(WebContentScraper.class.getName());
    private static final Pattern emailPattern = Pattern.compile(
            "[a-z0-9][a-z0-9_\\.-]*(\\+[a-z0-9_\\.-]+)?@([a-z0-9-]+\\.)+(com|org|edu|net|gov|co|us)",
            Pattern.CASE_INSENSITIVE);

    private final Website website;
    private final Set<Email> emails;
    private final UniqueBlockingQueue<String> links;
    private Document content;

    /**
     * Construct this {@code WebContentScraper} for a given website
     * 
     * @param website the {@code Website} to scrape for emails and links
     * @param emails  the set to add all scraped emails to
     * @param links   the queue to add all scraped links to
     */
    public WebContentScraper(
            Website website,
            Set<Email> emails,
            UniqueBlockingQueue<String> links) {
        this.website = website;
        this.emails = emails;
        this.links = links;
    }

    /**
     * Run the scraper and extract all of the emails and links from the website
     */
    @Override
    public void run() {
        try {
            content = website.getContent();
            extractLinks();
            extractEmails();
        } catch (IOException e) {
            logger.warning("Failed to fetch content from " + website);
        }
    }

    /**
     * Extract all of the emails from the website and put them into the emails set
     */
    private void extractEmails() {
        Matcher matcher = emailPattern.matcher(content.outerHtml());
        while (matcher.find()) {
            Email email = new Email(matcher.group(), website.getURL());
            int emailNumber;
            synchronized (emails) {
                if (emails.add(email)){
                    emailNumber = emails.size();
                } else {
                    emailNumber = 0;
                }
            }
            if (emailNumber != 0) {
                logger.info(String.format("Found email #%d: %s", emailNumber, email.getEmailAddress()));
            }
        }
    }

    /**
     * Extract all of the links from the website and put them onto the links queue
     */
    private void extractLinks() {
        Elements linkElements = content.getElementsByTag("a");
        for (Element linkElement : linkElements) {
            links.add(linkElement.absUrl("href"));
        }
    }

}
