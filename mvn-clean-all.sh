#!/usr/bin/env bash

#
# executes mvn clean for all sub directories containing a pom.xml
#

find -maxdepth 1 -mindepth 1 -type d -exec mvn -f '{}/pom.xml' clean \;