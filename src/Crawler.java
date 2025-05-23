import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A web crawler for finding email addresses
 */
public class Crawler {

    private UniqueBlockingQueue<String> links = new UniqueBlockingQueue<>();
    private Set<String> emails = new HashSet<>();

    /**
     * Construct a crawler that starts at the give web address
     * 
     * @param startingAddress the address from which to begin crawling
     */
    public Crawler(String startingAddress) {
        links.add(startingAddress);
    }

    /**
     * Crawl the web and find {@code n} emails and then stop crawling
     * 
     * @param n the number of emails to find
     * @return a set containing all the emails that this crawler has found so far
     */
    public Set<String> findEmails(int n) {
        while (emails.size() < n) {
            try {
                new WebContentScraper(
                        new WebsiteImp(links.take()),
                        emails,
                        links).run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Collections.unmodifiableSet(emails);
    }

}
