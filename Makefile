#makefile

build:
	mvn package
	docker build --build-arg SPRING_PROFILES_ACTIVE=dev -f Dockerfile -t stream-video-api:latest .

run:	build
	sudo docker run -p 8081:8081 -t stream-video-api:latest