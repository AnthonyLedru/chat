package client.processors;

import client.Connection;
import client.EventType;

public class AliasProcessor implements CommandProcessor {

    @Override
    public void processCommand(String args, Connection connection) {
        connection.emitEvent(EventType.ALIAS_UPDATED, args);
    }

}