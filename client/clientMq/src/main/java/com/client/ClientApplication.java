package com.client;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ClientApplication.class, args);

		String brokerUrl = System.getenv("ACTIVEMQ_URL");
		if (brokerUrl == null) {
			brokerUrl = "tcp://localhost:61616";
		}

		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

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

					var encrypted = message.getBooleanProperty("Encrypted");
					var key = message.getStringProperty("Key");
					var image = message.getStringProperty("Image");

//					BytesMessage bytesMessage = (BytesMessage) message;
//					byte[] imageBytes = new byte[(int) bytesMessage.getBodyLength()];
//					bytesMessage.readBytes(imageBytes);


					processImage(key, image);

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

	private static void processImage(String key, String imageString) {
		// Command to run the encrypt executable in the encrypt container
		String command = "./encrypt " + key + " " + imageString;
		System.out.println(command);

		try {
			ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", command);
			Process process = null;
			try {
				 process = processBuilder.start();
			}catch (Error e) {
				System.out.println(e.getMessage());
			}


			// Reading the output from the command
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			int exitCode = process.waitFor();
			if (exitCode != 0) {
				System.err.println("Error: Encrypt process exited with code " + exitCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}