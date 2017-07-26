from pydhcplib.dhcp_network import *
import paho.mqtt.publish as publish


def switch_off():
	print("button has been pressed arial-button")
	publish.single("home/dash", "arial-button", hostname="192.168.0.17")

def switch_on():
	print("button has been pressedi blank-button")
	publish.single("home/dash", "blank-button", hostname="192.168.0.17")


netopt = {'client_listen_port':"68", 'server_listen_port':"67", 'listen_address':"0.0.0.0"}

class Server(DhcpServer):
	def __init__(self, options, dashbuttons):
		DhcpServer.__init__(self, options["listen_address"],
								options["client_listen_port"],
								options["server_listen_port"])
		self.dashbuttons = dashbuttons

	def HandleDhcpRequest(self, packet):
		mac = self.hwaddr_to_str(packet.GetHardwareAddress())
		self.dashbuttons.press(mac)


	def hwaddr_to_str(self, hwaddr):
		result = []
		hexsym = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']
		hexsym = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']
		for iterator in range(6) :
			result += [str(hexsym[hwaddr[iterator]/16]+hexsym[hwaddr[iterator]%16])]
		return ':'.join(result)

class DashButtons():
	def __init__(self):
		self.buttons = {}

	def register(self, mac, function):
		self.buttons[mac] = function

	def press(self, mac):
		print
		if mac in self.buttons:
			self.buttons[mac]()
			return True
		return False

		
dashbuttons = DashButtons()
dashbuttons.register("ac:63:be:31:1e:2c", switch_off)
dashbuttons.register("ac:63:be:9d:fb:a9", switch_on)

server = Server(netopt, dashbuttons)

while True :
    server.GetNextDhcpPacket()
