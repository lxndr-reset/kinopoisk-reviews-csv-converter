package writer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import parser.Review;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvConverter {

    public static void writeToCSV(String path, List<Review> list) throws IOException {
        if (!path.endsWith(".csv")) {
            throw new IllegalArgumentException("File should end with .csv");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                .setHeader("id", "type", "review", "date", "author", "updatedAt", "createdAt")
                .setAutoFlush(true)
                .build());
        for (Review review : list) {
            printer.printRecord(review.id(), review.type(), review.review(), review.date(), review.author(), review.updatedAt(), review.createdAt());
        }
    }
}
