package net.seibertmedia.jmeter.commands;

import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.max;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.mean;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.median;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.min;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.n;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.p90;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.p95;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.p99;
import static net.seibertmedia.jmeter.util.StatisticsParser.createStatisticsFromFilename;

import java.util.Map;

import net.seibertmedia.jmeter.util.JMeterStatistics;
import net.seibertmedia.jmeter.util.JMeterStatisticsReport;

public class ReportCommandFunction implements AppCommandFunction {
    @Override
    public void apply(Map<String, String> args) throws Exception {
        JMeterStatistics elapsedStatistics = createStatisticsFromFilename(args.get("filename"), "elapsed");
        JMeterStatisticsReport jMeterStatisticReport = new JMeterStatisticsReport(elapsedStatistics);

        jMeterStatisticReport.addValueColumn(n);
        jMeterStatisticReport.addValueColumn(min);
        jMeterStatisticReport.addValueColumn(mean);
        jMeterStatisticReport.addValueColumn(median);
        jMeterStatisticReport.addValueColumn(p90);
        jMeterStatisticReport.addValueColumn(p95);
        jMeterStatisticReport.addValueColumn(p99);
        jMeterStatisticReport.addValueColumn(max);

        jMeterStatisticReport.printReport();
    }

}
