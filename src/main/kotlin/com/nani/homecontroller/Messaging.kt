package start.com.nani.homecontroller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.MqttClient

class Messaging(host: String) : MqttCallback{

    val listenTopics = listOf("home/adb",
                              "home/lifx")

    var client = MqttClient(host, "Sending")

    init {
        setListners(client, host)
    }

    override fun connectionLost(cause: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        var actionCall = {};
        when (topic) {
            "home/adb" -> {
                actionCall = {
                    println("in the action call")
                }
            }
             else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }


        println("***Received MESSAGE**")
        println(message.toString())
        setListners(client, client.serverURI)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun setListners(clinet: MqttClient , host:String) {
        client = MqttClient(host, "Sending")
        client.connect()
        client.setCallback(this)
        for(item in listenTopics){
            client.subscribe(item)
        }
    }


}
