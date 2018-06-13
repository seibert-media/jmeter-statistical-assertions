package net.seibertmedia.jmeter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import net.seibertmedia.jmeter.util.JMeterStatistic;
import net.seibertmedia.jmeter.util.JMeterStatistics;
import net.seibertmedia.jmeter.util.JMeterStatisticsReport;

public class App {

    enum Command {
        report
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            exitWithUsageInfo();
        }

        Command command = parseCommand(args[0]);
        String filename = args[1];

        switch (command) {
            case report:
                report(filename);
                break;
        }
    }

    public static Command parseCommand(String arg) {
        try {
            return Command.valueOf(arg);
        } catch (IllegalArgumentException e) {
            exitWithUsageInfo();
            return null;
        }
    }

    public static void exitWithUsageInfo() {
        System.out.println("Usage: <command> <filename>");
        System.exit(1);
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
