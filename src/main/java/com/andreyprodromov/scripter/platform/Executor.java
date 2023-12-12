package com.andreyprodromov.scripter.platform;

public interface Executor {

    /**
     * @param cmd
     *        the command to be executed in the current OS
     *
     * @return the status code after command execution completes
     */
    int execute(String cmd);

}
