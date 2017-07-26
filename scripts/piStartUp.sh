#!/bin/bash

kill -9 $(pidof node)
/usr/local/bin/forever stopall

cd /home/nanipi/work/Home_Contorller/alexa_skill

/usr/local/bin/forever --workingDir /home/nanipi/work/Home_Contorller/alexa_skill/ start -c /usr/bin/node /home/nanipi/work/Home_Contorller/alexa_skill/server.js

cd /home/nanipi/work/Home_Contorller/home_controller

/usr/local/bin/forever --workingDir /home/nanipi/work/Home_Contorller/home_controller/ start -c /usr/bin/node /home/nanipi/work/Home_Contorller/home_controller/server.js

/bin/bash /home/nanipi/work/habridge/ha-bridge.sh
