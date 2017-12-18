package client.processors;

import client.Connection;
import client.EventType;

public class ListProcessor implements CommandProcessor {

	@Override
	public void processCommand(String args, Connection connection) {
		connection.emitEvent(EventType.LIST, args);
	}

}
