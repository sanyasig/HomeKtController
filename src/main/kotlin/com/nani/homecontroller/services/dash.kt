package com.nani.homecontroller.services

class DashService : HomeService {
    override fun doAction(label: String, message: String): ServiceResponse {
        if(message.equals("arial-button")) {
            LifxService().doAction("/home/lifx", "turnoff")
        }
        return ServiceResponse("tets", "test")
    }
}