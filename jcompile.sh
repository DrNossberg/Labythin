#!/bin/bash

if [ $# -eq 0 ] ; then
    make
else
    make
    java -cp bin:lib/picocli-4.6.1.jar Labythin $@
fi