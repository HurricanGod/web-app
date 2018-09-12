#!/bin/sh
#打开expand_aliases选项
shopt -s expand_aliases
declare -A map
map["7080"]="server_7080"
map["7180"]="server_7180"
map["7280"]="server_7280"
map["7380"]="server_7380"

# 输出所有 key
echo ${!map[@]}

# 遍历所有 key
for key in ${!map[@]}
do
    echo "value = ${key}"
    echo "value = ${map[$key]}"
    echo ""
done

alias ll="ls -l"
ll

