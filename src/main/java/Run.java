import org.json.JSONArray;
import parser.ReviewParser;

import java.util.List;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        String api = ""; // todo remove api
      Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your API. Press enter to use default API (may be out of the limit");

        String userInput = scanner.nextLine();
        scanner.close();

        if (!userInput.isEmpty()) {
            api = userInput;
        }
        ReviewParser parser = new ReviewParser(api);
        String parse = parser.parseAll();

        System.out.println(parse.contains("\"page\":1"));
        System.out.println(parse.contains("\"page\":2"));
        System.out.println(parse.contains("\"page\":3"));
    }
}
