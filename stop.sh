echo "Stopping the server"
if kill -9 `cat save_pid.txt` 2>/dev/null ;
then rm save_pid.txt & echo "server process successfully killed";
else echo "no server running";
fi;