package net.seibertmedia.jmeter.commands;


public class AppCommandParameter {
    private final String parameterName;

    private AppCommandParameter(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public static AppCommandParameter createParameter(String parameterName) {
        return new AppCommandParameter(parameterName);
    }

    @Override
    public boolean equals(Object anObject) {
        return parameterName.equals(anObject);
    }

    @Override
    public int hashCode() {
        return parameterName.hashCode();
    }
}
