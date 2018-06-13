package net.seibertmedia.jmeter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Set<String> getLabels() {
        return statisticsByLabel.keySet();
    }

    public JMeterStatistic getStatisticForLabel(String label) {
        return statisticsByLabel.get(label);
    }

}
