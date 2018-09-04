#!/bin/sh
declare -a start_sh
start_sh[0]="/home/hurrican/tomcat8_1/tomcat8.5/bin/startup.sh"
start_sh[1]="/home/hurrican/tomcat8_2/tomcat8.5/bin/startup.sh"
# 注意：一定要用 -A 选项
declare -A start_sh_map
# 往 map 放元素
start_sh_map["1"]=${start_sh[0]}
start_sh_map["2"]=${start_sh[1]}

# 从 map 中取元素
echo ${start_sh_map["1"]}
echo ${start_sh_map["2"]}

#
echo 'start_sh_map.size = '${#start_sh_map[@]}
unset start_sh_map["1"]
echo 'start_sh_map.size = '${#start_sh_map[*]}

declare -a list
list=(1 2 3 4 5 1)
echo 'current list is: '${list[*]}
list=(${list[*]} 6)
echo 'after append,the list is: '${list[*]}

# 删除 list 中的元素
unset list[5]
echo 'after delete,the list is: '${list[*]}

string="Hello Shell..."
echo ${string:0:5}
echo ${string:5:5}
echo '${string:5:} = '${string:5:}
echo '${string:5:-1} = '${string:5:-1}
echo '${string:5:-2} = '${string:5:-2}

email="Hurrican@163.com_@163.com"
str="the...the"
echo 'email is '${email}
echo '${email/@163.com/qq.com} = '${email/163.com/qq.com}
echo ${str/163.com/qq.com}
echo -e "\n"
echo '${email//@163.com/qq.com} = '${email//163.com/qq.com}
