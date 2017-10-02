package start

import com.nani.homecontroller.services.LifxService
import start.com.nani.homecontroller.Messaging

fun main(args: Array<String>) {
  print(args)
  println("in start")

  Messaging(host = "tcp://192.168.0.17:1883")
  
  while (true) {
    Thread.sleep(500)
  }

}
