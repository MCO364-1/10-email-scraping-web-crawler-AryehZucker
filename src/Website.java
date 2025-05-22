import java.io.IOException;

import org.jsoup.nodes.Document;

/**
 * Represents a website with HTML content
 */
public interface Website {

    /**
     * Get the HTML content of this {@code Website}
     * 
     * @return the HTML content of this {@code Website}
     * @throws IOException if there is an issue retrieving the content
     */
    Document getContent() throws IOException;

}
