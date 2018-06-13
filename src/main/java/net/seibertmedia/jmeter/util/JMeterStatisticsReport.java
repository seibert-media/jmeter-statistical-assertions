package net.seibertmedia.jmeter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JMeterStatisticsReport {

    private final JMeterStatistics jMeterStatistics;
    private final List<String> columns = new ArrayList<>();
    private final Map<String, Function<JMeterStatistic, Double>> getterByLabel = new HashMap<>();

    public JMeterStatisticsReport(JMeterStatistics jMeterStatistics) {
        this.jMeterStatistics = jMeterStatistics;
    }

    public void addValueColumn(String columnName, Function<JMeterStatistic, Double> getter) {
        columns.add(columnName);
        getterByLabel.put(columnName, getter);
    }

    public void printReport() {
        int labelColumnWidth = 50;
        int valueColumnWidth = 6;

        String firstColumnFormat = "%-" + labelColumnWidth + "s";
        String valueColumnsFormat = nString(" | %" + valueColumnWidth + ".0f ", columns.size());

        // first header column
        System.out.printf("%-" + labelColumnWidth + "s", "Label");

        // header value column format
        String format = nString(" | %" + valueColumnWidth + "s ", columns.size()) + "\n";
        System.out.printf(format, columns.toArray(new Object[columns.size()]));

        System.out.println(nString("=", labelColumnWidth) + nString(" | =" + nString("=", valueColumnWidth), columns.size()));

        for (String label : jMeterStatistics.getLabels()) {
            JMeterStatistic statisticsForLabel = jMeterStatistics.getStatisticForLabel(label);

            final Double[] values = columns.stream()
                    .map(s -> getterByLabel.get(s).apply(statisticsForLabel))
                    .collect(Collectors.toList()).toArray(new Double[columns.size()]);

            System.out.printf(firstColumnFormat, label);
            System.out.printf(valueColumnsFormat + "\n", (Object[]) values);
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
