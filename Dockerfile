# Usa uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o JAR do seu projeto para o contêiner
COPY target/orders-application.jar app.jar

# Exponha a porta usada pela aplicação (alterar se for diferente)
EXPOSE 8080

# Define o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
