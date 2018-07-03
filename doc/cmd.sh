PROG=ring-service
PIDFILE=ring.pid
JAVA_OPTS="-Xms768m -Xmx768m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Duser.timezone=GMT+08  -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -Xloggc:gc.log -XX:+DisableExplicitGC"

status()
{
        if [ -f $PIDFILE ]; then
                PID=$(cat $PIDFILE)
        if [ ! -x /proc/${PID} ]; then
                return 1
        else
                return 0
        fi
        else
                return 1
        fi
}

case "$1" in
        start)
                status
                RETVAL=$?
                        if [ $RETVAL -eq 0 ]; then
                                echo "$PIDFILE exists, process is already running or crashed"
                                exit 1
                        fi
                echo "Starting $PROG ..."
                jar  -uvf ring.jar BOOT-INF/classes/*.properties BOOT-INF/classes/*.xml
                java $JAVA_OPTS  -jar ring.jar >/dev/null &
                        RETVAL=$?
                        if [ $RETVAL -eq 0 ]; then
                                echo "$PROG is started"
                                echo $! > $PIDFILE
                                exit 0
                        else
                                echo "Stoping $PROG"
                                rm -f $PIDFILE
                                exit 1
                        fi
                ;;
        stop)
                status
                RETVAL=$?
                        if [ $RETVAL -eq 0 ]; then
                                echo "Shuting down $PROG"
                                PID=$(cat $PIDFILE)
                                kill -9 $PID
                                RETVAL=$?
                                if [ $RETVAL -eq 0 ]; then
                                        rm -f $PIDFILE
                                else
                                        echo "Failed to stopping $PROG" 
                                fi
                        fi
                ;;
        status)
                status
                RETVAL=$?
                        if [ $RETVAL -eq 0 ]; then
                                PID=$(cat $PIDFILE)
                                echo "$PROG is running ($PID)"
                        else
                                echo "$PROG is not running"
                        fi
                ;;
        restart)
                $0 stop
                $0 start
                ;;
        *)
                echo "Usage : $0 {start | stop | restart | status}"
                ;;
esac
