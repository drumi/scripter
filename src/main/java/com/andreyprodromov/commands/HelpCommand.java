package com.andreyprodromov.commands;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Command for outputting help menu.
 */
public final class HelpCommand implements Command {

    private final PrintStream outputStream;

    /**
     * @param outputStream the output stream where the help menu will be written
     */
    public HelpCommand(OutputStream outputStream) {
        this.outputStream = new PrintStream(outputStream);
    }

    @Override
    public void execute() {
        outputStream.println(
            """
            scripter -c, --create <env>
                Creates a new environment <env>
                
            scripter -cl, --clone <env> <new_env>
                Creates a new environment <new_env> which is a clone of <env>
                
            scripter -d, --delete {options}
                Deletes, where {options} is one of the following:
                    -gv, --global-variable <var>
                    -lv, --local-variable <env> <var>
                    -s, --script <env>
                    -env, --environment <env>
                
            scripter -e, --execute <env> <args>...
                Executes the script set in the environment <env> with arguments passed as <args>...
                
            scripter -l, --list {options}
                Lists values, where {options} is one of the following:
                    -gv, --global-variables
                    -lv, --local-variables
                    -env, --environments
                    -s, --script <env>
                    -ps, --parsed-script <env> <args>...
                
            scripter -m, --modify {options}
                Modifies, where {options} is one of the following:
                    -slv, --set-local-variable <env> <var> <value>
                    -sgv, --set-global-variable <var> <value>
                    -ss,  --set-script <env> <file>
            
            scripter -h, --help
                Lists help menu
            """
        );
    }

}
