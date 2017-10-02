package com.nani.homecontroller.services

import com.nani.homecontroller.managers.PIProcess

class ADBService : HomeService{
    var attempNumber = 0
    var ip = "192.168.0.6"

    var kodi = "org.xbmc.kodi"
    var youtube = "org.xbmc.kodi"


    override fun doAction(label: String, messagge: String): ServiceResponse {
        var action = messagge.split(":")[0]
        println("LABEL:" + label + "   MESSAGE:" + messagge)
        if(action.equals("start", true) || action.equals("stop", ignoreCase = true)) {
            appAction(action = messagge.split(":")[0], app = messagge.split(":")[1])
        }
        if(action.equals("restart")){
            restartFS()
        }

        return ServiceResponse("", "")
    }

    private fun restartFS(){
        attempNumber = 0
        var process = PIProcess()
        if(process.connectAdb(ip)) {
            Thread.sleep(2000)
            process.runCommand("/usr/bin/adb reboot")
        }
    }

    private fun  appAction(action: String, app: String) {
        attempNumber = 0
        var process = PIProcess()
        if(process.connectAdb(ip)){
            process.runCommand("sleep 2")
            println("SUCESFULLY RECONNECTED")
            if(action.equals("start", true)){
                if(app.equals("kodi", true)){
                    process.runCommand("/usr/bin/adb shell am force-stop org.chromium.youtube_apk")
                    process.runCommand("sleep 1")
                    process.runCommand("/usr/bin/adb shell am start -n org.xbmc.kodi/.Splash")
                    process.runCommand("/usr/bin/adb shell input keyevent 25")
                }else {
                    process.runCommand("/usr/bin/adb shell am force-stop org.xbmc.kodi")
                    process.runCommand("sleep 1")
                    process.runCommand("/usr/bin/adb shell am start -n org.chromium.youtube_apk/.YouTubeActivity")
                    process.runCommand("/usr/bin/adb shell input keyevent 25")

                }
            } else {
                when (app) {
                    "kodi" ->
                        process.runCommand("/usr/bin/adb shell am force-stop org.xbmc.kodi")
                    "youtube" ->
                        process.runCommand("/usr/bin/adb shell am force-stop org.chromium.youtube_apk")
                }

            }
        }else
            println("ERROR CONNECTING TO ADB")

    }

}
