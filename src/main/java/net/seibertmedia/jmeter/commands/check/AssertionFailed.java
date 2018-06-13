package net.seibertmedia.jmeter.commands.check;

public class AssertionFailed extends Exception {
    public AssertionFailed() {
    }

    public AssertionFailed(String message) {
        super(message);
    }

}
