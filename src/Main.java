public class Main {
    public static void main(String[] args) throws InterruptedException {
        Crawler crawler = new Crawler("https://touro.edu");
        crawler.findEmails(10_000);
    }
}