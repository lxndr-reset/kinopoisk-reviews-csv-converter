import org.json.JSONArray;
import org.json.JSONObject;
import utils.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ReviewConvertor {
    public static List<Review> reviewsFromString(List<String> data) {
        List<Review> list = new ArrayList<>();

        for (String datum : data) {
            JSONArray jsonArray = new JSONObject(datum).getJSONArray("docs");

            for (int i = 0; i < jsonArray.length(); i++) {
                Review review = getArgumentsOrNull(jsonArray.getJSONObject(i));

                list.add(review);
            }
        }

        return list;
    }

    private static Review getArgumentsOrNull(JSONObject jsonObject) {
        Function<String, String> getStringOrNull =
                string -> jsonObject.has(string) ? jsonObject.getString(string) : null;

        return new Review(
                jsonObject.getInt("id"),
                getStringOrNull.apply("type"),
                getStringOrNull.apply("review"),
                getStringOrNull.apply("date"),
                getStringOrNull.apply("author"),
                getStringOrNull.apply("updatedAt"),
                getStringOrNull.apply("createdAt")
        );
    }
}
