package net.seibertmedia.jmeter.commands;


import static net.seibertmedia.jmeter.commands.AppCommandParameter.createParameter;

import java.util.HashMap;
import java.util.Map;

import net.seibertmedia.jmeter.commands.check.AssertionFailed;
import net.seibertmedia.jmeter.commands.check.CheckCommandFunction;

public enum AppCommand {
    report("Create a report", new ReportCommandFunction(), createParameter("filename")),
    check("Check", new CheckCommandFunction(), createParameter("filename"), createParameter("assertions-filename"));

    private final String description;
    private final AppCommandFunction commandFunction;
    private final AppCommandParameter[] params;

    AppCommand(String description, AppCommandFunction commandFunction, AppCommandParameter... params) {
        this.description = description;
        this.commandFunction = commandFunction;
        this.params = params;
    }

    public void execute(String[] args) throws AppCommandException, AssertionFailed {
        try {
            final Map<String, String> commandParams = new HashMap<>();

            for (int i = 0; i < params.length; i++) {
                commandParams.put(params[i].getParameterName(), args[i + 1]);
            }

            commandFunction.apply(commandParams);
        } catch (AssertionFailed e) {
            throw e;
        } catch (Exception e) {
            throw new AppCommandException(e);
        }
    }

    public static AppCommand parseCommand(final String[] args) throws AppCommandException {
        if (args.length < 1) {
            throw new AppCommandException("Command parameter is missing");
        }

        final String command = args[0];
        try {
            return AppCommand.valueOf(command);
        } catch (IllegalArgumentException e) {
            throw new AppCommandException("Command " + command + " not found", e);
        }
    }
}
