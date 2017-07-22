package com.nani.homecontroller.services

import java.util.*
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream


class ADBService : HomeService{
    var attempNumber = 0
    var ip = "192.168.0.6"

    var kodi = "org.xbmc.kodi"
    var youtube = "org.xbmc.kodi"


    override fun doAction(label: String, messagge: String): ServiceResponse {
        var action = messagge.split(":")[0]
        if(action.equals("start", true) || action.equals("stop", ignoreCase = true)) {
            appAction(action = messagge.split(":")[0], app = messagge.split(":")[1])
        }

        println("" + label + "  " + messagge)
        return ServiceResponse("", "")
    }

    private fun  appAction(action: String, app: String) {
        attempNumber = 0
        if(reConnect()){
            runCommnad("sleep 2")
            println("SUCESFULLY RECONNECTED")
            if(action.equals("start", true)){
                when (app) {
                    "kodi" -> {
                        runCommnad("/usr/bin/adb shell am force-stop org.chromium.youtube_apk")
                        runCommnad("sleep 1")
                        runCommnad("/usr/bin/adb shell am start -n org.xbmc.kodi/.Splash")
                        runCommnad("/usr/bin/adb shell input keyevent 25")
                    }
                    "youtube" -> {
                        runCommnad("/usr/bin/adb shell am force-stop org.xbmc.kodi")
                        runCommnad("sleep 1")
                        runCommnad("/usr/bin/adb shell am start -n org.chromium.youtube_apk/.YouTubeActivity")
                        runCommnad("/usr/bin/adb shell input keyevent 25")
                    }
                }
            } else {
                when (app) {
                    "kodi" ->
                        runCommnad("/usr/bin/adb shell am force-stop org.xbmc.kodi")
                    "youtube" ->
                        runCommnad("/usr/bin/adb shell am force-stop org.chromium.youtube_apk")
                }

            }
        }else
            println("ERROR CONNECTIONG TPO ADB")

    }

    private fun reConnect() : Boolean {
       if(runCommnad("/usr/bin/adb kill-server").waitFor() == 0) {
           if (runCommnad("/usr/bin/adb start-server").waitFor() == 0) {
               var process = runCommnad("/usr/bin/adb connect " + ip)
               if (process.waitFor() == 0) {
                   return true
               }
           }
       }
        if(attempNumber < 4){
            println ("ADB connection failed trying again ATTEMPT NO" + attempNumber)
            attempNumber++
            runCommnad("sleep 2")
            reConnect()
        }

       return false
    }

    private fun runCommnad(command: String) : Process{
        val env = arrayOf("PATH=/bin:/usr/bin:")
        println ("running command " + command)
        var proccess = Runtime.getRuntime().exec(command, env)
        Runtime.getRuntime().exec("sleep 2", env)
        println(output(proccess.inputStream))
       return proccess
    }



    @Throws(IOException::class)
    private fun output(inputStream: InputStream): String {
        val sb = StringBuilder()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(InputStreamReader(inputStream))
            var line: String? = null
            line = br.readLine()
            while (line != null) {
                sb.append(line!! + System.getProperty("line.separator"))
                line = br.readLine()
            }
        } finally {
            br!!.close()
        }
        return sb.toString()
    }
}
