#! /bin/sh

mkdir -p /srv/arcanjo/photos
cp nophoto.png /srv/arcanjo/photos/
cp nophoto.png /srv/arcanjo/photos/601aeab282f94aaeb4f706042d6427fe.png

mvn clean package

docker ps -a | awk '{ print $1,$2 }' | grep projetoarcanjo/arcanjo:1.0 | awk '{print $1 }' | xargs -I {} docker rm -f {}
docker rmi projetoarcanjo/arcanjo:1.0
docker build --tag=projetoarcanjo/arcanjo:1.0 --rm=true .

docker run --name arcanjo --hostname=arcanjo --network arcanjo \
-v /etc/localtime:/etc/localtime:ro \
-e FEDERATION_NAME=ArcanjoFederation \
-e FEDERATE_NAME=Arcanjo \
-e ARCANJO_DB_USER=postgres \
-e ARCANJO_DB_PASSWORD=@rcanjo \
-e ARCANJO_DB_HOST=arcanjo-db \
-e ARCANJO_DB_PORT=5432 \
-e ARCANJO_DB=arcanjo \
-p 8080:8080 \
-d projetoarcanjo/arcanjo:1.0


