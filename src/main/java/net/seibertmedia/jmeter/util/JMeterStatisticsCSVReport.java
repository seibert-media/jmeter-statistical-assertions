package net.seibertmedia.jmeter.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JMeterStatisticsCSVReport {

    private final JMeterStatistics jMeterStatistics;
    private final List<JMeterStatistic.Getter> columns = new ArrayList<>();

    public JMeterStatisticsCSVReport(JMeterStatistics jMeterStatistics) {
        this.jMeterStatistics = jMeterStatistics;
    }

    public void addValueColumn(JMeterStatistic.Getter columnGetter) {
        columns.add(columnGetter);
    }

    public void printReport() {
        printReport(System.out);
    }

    protected void printReport(PrintStream printStream) {

        for (String label : jMeterStatistics.getLabels()) {
            JMeterStatistic statisticsForLabel = jMeterStatistics.getStatisticForLabel(label);

            final Double[] values = columns.stream()
                    .map(column -> column.getGetter().apply(statisticsForLabel))
                    .collect(Collectors.toList()).toArray(new Double[columns.size()]);

            printStream.print(label);
            printStream.printf(";%.0f\n", (Object[]) values);
        }
    }

}
