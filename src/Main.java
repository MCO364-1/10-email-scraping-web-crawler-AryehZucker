import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

import persistence.Database;
import persistence.Email;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String startingAddress = "https://touro.edu";
    private static final int numOfEmailsToCollect = 10_000;

    public static void main(String[] args) throws InterruptedException {
        Crawler crawler = new Crawler(startingAddress);
        logger.fine("Beginning web crawl from: " + startingAddress);
        logger.fine("Crawling until " + numOfEmailsToCollect + " emails are found");
        Set<Email> emails = crawler.findEmails(numOfEmailsToCollect);

        try (Database database = new Database()) {
            database.persistEmails(emails);
            logger.info("Successfully persisted emails to the database");
        } catch (SQLException e) {
            logger.severe("Error persisting emails to the database: " + e.getMessage());
        }
    }
}