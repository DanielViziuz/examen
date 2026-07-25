FROM maven:3.9.5-sapmachine-17
RUN apt-get update && apt-get install -y curl
RUN apt-get update && apt-get install -y iputils-ping
RUN apt-get update && apt-get install -y telnetd
RUN apt-get update && apt-get install -y netcat
WORKDIR /app
COPY . .

RUN mvn clean install
CMD ["mvn","spring-boot:run"]