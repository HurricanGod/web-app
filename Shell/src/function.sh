#!/bin/sh
#打开expand_aliases选项
#list=("1" "2" "3" "4" "5" "1" "2" "3")
list=(1 2 3 4 5 1 2 1)
array=(Hello World)
#list=(server_7080 server_7180 server_7280 server_7380 server_7480 server_7380 server_7480)
set=`{ for i in ${list[*]} ${list[*]}; do echo  ${i}; done;}`
echo -e "set = \n${set[*]}"
echo -e "set.length = ${#set[*]}"
function foreach()
{
    array=$1
    echo "array.length = ${#array[*]}"
    for ele in ${array}
    do
        echo ${ele}
    done

}

foreach "${list[*]}"

echo "uniq...."
set=`{ for i in ${list[*]}; do echo  ${i}; done;} | sort | uniq`

echo "set.length = ${#set[*]}"
echo "set = ${set[*]}"
for member in ${set[*]}
do
    echo "member = ${member}"
    echo "member.length = ${#member}"
done

