import parser.ReviewParser;
import utils.Review;

import java.util.List;
import java.util.Scanner;

public class Run {
    //todo add list-to-csv conversion
    public static void main(String[] args) {
        String api = ""; // todo remove api
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your API. Press enter to use default API (may be out of the limit)");

        String userInput = scanner.nextLine();
        scanner.close();

        if (!userInput.isEmpty()) {
            api = userInput;
        }
        ReviewParser parser = new ReviewParser(api);
        List<String> parse = parser.parseAll();
        List<Review> reviews = StringToReviewProcessor.reviewsFromString(parse);
    }
}
