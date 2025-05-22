/**
 * A mock implementation of a {@code Website} to be used for tests
 */
public class MockWebsite implements Website {

    private final String content;

    /**
     * Construct this {@code MockWebsite} with the given content
     * 
     * @param content the content of this website
     */
    public MockWebsite(String content) {
        this.content = content;
    }

    /**
     * Get the provided content for this {@code MockWebsite}
     * 
     * @return the content of this site
     */
    @Override
    public String getContent() {
        return content;
    }
    
}
