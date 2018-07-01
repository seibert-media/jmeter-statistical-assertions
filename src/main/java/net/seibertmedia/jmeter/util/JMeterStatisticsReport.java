package net.seibertmedia.jmeter.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JMeterStatisticsReport {

    private static final int DEFAULT_LABEL_COLUMN_WITH = 50;

    private final JMeterStatistics jMeterStatistics;
    private final List<JMeterStatistic.Getter> columns = new ArrayList<>();

    public JMeterStatisticsReport(JMeterStatistics jMeterStatistics) {
        this.jMeterStatistics = jMeterStatistics;
    }

    public void addValueColumn(JMeterStatistic.Getter columnGetter) {
        columns.add(columnGetter);
    }

    public void printReport() {
        printReport(System.out);
    }

    protected void printReport(PrintStream printStream) {

        int labelColumnWidth = Math.max(DEFAULT_LABEL_COLUMN_WITH, jMeterStatistics.getLabels()
                .stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(DEFAULT_LABEL_COLUMN_WITH));

        int valueColumnWidth = 7;

        String firstColumnFormat = "%-" + labelColumnWidth + "s";
        String valueColumnsFormat = nString(" | %" + valueColumnWidth + ".0f", columns.size());

        // first header column
        printStream.printf("%-" + labelColumnWidth + "s", "Label");

        // header value column format
        String format = nString(" | %" + valueColumnWidth + "s", columns.size()) + "\n";
        printStream.printf(format, columns.toArray(new Object[columns.size()]));

        printStream.println(nString("=", labelColumnWidth) + nString(" | " + nString("=", valueColumnWidth), columns.size()));

        for (String label : jMeterStatistics.getLabels()) {
            JMeterStatistic statisticsForLabel = jMeterStatistics.getStatisticForLabel(label);

            final Double[] values = columns.stream()
                    .map(column -> column.getGetter().apply(statisticsForLabel))
                    .collect(Collectors.toList()).toArray(new Double[columns.size()]);

            printStream.printf(firstColumnFormat, label);
            printStream.printf(valueColumnsFormat + "\n", (Object[]) values);
        }
    }

    private static String nString(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
