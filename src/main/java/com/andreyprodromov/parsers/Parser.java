package com.andreyprodromov.parsers;

public interface Parser {

    /**
     * @param environment
     *        the environment name where the script is located
     * @param args
     *        positional arguments that should be taken into consideration when parsing
     *
     * @return the parsed script
     *
     * @throws com.andreyprodromov.parsers.exceptions.ScriptDoesNotExistException
     *         when parsing in an environment without a script set
     * @throws com.andreyprodromov.parsers.exceptions.VariableIsNotSetException
     *         when parsing in an environment with a required variable that is not set
     */
    String parse(String environment, String[] args);

}
