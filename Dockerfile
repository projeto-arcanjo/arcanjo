FROM openjdk:8-jdk-alpine
MAINTAINER magno.mabreu@gmail.com
RUN mkdir /fotos
RUN mkdir /foms
COPY ./foms /foms/
COPY ./nophoto.png /fotos/
COPY ./target/arcanjo-1.0.war /opt/lib/
ENV LANG=pt_BR.utf8 
ENTRYPOINT ["java"]
CMD ["-jar", "/opt/lib/arcanjo-1.0.war"]
