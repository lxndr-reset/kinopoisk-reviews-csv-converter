package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewParser {

    private final AtomicInteger currentPage = new AtomicInteger(1);

    private void waitFutures(List<CompletableFuture<JsonPageResponse>> futures, List<Review> parsedPages) {
        for (CompletableFuture<JsonPageResponse> future : futures) {
            try {
                List<Review> reviews = future.get().getReviews();
                parsedPages.addAll(reviews);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getRequestUrlOfPage(int page) {
        return STR."https://api.kinopoisk.dev/v1.4/review?page=\{page}&limit=250&selectFields=id&selectFields=type&selectFields=review&selectFields=date&selectFields=author&selectFields=updatedAt&selectFields=createdAt&notNullFields=&movieId=326";
    }

    public List<Review> parseAll() {
        // need to parse the first page to get the number of pages
        JsonPageResponse jsonPageResponse = RequestSender.send(getRequestUrlOfPage(currentPage.get()));

        List<Review> parsedPages = new ArrayList<>(jsonPageResponse.getPages() * 250);
        List<CompletableFuture<JsonPageResponse>> futures = new ArrayList<>();
        parsedPages.addAll(jsonPageResponse.getReviews());

        while (currentPage.incrementAndGet() <= jsonPageResponse.getPages()) {
            // If inline currentPage.get(), it'll be changed after loop finished and all built request urls will wrong
            String parseURL = getRequestUrlOfPage(currentPage.get());
            futures.add(CompletableFuture.supplyAsync(() ->
                    RequestSender.send(parseURL)
            ));
        }

        currentPage.set(1);

        waitFutures(futures, parsedPages);

        return parsedPages;
    }
}
