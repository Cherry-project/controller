echo "starting cherry controller server";
if ls journal 2>/dev/null;then : ;else mkdir journal;fi
nohup java -jar $* > journal/`date +%Y-%m-%d`.log 2>&1 & echo $! > save_pid.txt ;
echo "server up and running, pid : " `cat save_pid.txt`;