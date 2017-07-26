#! /bin/bash
sudo mount /dev/sda1 /home/pi/drive/ -o uid=pi,gid=pi
sudo /usr/sbin/lircd -d /dev/lirc0
