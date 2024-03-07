# Use Java 17 as the base image, as builder, so that we can discard not-needed files such as pom.xml and src files after build
FROM amazoncorretto:17-alpine-jdk as builder
EXPOSE 8080

# Create a new application directory for my application files
RUN mkdir /app
WORKDIR /app

# Check depency changes, Download all dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw dependency:go-offline

# Then check src code changes
RUN ./mvnw package -DskipTests

# Copy the app files from host machine to image filesystem
FROM amazoncorretto:17-alpine
COPY --from=builder /app/target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]



