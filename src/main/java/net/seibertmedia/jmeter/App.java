package net.seibertmedia.jmeter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import net.seibertmedia.jmeter.util.JMeterStatistic;
import net.seibertmedia.jmeter.util.JMeterStatisticsReport;
import net.seibertmedia.jmeter.util.JMeterStatistics;

/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args ) throws IOException {
        if (args.length < 2 || !args[0].matches("report")) {
            usage();
            System.exit(1);
        }

        String command = args[0];
        String filename = args[1];

        report(filename);

    }

    public static void usage() {
        System.out.println("Usage: <command> <filename>");
    }

    public static void report(String filename) throws IOException {
        Reader in = new FileReader(filename);

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        // timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,Latency,IdleTime,Connect

        JMeterStatistics elapsedStatistic = new JMeterStatistics();
        for (CSVRecord record : records) {
            int elapsed = Integer.parseInt(record.get("elapsed"));
            String label = record.get("label");

            elapsedStatistic.addValueForLabel(label, elapsed);
        }


        JMeterStatisticsReport jMeterStatisticReport = new JMeterStatisticsReport(elapsedStatistic);

        jMeterStatisticReport.addValueColumn("Min", JMeterStatistic::getMin);
        jMeterStatisticReport.addValueColumn("Mean", JMeterStatistic::getMean);
        jMeterStatisticReport.addValueColumn("Median", JMeterStatistic::getMedian);
        jMeterStatisticReport.addValueColumn("90p", JMeterStatistic::get90Percentile);
        jMeterStatisticReport.addValueColumn("95p", JMeterStatistic::get95Percentile);
        jMeterStatisticReport.addValueColumn("99p", JMeterStatistic::get99Percentile);
        jMeterStatisticReport.addValueColumn("Max", JMeterStatistic::getMax);

        jMeterStatisticReport.printReport();
    }

}
