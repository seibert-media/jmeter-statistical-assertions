package net.seibertmedia.jmeter.commands.check;

import net.seibertmedia.jmeter.util.JMeterStatistic;

public class Assertion {
    private JMeterStatistic.Getter getter;
    private int value;

    public Assertion(JMeterStatistic.Getter getter, Integer value) {
        this.getter = getter;
        this.value = value;
    }

    public JMeterStatistic.Getter getGetter() {
        return getter;
    }

    public int getValue() {
        return value;
    }
}
