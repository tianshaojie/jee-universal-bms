#!/bin/sh
BASEDIR="$(dirname $(readlink -f $(dirname "$0")))"

profile=""
port=""
if [ $# -eq 2 ] ; then
   profile="-Dspring.profiles.active=$1"
   port="-Dserver.port=$2"
   if [ $1 == "dev" ] ; then
      cp -f  $BASEDIR/config/dubbo-business-site.xml.dev $BASEDIR/config/dubbo-business-site.xml
   elif [ $1 == "test" ] ; then
      cp -f  $BASEDIR/config/dubbo-business-site.xml.test $BASEDIR/config/dubbo-business-site.xml
   elif [ $1 == "online" ] ; then
      cp -f $BASEDIR/config/dubbo-business-site.xml.online $BASEDIR/config/dubbo-business-site.xml
   fi
fi

str=`jps | grep -i "BusinessSiteWebApplication" | awk -F ' ' '{print $1}'`
if [ ! -n "$str" ]; then
        echo "site-web process has been dead"
else
        echo "kill site-web processId: $str"
        kill -9 $str
fi

nohup java $profile $port -cp $BASEDIR:$BASEDIR/config:$BASEDIR/lib/*:.  com.yuzhi.BusinessSiteWebApplication >> /yuzhi/log/site/console.log 2>&1 &


str=`jps | grep -i "BusinessSiteWebApplication" | awk -F ' ' '{print $1}'`
if [ ! -n "$str" ]; then
        echo "site-web process don't restart"
else
        echo "restart site-web processId: $str"
fi