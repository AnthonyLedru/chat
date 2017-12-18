package client.processors;

import client.Connection;
import client.EventType;

public class RenamedProcessor implements CommandProcessor {

	@Override
	public void processCommand(String args, Connection connection) {
		String[] parts = args.split(" ");
		connection.emitEvent(EventType.USER_RENAMED, parts[0], parts[1]);

	}

}
