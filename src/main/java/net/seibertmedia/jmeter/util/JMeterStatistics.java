package net.seibertmedia.jmeter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JMeterStatistics {

    private final Map<String, JMeterStatistic> statisticsByLabel = new HashMap<>();

    public void addValueForLabel(String label, double value) {
        if (!statisticsByLabel.containsKey(label)) {
            statisticsByLabel.put(label, new JMeterStatistic());
        }
        statisticsByLabel.get(label).addValue(value);
    }

    public double getPercentileForLabel(String label, double percentile) {
        return statisticsByLabel.get(label).getPercentile(percentile);
    }

    public List<String> getLabels() {
        final List<String> labels = new ArrayList<>(statisticsByLabel.size());
        labels.addAll(statisticsByLabel.keySet());
        labels.sort(String::compareTo);
        return labels;
    }

    public JMeterStatistic getStatisticForLabel(String label) {
        return statisticsByLabel.get(label);
    }

}
