package parser;

import com.fasterxml.jackson.databind.json.JsonMapper;
import run.Run;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestSender {

    public static final JsonMapper jsonMapper = new JsonMapper();

    public static JsonPageResponse send(String url) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest request = buildRequestOfURL(url);
            String responseBody = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

            return jsonMapper.readValue(responseBody, JsonPageResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpRequest buildRequestOfURL(String reviewsRequestURL) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(reviewsRequestURL))
                    .header("accept", "application/json")
                    .header("X-API-KEY", Run.API)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return request;
    }

}
