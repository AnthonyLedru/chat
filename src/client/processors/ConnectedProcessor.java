package client.processors;

import client.Connection;
import client.EventType;

public class ConnectedProcessor implements CommandProcessor {

	@Override
	public void processCommand(String args, Connection connection) {
		connection.emitEvent(EventType.USER_CONNECTED, args);
	}

}
