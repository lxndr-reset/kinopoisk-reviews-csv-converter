package parser;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;


public class JsonPageResponse {

    @JsonProperty("docs")
    private List<Review> reviews;

    // "total":599,"limit":250,"page":1,"pages":3
    private int total;

    private int limit;

    private int page;

    private int pages;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonPageResponse that)) return false;
        return getTotal() == that.getTotal() && getLimit() == that.getLimit() && getPage() == that.getPage() && getPages() == that.getPages() && Objects.equals(getReviews(), that.getReviews());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReviews(), getTotal(), getLimit(), getPage(), getPages());
    }

    @Override
    public String toString() {
        return "JsonPageResponse{" +
                "reviews=" + reviews +
                ", total=" + total +
                ", limit=" + limit +
                ", page=" + page +
                ", pages=" + pages +
                '}';
    }
}
