package net.seibertmedia.jmeter.commands;

import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.median;
import static net.seibertmedia.jmeter.util.StatisticsParser.createStatisticsFromFilename;

import java.util.Map;

import net.seibertmedia.jmeter.util.JMeterStatistics;
import net.seibertmedia.jmeter.util.JMeterStatisticsCSVReport;

public class ReportCSVCommandFunction implements AppCommandFunction {
    @Override
    public void apply(Map<String, String> args) throws Exception {
        JMeterStatistics elapsedStatistics = createStatisticsFromFilename(args.get("filename"), "elapsed");
        JMeterStatisticsCSVReport jMeterStatisticsCSVReport = new JMeterStatisticsCSVReport(elapsedStatistics);

        jMeterStatisticsCSVReport.addValueColumn(median);

        jMeterStatisticsCSVReport.printReport();
    }

}
