package net.seibertmedia.jmeter.commands;


import static net.seibertmedia.jmeter.commands.AppCommandParameter.createParameter;

import java.util.HashMap;
import java.util.Map;

public enum AppCommand {
    report("Create a report", new ReportCommandFunction(), createParameter("filename"));

    private final String description;
    private final AppCommandFunction commandFunction;
    private final AppCommandParameter[] params;

    AppCommand(String description, AppCommandFunction commandFunction, AppCommandParameter... params) {
        this.description = description;
        this.commandFunction = commandFunction;
        this.params = params;
    }

    public void execute(String[] args) throws AppCommandException {
        try {
            final Map<String, String> commandParams = new HashMap<>();

            for (int i = 0; i < params.length; i++) {
                commandParams.put(params[i].getParameterName(), args[i + 1]);
            }

            commandFunction.apply(commandParams);
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
