package com.nani.homecontroller.managers

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Process

class PIProcess {

    fun connectAdb(ip : String, totalAttempts : Int = 3) : Boolean {
        var result = doReconnect(ip)
        if(!result){
            for(i in 1..totalAttempts){
                var result = doReconnect(ip)
                if(result){
                    return true
                }

            }
        }else{
            return true
        }
        return false
    }

    fun runCommand(command: String, waitTime: Int=3) : Process {
        val env = arrayOf("PATH=/bin:/usr/bin:")
        println ("running command " + command)
        var proccess = Runtime.getRuntime().exec(command, env)
        Runtime.getRuntime().exec("sleep 2", env)
        println(output(proccess.inputStream))
        return proccess
    }

    private fun doReconnect(ip : String) : Boolean {

        if(runCommand("/usr/bin/adb kill-server").waitFor() == 0) {
            if (runCommand("/usr/bin/adb start-server").waitFor() == 0) {
                var process = runCommand("/usr/bin/adb connect " + ip)
                if (process.waitFor() == 0) {
                    return true
                }
            }
        }
        return false
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