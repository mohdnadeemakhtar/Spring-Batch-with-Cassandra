#!/bin/sh

#if [ -z "$1" ]; then
#    echo "please provide the name"
#    exit 1
#else
#    repo="pierone.stups.zalan.do"
#    team="dz"
#    artifactname="cassandra"
#    image=$repo/$team/$artifactname:$1
#    docker run -d -p 8080:8080 $image
#fi

java -jar /opt/docker/dataMigration-0.0.1-SNAPSHOT.jar

