package com.nani.homecontroller.services

import com.nani.homecontroller.managers.PIProcess

class IRService : HomeService{
    override fun doAction(label: String, messagge: String): ServiceResponse {
        var process = PIProcess()
        if (messagge.split(":")[0].equals("tvon",true) || messagge.split(":")[0].equals("tvoff", true)){
            println("TOGGLE TV STATE")
            process.runCommand("irsend SEND_ONCE SAMSUNG_AA59-00600A_POWER KEY_POWER KEY_POWER")
        }


    return ServiceResponse("", "")
    }

}