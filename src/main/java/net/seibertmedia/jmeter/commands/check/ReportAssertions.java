package net.seibertmedia.jmeter.commands.check;


import java.util.List;

public class ReportAssertions {

    private String label;
    private List<Assertion> assertions;

    public ReportAssertions(String label, List<Assertion> assertions) {
        this.label = label;
        this.assertions = assertions;
    }

    public String getLabel() {
        return label;
    }

    public List<Assertion> getAssertions() {
        return assertions;
    }
}
