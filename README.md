# KMenu

### Warning

Because jetpack compose targets the jvm, this program might take up around 200 MB of memory when running.  I would not recommend using this if you have extreme memory limitations on your machine.

### Run for testing

`./gradlew :kmenu:run`

### Install

`./buildAndInstall.sh`

### Uninstall

`./uninstall.sh`

### Customizing

The files you will likely be interested in are located in `kmenu/src/main/java/io/obez/kmenu`    
Check out `theme/AppTheme` for colors    
Check out `system/Commands` for your system commands