# Etapa de construcción: Usa una imagen de Gradle para construir la aplicación
FROM gradle:7.3.3-jdk17 AS build

# Copia el código fuente en el contenedor
COPY --chown=gradle:gradle . /home/gradle/src

# Cambia al directorio de trabajo donde está el código fuente
WORKDIR /home/gradle/src

# Ejecuta el build de Gradle, esto generará el JAR ejecutable
RUN gradle build --no-daemon

# Etapa de ejecución: Usa una imagen base de Java para ejecutar la aplicación
FROM openjdk:17-jdk

# Crea un directorio en el contenedor para la aplicación
RUN mkdir /app

# Copia el JAR construido desde la etapa de construcción al directorio de la aplicación en el contenedor
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

# Define el directorio de trabajo
WORKDIR /app

# Expone el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "spring-boot-application.jar"]
