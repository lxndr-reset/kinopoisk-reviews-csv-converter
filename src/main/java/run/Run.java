package run;

import parser.ReviewParser;
import writer.CsvConverter;

import java.io.IOException;
import java.util.Scanner;


public class Run {

    public static String API = "QZ73JS1-D4DMMK9-MDSVKBQ-TSN655K";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your API. Press enter to use default API (may be out of the limit)");

        String userInput = scanner.nextLine();
        scanner.close();

        if (!userInput.isEmpty()) {
            API = userInput;
        }

        try {
            new CsvConverter().writeToCSV("./reviews.csv", new ReviewParser().parseAll());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
