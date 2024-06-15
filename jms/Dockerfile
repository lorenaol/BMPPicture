# Use the critoma/linux-u20-dev-security-ism image as the base
FROM critoma/linux-u20-dev-security-ism

# Install ActiveMQ on top of the base image
RUN apt-get update && \
    apt-get install -y activemq

# Expose ports for AMQP, MQTT, STOMP, OpenWire, and HTTP
EXPOSE 5672 1883 61613 61616 8161

# Optionally, you can copy a custom configuration file if needed
COPY ./activemq.xml /var/lib/activemq/conf/activemq.xml
COPY ./jetty.xml /var/lib/activemq/conf/jetty.xml
COPY ./credentials.properties /var/lib/activemq/conf/credentials.properties





# Start ActiveMQ
CMD ["activemq", "console"]
