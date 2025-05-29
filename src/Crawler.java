import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import persistence.Email;

/**
 * A web crawler for finding email addresses
 */
public class Crawler {

    private UniqueBlockingQueue<String> links = new UniqueBlockingQueue<>();
    private Set<Email> emails = Collections.synchronizedSet(new HashSet<>());

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
     * @throws InterruptedException if interrupted while waiting for the next link
     */
    public Set<Email> findEmails(int n) throws InterruptedException {
        final int threadCount = 4 * Runtime.getRuntime().availableProcessors();
        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            while (emails.size() < n) {
                String nextLink = links.take();
                executor.submit(new WebContentScraper(new WebsiteImp(nextLink), emails, links));
            }
            executor.shutdownNow();
        }
        return Collections.unmodifiableSet(emails);
    }

}
