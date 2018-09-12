#!/bin/sh
current=`date "+%s"`
echo "current_str = "`date +"%y-%m-%d %H:%M:%S"`
echo "current = ${current} s"
((yesterday = ${current} - 3600 * 24))
echo "yesterday = ${yesterday} s"
yesterday_str=`date -d @${yesterday} +"%y-%m-%d %H:%M:%S"`
echo "yesterday_str = ${yesterday_str}"
