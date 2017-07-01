package com.nani.homecontroller.services

interface HomeService {
     fun doAction(label:String, messagge: String) : ServiceResponse
}

class ServiceResponse (message: String, sender: String){
}
