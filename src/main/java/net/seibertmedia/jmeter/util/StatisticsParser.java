package net.seibertmedia.jmeter.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class StatisticsParser {
    public static JMeterStatistics createStatisticsFromFilename(String filename, String column) throws IOException {
        Reader in = new FileReader(filename);

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

        // timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,Latency,IdleTime,Connect

        JMeterStatistics statistics = new JMeterStatistics();
        for (CSVRecord record : records) {
            int elapsed = Integer.parseInt(record.get(column));
            String label = record.get("label").trim();

            statistics.addValueForLabel(label, elapsed);
        }

        return statistics;
    }

}
