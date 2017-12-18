package client.processors;

import client.Connection;
import client.EventType;

public class PrivateProcessor implements CommandProcessor {

	@Override
	public void processCommand(String args, Connection connection) {
		String[] parts = args.split(" ");
		connection.emitEvent(EventType.PRIVATE, parts[0], parts[1]);
	}

}
