#!/bin/sh
let j=6*10+5
echo ${j}
((j=6*100+5))
echo ${j}

int_list={1..100}
echo ${int_list}
echo $(seq 10)


