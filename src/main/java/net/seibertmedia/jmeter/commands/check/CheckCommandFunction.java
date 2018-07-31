package net.seibertmedia.jmeter.commands.check;

import static net.seibertmedia.jmeter.util.StatisticsParser.createStatisticsFromFilename;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import net.seibertmedia.jmeter.commands.AppCommandFunction;
import net.seibertmedia.jmeter.util.JMeterStatistic;
import net.seibertmedia.jmeter.util.JMeterStatistics;

public class CheckCommandFunction implements AppCommandFunction {

    @Override
    public void apply(Map<String, String> args) throws Exception {
        JMeterStatistics elapsedStatistics = createStatisticsFromFilename(args.get("filename"), "elapsed");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(args.get("assertions-filename"));

        final ReportAssertionsYaml reportAssertionsYaml = mapper.readValue(file, ReportAssertionsYaml.class);

        int failedAssertions = 0;
        int successfulAssertions = 0;

        System.out.println();

        for (ReportAssertions reportAssertions : reportAssertionsYaml.getReportAssertions()) {
            final String label = reportAssertions.getLabel();
            final JMeterStatistic statisticForLabel = elapsedStatistics.getStatisticForLabel(label);

            if (statisticForLabel == null) {
                System.err.printf("✗ No values found for '%s'.\n", label);
                failedAssertions++;
                continue;
            }

            for (Assertion assertion : reportAssertions.getAssertions()) {
                final JMeterStatistic.Getter getter = assertion.getGetter();
                Double statisticValue = getter.getGetter().apply(statisticForLabel);

                if (statisticValue > assertion.getValue()) {
                    System.err.printf("✗ Assertion for '%s' failed. Measured values (%s: %.2f) exceeds the threshold %d \n", label, getter.toString(), statisticValue, assertion.getValue());
                    failedAssertions++;
                } else {
                    System.err.printf("✓ Assertion for '%s' succeeded. Measured values (%s: %.2f) are below the threshold %d \n", label, getter.toString(), statisticValue, assertion.getValue());
                    successfulAssertions++;
                }
            }
        }

        System.out.println();
        if (failedAssertions > 0) {
            System.err.printf("%d assertions failed (%d/%d were successful)\n", failedAssertions, successfulAssertions, failedAssertions + successfulAssertions);
            throw new AssertionFailed(failedAssertions + " assertions failed ");
        } else {
            System.out.printf("%d/%d were successful\n", successfulAssertions, failedAssertions + successfulAssertions);
        }
    }


}
