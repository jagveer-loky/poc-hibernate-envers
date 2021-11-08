#!/bin/ksh
####################################################
# Shell script to be for building by CM
####################################################


export TOOL_HOME=/opt/escm/acm/tool
export JAVA_HOME=$TOOL_HOME/java/jdk1.8.0_152
export ANT_HOME=$TOOL_HOME/ant/ant_1.8.2

PATH=/opt/escm/acm/acm_cli/bin:$PATH
export PATH

cd ..

# Build
mvn clean package -Puat -Dbuild.number=1

# Copy to package
cp target/pre-proposal-api.war package/pre-proposal-api.war

cp build/pre-proposal-api.properties package/pre-proposal-api.properties