#!/usr/bin/env bash

str="Hurrican@163.com"
echo ${str#Hurrican}
echo ${str#*@}

echo -e "\nDemo2\n"
path="/home/hurrican/hello.sh"
echo "path = ${path}"
echo '${path#*/} = '${path#*/}
echo '${path##*/} = '${path##*/}

echo '${path%/*} = '${path%/*}
echo '${path%%/*} = '${path%%/*}

array=(1 2 3 4 5)
echo 'array.length = '${#array[*]}
echo 'array.length = '${#array[@]}
unset array[0]
echo 'array = '${array[*]}
echo 'array.length = '${#array[*]}

