package net.seibertmedia.jmeter;

import java.io.IOException;

import net.seibertmedia.jmeter.commands.AppCommand;
import net.seibertmedia.jmeter.commands.AppCommandException;

public class App {

    public static void main(String[] args) throws IOException {
        try {
            AppCommand command = AppCommand.parseCommand(args);
            command.execute(args);
        } catch (AppCommandException e) {
            System.out.println("Usage: <command> <filename>\n");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
