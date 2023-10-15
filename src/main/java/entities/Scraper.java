package entities;

public abstract class Scraper {

    private String url;

    public Scraper() {}

    public abstract String scrap(String productName);

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
