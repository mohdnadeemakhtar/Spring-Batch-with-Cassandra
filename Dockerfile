FROM zalando/openjdk:8u40-b09-1
EXPOSE 8080
RUN ["mkdir","/opt/docker"]

WORKDIR /opt/docker
COPY target/dataMigration-0.0.1-SNAPSHOT.jar /opt/docker/
COPY run.sh /opt/docker/

RUN chmod a+x /opt/docker/run.sh

ENTRYPOINT ["/opt/docker/run.sh"]