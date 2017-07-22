package start.com.nani.homecontroller;

import com.nani.homecontroller.services.ADBService
import com.nani.homecontroller.services.HomeService
import com.nani.homecontroller.services.IRService
import com.nani.homecontroller.services.LifxService
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.MqttClient
import java.util.*

class Messaging(host: String) : MqttCallback{
    val services = HashMap<String, HomeService>()

    var client = MqttClient(host, "Sending")
    init {
        services.put("home/adb", ADBService())
        services.put("home/lifx", LifxService())
        services.put("home/ir", IRService())
        setListners(client, host)
    }

    override fun connectionLost(cause: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun messageArrived(topic: String, message: MqttMessage) {
        var service = services.get(topic)
        if(service != null) services.get(topic)!!.doAction(topic, message.toString())
        else print("SERICE NOT FOUUND")
        println("***Received MESSAGE**")
        println(message.toString())
        setListners(client, client.serverURI)
    }

    fun setListners(clineast: MqttClient , host:String) {
        println("Reconnecting to message server")
        client = MqttClient(host, "Sending")
        client.connect()
        client.setCallback(this)
        for(item in services.keys){
            println("Subscribing to channel " + item)
            client.subscribe(item)
        }
    }

}
