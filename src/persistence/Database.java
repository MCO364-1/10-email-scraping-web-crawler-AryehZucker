package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A database for saving {@code Email}s
 */
public class Database implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(Database.class.getName());
    private static final String CONNECTION_URL = "jdbc:sqlserver://"
            + System.getenv("DB_SERVER") + ";"
            + "database=" + System.getenv("DB_DATABASE") + ";"
            + "user=" + System.getenv("DB_USER") + ";"
            + "password=" + System.getenv("DB_PASSWD") + ";"
            + "encrypt=true;trustServerCertificate=true;loginTimeout=30;";

    private final Connection connection;

    /**
     * Construct this {@code Database} and connect to the server
     * 
     * @throws SQLException if there is an issue connecting to the server
     */
    public Database() throws SQLException {
        connection = DriverManager.getConnection(CONNECTION_URL);
    }

    /**
     * Save a set of emails in the database
     * 
     * @param emails the set of emails to persist
     * @throws SQLException if there is an issue saving the emails to the database
     */
    public void persistEmails(Set<Email> emails) throws SQLException {
        logger.info("Persisting " + emails.size() + " emails");
        try (PreparedStatement insertStatement = connection.prepareStatement(
                "INSERT INTO Emails (EmailAddress, Source, TimeStamp) VALUES (?, ?, ?)")) {
            for (Email email : emails) {
                insertStatement.setString(1, email.getEmailAddress());
                insertStatement.setString(2, email.getSource());
                insertStatement.setString(3, email.getTimeStamp().toString());
                try {
                    insertStatement.executeUpdate();
                } catch (SQLException e) {
                    logger.warning(e.getMessage());
                }
            }
        }
    }

    /**
     * Close the database connection
     */
    @Override
    public void close() throws SQLException {
        connection.close();
    }

}
