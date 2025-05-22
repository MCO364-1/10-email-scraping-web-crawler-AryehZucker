import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scraper for extracting emails and links from a website
 */
public class WebContentScraper implements Runnable {

    private static final Pattern emailPattern = Pattern.compile(
            "[a-z0-9][a-z0-9_\\.-]*(\\+[a-z0-9_\\.-]+)?@([a-z0-9-]+\\.)+[a-z0-9]+",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern linkPattern = Pattern.compile(
            "<a.*href=\"([^\"]*)\".*<\\/a>",
            Pattern.CASE_INSENSITIVE);

    private final Website website;
    private final Set<String> emails;
    private final UniqueBlockingQueue<String> links;
    private String content;

    /**
     * Construct this {@code WebContentScraper} for a given website
     * 
     * @param website the {@code Website} to scrape for emails and links
     * @param emails  the set to add all scraped emails to
     * @param links   the queue to add all scraped links to
     */
    public WebContentScraper(
            Website website,
            Set<String> emails,
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
        content = website.getContent();
        extractLinks();
        extractEmails();
    }

    /**
     * Extract all of the emails from the website and put them into the emails set
     */
    private void extractEmails() {
        Matcher matcher = emailPattern.matcher(content);
        while (matcher.find()) {
            emails.add(matcher.group().toLowerCase());
        }
    }

    /**
     * Extract all of the links from the website and put them onto the links queue
     */
    private void extractLinks() {
        Matcher matcher = linkPattern.matcher(content);
        while (matcher.find()) {
            links.add(matcher.group(1));
        }
    }

}
