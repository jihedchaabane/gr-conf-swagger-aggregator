# gr-conf-swagger-aggregator

* Vérifiez que http://localhost:8765/sse/eureka-updates est accessible (par exemple, ouvrez l’URL dans un navigateur pour voir le flux SSE).


# gr-conf-swagger-aggregator
-----------------------------------------------------
http://localhost:8765/swagger-ui/index.html
http://10.0.0.137:8765/swagger-ui/index.html

curl http://10.0.0.137:8765/hello
-----------------------------------------------------
IN "10.0.0.137" do:
-----------------------------------------------------
sudo firewall-cmd --add-port=8765/tcp --permanent
sudo firewall-cmd --reload

sudo firewall-cmd --list-ports
sudo firewall-cmd --list-all

---Fermer le port 8765 -------------------------------
sudo firewall-cmd --permanent --remove-port=8765/tcp
sudo firewall-cmd --reload

sudo firewall-cmd --list-ports
sudo firewall-cmd --list-all


