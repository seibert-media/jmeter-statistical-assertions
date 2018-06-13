package net.seibertmedia.jmeter.util;


import java.util.function.Function;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class JMeterStatistic {

    public enum Getter {
        max(JMeterStatistic::getMax),
        mean(JMeterStatistic::getMean),
        median(JMeterStatistic::getMedian),
        min(JMeterStatistic::getMin),
        p90(JMeterStatistic::getPercentile90),
        p95(JMeterStatistic::getPercentile95),
        p99(JMeterStatistic::getPercentile99),;

        private final String displayName;
        private final Function<JMeterStatistic, Double> getter;

        Getter(Function<JMeterStatistic, Double> getter) {
            this(null, getter);
        }

        Getter(String displayName, Function<JMeterStatistic, Double> getter) {
            this.displayName = displayName;
            this.getter = getter;
        }

        public Function<JMeterStatistic, Double> getGetter() {
            return getter;
        }

        @Override
        public String toString() {
            if (displayName == null) {
                return super.toString();
            } else {
                return displayName;
            }
        }
    }

    private final DescriptiveStatistics statistics = new DescriptiveStatistics();

    void addValue(double value) {
        statistics.addValue(value);
    }

    public double getMean() {
        return statistics.getMean();
    }

    public double getGeometricMean() {
        return statistics.getGeometricMean();
    }

    public double getPopulationVariance() {
        return statistics.getPopulationVariance();
    }

    public double getStandardDeviation() {
        return statistics.getStandardDeviation();
    }

    public double getQuadraticMean() {
        return statistics.getQuadraticMean();
    }

    public double getSkewness() {
        return statistics.getSkewness();
    }

    public double getMax() {
        return statistics.getMax();
    }

    public double getMin() {
        return statistics.getMin();
    }

    public long getN() {
        return statistics.getN();
    }

    public double getSum() {
        return statistics.getSum();
    }

    public double getSumsq() {
        return statistics.getSumsq();
    }

    public double getPercentile(double p) throws MathIllegalStateException, MathIllegalArgumentException {
        return statistics.getPercentile(p);
    }

    public double getMedian() {
        return statistics.getPercentile(50);
    }

    public double getPercentile90() {
        return statistics.getPercentile(90);
    }

    public double getPercentile95() {
        return statistics.getPercentile(95);
    }

    public double getPercentile99() {
        return statistics.getPercentile(99);
    }
}
