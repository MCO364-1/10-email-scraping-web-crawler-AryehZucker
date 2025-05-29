import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String startingAddress = "https://touro.edu";
    private static final int numOfEmailsToCollect = 10_000;

    public static void main(String[] args) throws InterruptedException {
        Crawler crawler = new Crawler(startingAddress);
        logger.fine("Beginning web crawl from: " + startingAddress);
        logger.fine("Crawling until " + numOfEmailsToCollect + " emails are found");
        crawler.findEmails(numOfEmailsToCollect);
    }
}