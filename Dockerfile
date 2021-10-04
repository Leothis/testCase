# Start with a base image containing Java runtime
FROM maven:3.8.2-openjdk-11


# Add project directory
COPY . /app

# Working dir is now the project dir
WORKDIR /app

# Create jar file
RUN mvn clean package

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/target/testCase.jar"]
