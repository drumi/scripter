# Scripter
Small Windows utility tool to deal with repeating tasks
## Commands
```
scripter -c, --create <environment> 
	Creates a new environment
	
scripter -d, --delete <options>
	Deletes, where options is one of the following
		-gv, --global-variable <name>
		-lv, --local-variable <environment> <name>
		-env --environment <environment>
	
scripter -e, --execute <environment> <arg1> <arg2> ... <argN>
	Executes the script set in the <environment> with arguments passed as <argN>
	
scripter -l, --list <options>
	Lists values, where <options> is one of the following:
		-gv, --global-variables
		-lv, --local-variables
		-env --environments
	
scripter -m, --modify <options>
	Modifies environment, where <options> is one of the following:
		-slv, --set-local-variable <environment> <name> <value> 
		-sgv, --set-global-variable <name> <value>
		-ss,  --set-script <environment> <file>

scripter -h, --help
	Lists help menu
```

## Example Usage
```
scripter -c media
scripter -m -sgv player "C:\misc\MediaPlayer.exe"
scripter -m -ss media script.txt
scripter -e media song.mp3
```

## Example Script
```
%[player]% play --high-quality %[1]%
%[tracker]% --register --no-remove %[1]%
%[manager]% --change-stream %[music-stream]%
```

Variables are accessed by enclosing them with `%[]%`, for example `%[var]%`     
Command-line arguments are accessed by `%[N]%`, where N >= 1
