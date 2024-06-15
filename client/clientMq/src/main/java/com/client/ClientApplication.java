package com.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.*;


@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) throws JMSException {
		SpringApplication.run(ClientApplication.class, args);
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://activemq:61616");

		// Create a Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the Topic
		Topic topic = session.createTopic("YourTopicName"); // Replace with your topic name

		// Create a MessageConsumer from the Session to the Topic
		MessageConsumer consumer = session.createConsumer(topic);

		// Create a MessageListener
		MessageListener listener = new MessageListener() {
			public void onMessage(Message message) {
				try {
					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						System.out.println("Received message: " + textMessage.getText());
					} else {
						System.out.println("Received message: " + message);
					}
				} catch (JMSException e) {
					System.out.println("Caught: " + e);
					e.printStackTrace();
				}
			}
		};

		// Register the MessageListener
		consumer.setMessageListener(listener);

		// Keep the application running to receive messages
		System.out.println("Subscriber is running...");
	}


}
