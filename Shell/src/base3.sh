#!/usr/bin/env bash

[ "$1" = "" ] && { echo "exec the sh file need provide search keyword for the first args"; exit -1;}
[ "$2" = "" ] && { echo "exec the sh file need provide log filename for the second args"; exit -1;}
out_put_file=$3
[ "${out_put_file}" = "" ] && { echo "shell exec result will output to ${out_put_file},"\
" because output file name is empty, so exec result will output to ./result.log";out_put_file="./result.log";}
echo '${out_put_file}='${out_put_file}
# 模拟 ps 命令输出正在运行中的 tomcat 路径
item_server=`echo -e "server_7130/tomcat/webapps/start.sh \n"\
        "server_7140/tomcat/webapps/start.sh \n"\
        "server_7240/tomcat/webapps/start.sh" | grep -P 'server_[0-9]+' -o`
echo ${item_server}
# 定义一个数组存放多个 Tomcat 实例的启动路径
declare -a start_sh
start_sh[0]="/home/hurrican/tomcat8_1/tomcat8.5/bin/startup.sh"
start_sh[1]="/home/hurrican/tomcat8_2/tomcat8.5/bin/startup.sh"
echo "start_sh is ${start_sh[*]}"

declare -A start_sh_map
start_sh_map["1"]=${start_sh[0]}
start_sh_map["2"]=${start_sh[1]}
echo ${start_sh_map["1"]}
echo ${start_sh_map["2"]}

declare -a running_instance
declare -A log_path_map1
log_path_map1["server_7040"]="/mnt/website/youyu/server_7040/ROOT/logs/web/"
log_path_map1["server_7140"]="/mnt/website/youyu/server_7140/ROOT/logs/web/"
log_path_map1["server_7240"]="/mnt/website/youyu/server_7240/ROOT/logs/web/"
log_path_map1["server_7340"]="/mnt/website/youyu/server_7340/ROOT/logs/web/"
log_path_map1["server_7440"]="/mnt/website/youyu/server_7440/ROOT/logs/web/"

# item_log_path 存放正在运行中的 Tomcat 实例业务日志目录
declare -a item_log_path

echo -e "\n"
echo -e "start list item tomcat instance...\n"
for server in ${item_server}
do
echo "running item tomcat is ${server}"
echo ${server} | tr cd "[0-9]"
# 往 list 里 append() 一个日志路径
item_log_path=(${item_log_path[*]} ${log_path_map1[${server}]})
done
echo -e "\n"

echo -e "finish iterate running tomcat server, item_log_path = \n${item_log_path[*]}\n"
for log in ${item_log_path[*]}
do
    if [ -d ${log} ]
    then
        { echo "log file path is ${log}$2";echo -e "\n";}
        grep -2 $1 "${log}$2" >> ${out_put_file}
    else
        echo "${log} is not exist"
    fi
done