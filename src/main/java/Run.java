import parser.ReviewParser;
import utils.Review;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) throws IOException {
        String api = "QZ73JS1-D4DMMK9-MDSVKBQ-TSN655K";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your API. Press enter to use default API (may be out of the limit)");

        String userInput = scanner.nextLine();
        scanner.close();

        if (!userInput.isEmpty()) {
            api = userInput;
        }
        ReviewParser parser = new ReviewParser(api);
        List<String> parse = parser.parseAll();
        List<Review> reviews = ReviewConvertor.reviewsFromString(parse);

        new CsvManager.Converter().addToCsv("./myfile.csv", reviews);
    }
}
