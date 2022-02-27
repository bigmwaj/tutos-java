Before all, install the redis server

1 - Download the zip of the redis server at https://github.com/microsoftarchive/redis/releases/tag/win-2.8.2104
2 - 


II - Follow these instruction to install on ubuntu

sudo apt-get update
sudo apt-get install redis-server
sudo nano /etc/redis/redis.conf
 // find the entry supervised. Its value is no and set it to systemd
 sudo systemctl restart redis.service