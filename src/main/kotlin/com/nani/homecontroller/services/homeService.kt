package com.nani.homecontroller.services

interface HomeService {
     fun doAction(label:String, mesagge: String) : ServiceResponse
}
class ServiceResponse (message: String, sender: String){
}
