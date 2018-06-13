package net.seibertmedia.jmeter.commands;

import java.util.Map;

@FunctionalInterface
public interface AppCommandFunction {
    void apply(Map<String, String> params) throws Exception;
}
