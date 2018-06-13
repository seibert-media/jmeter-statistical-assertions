package net.seibertmedia.jmeter.commands.check;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.seibertmedia.jmeter.util.JMeterStatistic;

public class ReportAssertionsYaml {
    private Map<String, Map<String, Integer>> thresholds;


    public Map<String, Map<String, Integer>> getThresholds() {
        return thresholds;
    }

    public void setThresholds(Map<String, Map<String, Integer>> thresholds) {
        this.thresholds = thresholds;
    }


    public List<ReportAssertions> getReportAssertions() {
        return thresholds.keySet().stream().map((label) -> new ReportAssertions(label, thresholds.get(label)
                .entrySet()
                .stream()
                .map(entry -> new Assertion(JMeterStatistic.Getter.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return thresholds.toString();
    }
}
