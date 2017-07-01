package com.nani.homecontroller.services

class LifxService : HomeService {

    override fun doAction(label: String, messagge: String): ServiceResponse {
        return ServiceResponse("tets", "test")
    }

}
