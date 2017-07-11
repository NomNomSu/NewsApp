package g.newsapp;

public class News {
    private String mTitle;
    private String mSection;
    private String mAuthor;
    private String mUrl;

    public News(String title, String section, String author, String url) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getAuthor() {
        return mSection;
    }
    public String getAuthorr() {
        return mAuthor;
    }
    public String getUrl() { return  mUrl;}
}
