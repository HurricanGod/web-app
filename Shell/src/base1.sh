#!/usr/bin/env bash
echo "Hello `ls -l`"
TODAY="`date`"
echo "today is $TODAY"
echo 'today is $TODAY'
echo ""
OUT_STR="`ls -l \`pwd\``"
echo ${OUT_STR}
OUT_STR="This is not a new\
        line"
echo ${OUT_STR}
{ ls -l;cd ..;ls -l;}