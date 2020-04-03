package loaders;

import com.opencsv.CSVWriter;
import lombok.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Value
public class CsvDataDumper {

    String fileName;
    private final static String HEADER = "Pokolenie:Best:Average:Worst";
    private static final Logger logger = LogManager.getLogger(CsvDataDumper.class);


    public void dumpValues(List<Double> best, List<Double> average, List<Double> worst) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(HEADER);
            writer.newLine();
            DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMANY);

            for (int i = 0; i < best.size(); i++) {
                writer.write(i + ":"
                        + format.format(best.get(i)).replace(".", "") + ":"
                        + format.format(average.get(i)).replace(".", "") + ":"
                        + format.format(worst.get(i)).replace(".", ""));
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
