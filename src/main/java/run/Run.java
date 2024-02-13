package run;

import parser.Review;
import parser.ReviewParser;
import writer.CsvConverter;

import java.io.IOException;
import java.util.List;


public class Run {

    public static String API = "QZ73JS1-D4DMMK9-MDSVKBQ-TSN655K";

    public static void main(String[] args) {
        if (args.length == 1) {
            API = args[0];
        }

        try {
            List<Review> list = new ReviewParser().parseAll();
            CsvConverter.writeToCSV("./reviews.csv", list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
