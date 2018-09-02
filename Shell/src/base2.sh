#!/usr/bin/env bash
declare -a list=(1 2 3 4 5 6)
echo "first element is ${list[0]}"
echo "list[@] is ${list[@]}"
echo "list[*] is ${list[*]}"

List_String=(Hello My World)
echo "Origin List_String is ${List_String[*]}"
unset ${List_String[*]}
echo "After Unset List_String is ${List_String[*]}"
