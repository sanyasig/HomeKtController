package start

import org.eclipse.paho.client.mqttv3.MqttClient
import start.com.nani.homecontroller.Messaging

fun main(args: Array<String>) {
  println("in start")
  Messaging(host = "tcp://192.168.0.17:1883")

  while (true) {
    Thread.sleep(500)
  }

}
