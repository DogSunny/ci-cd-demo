kill `cat pid`
nohup java -jar app.jar >/dev/null 2>&1 & echo $! > pid