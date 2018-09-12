#!/bin/sh
set=(1 2 3 4 5 6)

# 值表方式遍历集合
for ele in ${set[*]}
do
	echo ${ele}
done

echo -e "\n- - - - - - - - - - - - - - - - - - - -\n"
# 算术表达式方式遍历集合
for((i=0;i<${#set[*]};i++))
do
	echo ${set[i]}
done
