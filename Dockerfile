FROM azul/zulu-openjdk-alpine:11.0.6-jre

RUN apk add --no-cache tini
# Tini is now available at /sbin/tini
ENTRYPOINT ["/sbin/tini", "--"]

ARG JAR_FILE
ADD target/${JAR_FILE} /target.jar

CMD ["java", "-jar", "/target.jar"]
EXPOSE 8080
