package net.seibertmedia.jmeter.util;

import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.max;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.median;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.min;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.n;
import static net.seibertmedia.jmeter.util.JMeterStatistic.Getter.p90;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class JMeterStatisticsReportTest {
    private StringBuffer sb = new StringBuffer();
    private JMeterStatistics jMeterStatistic;
    private JMeterStatisticsReport jMeterStatisticsReport;

    private static final String LABEL_DASHBOARD = "/ (Dashboard)";
    private static final String LONG_LABEL = "/rest/enterprise-news-bundle/1.0/cover-stories (Cover-Stories Default Config)";

    private static void addSomeValues(JMeterStatistics jMeterStatistic) {
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 1);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 1);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 1);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 20);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 20);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 20);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 20);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 20);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 1000);
        jMeterStatistic.addValueForLabel(LABEL_DASHBOARD, 1000);
    }

    @Before
    public void setUp() {
        jMeterStatistic = new JMeterStatistics();
        jMeterStatisticsReport = new JMeterStatisticsReport(jMeterStatistic);
    }

    private PrintStream printStream = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            sb.append((char) b);
        }
    });

    @Test
    public void test_printReport_emptyReport() {
        jMeterStatisticsReport.printReport(printStream);

        final String[] resultLines = sb.toString().split("\n");
        assertThat(resultLines[0].trim(), equalTo("Label"));
    }

    @Test
    public void test_printReport_reportWithLongLabel() {
        addSomeValues(jMeterStatistic);

        jMeterStatistic.addValueForLabel(LONG_LABEL, 15);

        jMeterStatisticsReport.addValueColumn(n);
        jMeterStatisticsReport.addValueColumn(min);
        jMeterStatisticsReport.addValueColumn(median);
        jMeterStatisticsReport.addValueColumn(max);

        jMeterStatisticsReport.printReport(printStream);

        final String[] resultLines = sb.toString().split("\\s*\n");
        assertThat(resultLines[0], equalTo("Label                                                                         |       n |     min |  median |     max"));
        assertThat(resultLines[1], equalTo("============================================================================= | ======= | ======= | ======= | ======="));
        assertThat(resultLines[2], equalTo("/ (Dashboard)                                                                 |      10 |       1 |      20 |    1000"));
        assertThat(resultLines[3], equalTo("/rest/enterprise-news-bundle/1.0/cover-stories (Cover-Stories Default Config) |       1 |      15 |      15 |      15"));

    }

    @Test
    public void test_printReport_simpleReport() {
        addSomeValues(jMeterStatistic);

        jMeterStatisticsReport.addValueColumn(n);
        jMeterStatisticsReport.addValueColumn(min);
        jMeterStatisticsReport.addValueColumn(median);
        jMeterStatisticsReport.addValueColumn(p90);
        jMeterStatisticsReport.addValueColumn(max);

        jMeterStatisticsReport.printReport(printStream);

        final String[] resultLines = sb.toString().split("\\s*\n");
        assertThat(resultLines[0], equalTo("Label                                              |       n |     min |  median |     p90 |     max"));
        assertThat(resultLines[1], equalTo("================================================== | ======= | ======= | ======= | ======= | ======="));
        assertThat(resultLines[2], equalTo("/ (Dashboard)                                      |      10 |       1 |      20 |    1000 |    1000"));
    }
}
