# KMenu

![screenshot](images/screenshot.png)

### Warning

Because jetpack compose targets the jvm, this program might take up around 200 MB of memory when running.  I would not recommend using this if you have extreme memory limitations on your machine.

### Run for testing

`./gradlew :kpower:run`

### Install

`./buildAndInstall.sh`

### Uninstall

`./uninstall.sh`

### Customizing

The files you will likely be interested in are located in `kpower/src/main/java/io/obez/kpower`    
Check out `theme/AppTheme` for colors    
Check out `system/Commands` for your system commands

### Recommended sway configs

If you are using sway, add this to your sway configs for the best experience


    for_window [title="KPower - power options"] floating enable
    for_window [title="KPower - power options"] floating_modifier none
