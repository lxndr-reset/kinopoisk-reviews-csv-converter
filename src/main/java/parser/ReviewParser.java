package parser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewParser {
    private final String API;
    private final AtomicInteger currentPage = new AtomicInteger(1);
    private String reviewsRequestURL = getRequestURL();

    public ReviewParser(String api) {
        API = api;
    }

    public String parseAll() {
        String reviewsString = parse();

        int limit = getPageAmount(reviewsString);
        StringBuilder res = new StringBuilder(reviewsString);
        List<CompletableFuture<String>> futures = new ArrayList<>(limit - 1);

        while (currentPage.get() < limit) {
            currentPage.incrementAndGet();
            reviewsRequestURL = getRequestURL();
            String requestURL = getRequestURL(currentPage.get());
            CompletableFuture<String> e = CompletableFuture.supplyAsync(() -> parse(requestURL));
            futures.add(e);
        }

        for (CompletableFuture<String> future : futures) {
            try {
                String str = future.get();
                res.append(str);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        currentPage.set(1);
        reviewsRequestURL = getRequestURL();

        return res.toString();
    }

    public String parse() {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(reviewsRequestURL))
                    .header("accept", "application/json")
                    .header("X-API-KEY", API)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String parse(String reviewsRequestURL) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(reviewsRequestURL))
                    .header("accept", "application/json")
                    .header("X-API-KEY", API)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRequestURL() {
        return STR."https://api.kinopoisk.dev/v1.4/review?page=\{currentPage}&limit=250&selectFields=id&selectFields=type&selectFields=review&selectFields=date&selectFields=author&selectFields=updatedAt&selectFields=createdAt&notNullFields=&movieId=326";
    }

    private String getRequestURL(int page) {
        return STR."https://api.kinopoisk.dev/v1.4/review?page=\{page}&limit=250&selectFields=id&selectFields=type&selectFields=review&selectFields=date&selectFields=author&selectFields=updatedAt&selectFields=createdAt&notNullFields=&movieId=326";
    }

    //"total":599,"limit":250,"page":1,"pages":3}
    private int getPageAmount(String string) {
        String firstPart = "\"pages\":";
        return Integer.parseInt(string.substring(string.indexOf(firstPart) + firstPart.length(), string.lastIndexOf("}")));
    }
}
