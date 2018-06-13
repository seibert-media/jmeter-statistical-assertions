package net.seibertmedia.jmeter.util;


import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.MathIllegalStateException;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class JMeterStatistic {

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

    public double get90Percentile() {
        return statistics.getPercentile(90);
    }

    public double get95Percentile() {
        return statistics.getPercentile(95);
    }

    public double get99Percentile() {
        return statistics.getPercentile(99);
    }
}
