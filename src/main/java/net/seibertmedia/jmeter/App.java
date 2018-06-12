package net.seibertmedia.jmeter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import net.seibertmedia.jmeter.util.JMeterStatistic;
import net.seibertmedia.jmeter.util.JMeterStatistics;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws IOException {
        Reader in = new FileReader("aggregate.csv");

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        // timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,Latency,IdleTime,Connect

        JMeterStatistics elapsedStatistic = new JMeterStatistics();
        for (CSVRecord record : records) {
            int elapsed = Integer.parseInt(record.get("elapsed"));
            String label = record.get("label");

            elapsedStatistic.addValueForLabel(label, elapsed);
        }

        int labelColumnWidth = 50;
        int valueColumnWidth = 10;
        int valueColumns = 1;

        String headerFormat = "%-" + labelColumnWidth + "s" + nString(" | %" + valueColumnWidth + "s ", valueColumns);
        String rowFormat = "%-" + labelColumnWidth + "s" + nString(" | %" + valueColumnWidth + ".0f ", valueColumns);

        System.out.printf(headerFormat + "\n", "Label", "Max");
        System.out.println(nString("=", labelColumnWidth) + nString(" | " + nString("=", valueColumnWidth), valueColumns));

        for (String label: elapsedStatistic.getLabels()) {
            JMeterStatistic statisticsForLabel = elapsedStatistic.getStatisticForLabel(label);
            System.out.printf(rowFormat + "\n", label, statisticsForLabel.getMax());
        }
    }

    private static String nString(String s, int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
