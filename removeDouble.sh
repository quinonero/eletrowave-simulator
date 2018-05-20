#!/bin/bash


echo $(grep -vf init.txt out.txt | sed 's/$'"/`echo \\\r`/" ) > init2.txt
