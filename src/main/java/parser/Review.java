package parser;

import java.util.Objects;

public record Review(int id, String type, String review, String date,
                     String author, String updatedAt, String createdAt) {
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", review='" + review + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review1)) return false;
        return id == review1.id
                && Objects.equals(type, review1.type)
                && Objects.equals(review, review1.review)
                && Objects.equals(date, review1.date)
                && Objects.equals(author, review1.author)
                && Objects.equals(updatedAt, review1.updatedAt)
                && Objects.equals(createdAt, review1.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, review, date, author, updatedAt, createdAt);
    }
}
