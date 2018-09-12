#!/bin/sh
#打开expand_aliases选项
shopt -s expand_aliases

declare -A array
array["7080"]="server_7080"
array["7180"]="server_7180"
array["7280"]="server_7280"
array["7380"]="server_7380"
array["7580"]="server_7580"

list=(server_7080 server_7180 server_7280 server_7380 server_7480)

# 合并两个数组
merge=(${list[*]} ${array[*]})

# 求交集
echo -e "\n交集:array & list"
{ for i in ${merge[*]}; do echo ${i}; done;} | sort | uniq -d


# 求并集
echo -e "\n并集:array | list"
{ for i in ${merge[*]}; do echo ${i}; done;} | sort | uniq


# 求差集 array - list
echo -e "\n差集:array - list"
absolute_set=`{ for i in ${merge[*]}; do echo ${i}; done;} | sort | uniq -u`
intersection=`{ for i in ${merge[*]}; do echo ${i}; done;} | sort | uniq -d`
diff_set=`{ for i in ${list[*]} ${intersection[*]}; do echo ${i}; done;} | sort | uniq -u`
diff_set=`{ for i in ${diff_set[*]} ${absolute_set[*]}; do echo ${i}; done;} | sort | uniq -u`

echo -e "${diff_set[*]}"