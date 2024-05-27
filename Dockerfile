FROM eclipse-temurin:17.0.7_7-jre-alpine

WORKDIR /app

# Crear y declarar el usuario sin privilegios
RUN addgroup -g 1000 -S app && adduser --uid 1000 -S app -G app

# Copiar el archivo JAR y el script de ejecución
COPY ./target/*.jar /app/app.jar

# Cambio a usuario rootless
USER app:app

# Exponer los puertos necesarios
EXPOSE 8080

# run jar with the possibility of custom java flags and arguments
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "env $(grep -v '^#' /app/.env | xargs) java ${JAVA_OPTS} -jar /app/app.jar ${0} ${@}"]