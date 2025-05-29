import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Represents a website with HTML content. Uses the JSoup library to access the
 * content of webpages.
 */
public class WebsiteImp implements Website {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36";

    private final String url;

    /**
     * Construct this {@code WebsiteImp} for a given URL
     * 
     * @param url the URL to fetch the webpage from
     */
    public WebsiteImp(String url) {
        this.url = url;
    }

    /**
     * Get the content of a webpage from the internet using JSoup
     * 
     * @return the content of the webpage
     * @throws IOException if there is an issue fetching the webpage
     */
    @Override
    public Document getContent() throws IOException {
        return Jsoup.connect(url)
                .timeout(5000)
                .userAgent(USER_AGENT)
                .get();
    }

    /**
     * Get a the URL of the website
     * 
     * @return the URL of this website
     */
    @Override
    public String toString() {
        return url;
    }

    /**
     * Get the URL of this {@code Website}
     * 
     * @return the URL as a {@code String}
     */
    @Override
    public String getURL() {
        return url;
    }

}
