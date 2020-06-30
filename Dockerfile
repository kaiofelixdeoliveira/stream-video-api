FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
ARG DEPENDENCY_CLASS-target/dependency
COPY ${DEPENDENCY_CLASS}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY_CLASS}/META-INF /app/META-INF
COPY ${DEPENDENCY_CLASS}/BOOT-INF/classes /app

CMS ["java","Dspring.profiles.active=${SPRING_PROFILE}","-cp", "app:app/lib/*", "com.kingoftech.stream_video.api.ApiApplication"]