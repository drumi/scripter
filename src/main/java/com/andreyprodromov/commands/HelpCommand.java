package com.andreyprodromov.commands;

import java.io.OutputStream;
import java.io.PrintStream;

public final class HelpCommand implements Command {

    private final String[] args;
    private final PrintStream outputStream;

    public HelpCommand(String[] args, OutputStream outputStream) {
        this.args = args;
        this.outputStream = new PrintStream(outputStream);
    }

    @Override
    public void execute() {
        outputStream.println(
            """
            scripter -c, --create <environment>
                Creates a new environment
                
            scripter -d, --delete <options>
                Deletes, where options is one of the following:
                    -gv, --global-variable <name>
                    -lv, --local-variable <environment> <name>
                    -env, --environment <environment>
                
            scripter -e, --execute <environment> <arg1> <arg2> ... <argN>
                Executes the script set in the <environment> with arguments passed as <argN>
                
            scripter -l, --list <options>
                Lists values, where <options> is one of the following:
                    -gv, --global-variables
                    -lv, --local-variables
                    -env, --environments
                    -s, --script <environment>
                    -ps, --parsed-script <environment> <arg1> <arg2> ... <argN>
                
            scripter -m, --modify <options>
                Modifies environment, where <options> is one of the following:
                    -slv, --set-local-variable <environment> <name> <value> 
                    -sgv, --set-global-variable <name> <value>
                    -ss,  --set-script <environment> <file>
            
            scripter -h, --help
                Lists help menu
            """
        );
    }

}
