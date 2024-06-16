# Use the specified base image
FROM critoma/linux-u20-dev-security-ism

# Set the working directory inside the container
WORKDIR /app

# Install Java
RUN apt-get update && apt-get install -y openjdk-11-jdk

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

# Add Java to PATH
ENV PATH $JAVA_HOME/bin:$PATH

# Copy the gradlew and gradle related files
COPY /broker /app

RUN chmod +x gradlew
# Run the build process
RUN ./gradlew build -x test

# Adjust the CMD to run your application
ENTRYPOINT ["java","-jar","./build/libs/broker-0.0.1.jar"]
