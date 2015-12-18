#!/bin/sh
BASEDIR="$(dirname $(readlink -f $(dirname "$0")))"

profile=""
port=""
if [ $# -eq 2 ] ; then
   profile="-Dspring.profiles.active=$1"
   port="-Dserver.port=$2"
fi

str=`jps | grep -i "BmsApplication" | awk -F ' ' '{print $1}'`
if [ ! -n "$str" ]; then
        echo "back-site process has been dead"
else
        echo "kill back-site processId: $str"
        kill -9 $str
fi

nohup java $profile $port -server -Xmx2048m -cp $BASEDIR:$BASEDIR/config:$BASEDIR/lib/*:.  com.yuzhi.back.BmsApplication >> /data0/log/back/console.log 2>&1 &


str=`jps | grep -i "BmsApplication" | awk -F ' ' '{print $1}'`
if [ ! -n "$str" ]; then
        echo "back-web process don't restart"
else
        echo "restart back-web processId: $str"
fi