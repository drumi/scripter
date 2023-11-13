# Scripter
Small Windows utility tool to deal with repeating tasks.
## Commands
```
scripter -e, --execute <environment> <arg1> <arg2> ... <argN>
    Executes script set in the <environment> with arguments passed as <argN>

scripter -c, --create
    Creates a new environment

scripter -d, --delete
    Deletes an environment if exists

scripter -m, --modify <options>
    Modifies environment, where <options> is one of the following:
        -slv, --set-local-variable <environment> <name> <value>
        -sgv, --set-global-variable <name> <value>
        -ss,  --set-script <environment> <file>

scripter -h, --help
    Lists help menu
```

Variables are accessed by enclosing them with `%[]%`, for example `%[var]%`
