# Scripter
Small utility tool for managing scripts
## Commands
```
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
        
scripter -v, --version
    Lists program's version

scripter -h, --help
    Lists help menu
```

## Example Usage
```cmd
scripter -c media
scripter -m -sgv player "C:\misc\MediaPlayer.exe"
scripter -m -ss media script.txt
scripter -e media song.mp3
```

## Example Script
```cmd
%[player]% play --high-quality %[1]% &&
%[tracker]% --register --no-remove %[1]% &&
%[manager]% --change-stream %[music-stream]%
```

Variables are accessed by enclosing them with `%[]%`, for example `%[var]%`     
Command-line arguments are accessed by `%[N]%`, where N >= 1
