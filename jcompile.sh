#!/bin/bash

if [ $# -eq 0 ] ; then
    make
else
    make "ARGS="$@""
fi