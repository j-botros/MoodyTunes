FROM openjdk:17-jdk-slim

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy and build
COPY pom.xml .
COPY src src

# Build the app
RUN mvn clean package -DskipTests

# List what got built (for debugging)
RUN ls -la target/

# Run with explicit jar name based on your pom.xml artifactId
CMD java -jar target/moodytunes-*.jar