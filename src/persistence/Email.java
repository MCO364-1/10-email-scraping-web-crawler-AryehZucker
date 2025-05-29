package persistence;

import java.time.Instant;

/**
 * Represents an email address from a website
 */
public class Email {

    private final String emailAddress, source;
    private final Instant timeStamp;

    /**
     * Construct an {@code Email} for an email address and a source website which it
     * can be found at
     * 
     * @param emailAddress the email address to store
     * @param source       the website that the email address can be found in
     */
    public Email(String emailAddress, String source) {
        this.emailAddress = emailAddress.toLowerCase();
        this.source = source;
        this.timeStamp = Instant.now();
    }

    /**
     * Get the email address
     * 
     * @return a {@code String} of the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Get the source website that the email address can be found at
     * 
     * @return a {@code String} of the website
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the time that this {@code Email} was created
     * 
     * @return the {@code Instant} this {@code Email} was created
     */
    public Instant getTimeStamp() {
        return timeStamp;
    }

    @Override
    public int hashCode() {
        return emailAddress.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Email other = (Email) obj;
        return emailAddress.equals(other.emailAddress);
    }


}
