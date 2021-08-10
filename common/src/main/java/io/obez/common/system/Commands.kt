package io.obez.common.system

import kotlin.system.exitProcess

object Commands {

    fun lock() {
        CommandRunner.run(
            "swaylock -i /ssd/home/workspace/Varda-Theme/wallpaper/varda.jpg" +
                    " --indicator-idle-visible --font \"JetBrainsMono Patched Glyphs\"" +
                    " --indicator-thickness 5" +
                    " --indicator-radius 100" +
                    " --key-hl-color 257B76" +
                    " --inside-color 0C0E11" +
                    " --inside-ver-color 0C0E11" +
                    " --inside-wrong-color 0C0E11" +
                    " --ring-color 52677C" +
                    " --ring-ver-color 257B76" +
                    " --ring-wrong-color 733447" +
                    " --separator-color 0C0E11" +
                    " --text-color D0EBEE" +
                    " --text-ver-color D0EBEE" +
                    " --text-wrong-color D0EBEE" +
                    " -n"
        )
        exitProcess(0)
    }

    fun sleep() {
        CommandRunner.run("systemctl suspend")
        exitProcess(0)
    }

    fun logout() {
        CommandRunner.run("swaymsg exit")
        exitProcess(0)
    }

    fun restart() {
        CommandRunner.run("reboot")
        exitProcess(0)
    }

    fun shutdown() {
        CommandRunner.run("shutdown now")
        exitProcess(0)
    }

    fun close() {
        exitProcess(0)
    }

    fun gtkLaunch(app: DesktopFileInfo) {
        println(app.name.substringBefore(".desktop"))
        if (app.programName == "Android Studio" || app.programName == "IntelliJ IDEA Community") {
            CommandRunner.run("export _JAVA_AWT_WM_NONREPARENTING=1 && gtk-launch ${app.name.substringBefore(".desktop")}")
        } else {
            CommandRunner.run("gtk-launch ${app.name.substringBefore(".desktop")}")
        }
        exitProcess(0)
    }
}