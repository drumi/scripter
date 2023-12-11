package com.andreyprodromov.scripter.platform;

public interface Executor {

    /**
     * @param cmd
     *        the command to be executed in the current OS
     */
    void execute(String cmd);

}
