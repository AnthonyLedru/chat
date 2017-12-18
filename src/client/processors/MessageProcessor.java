/**
 * 
 */
package client.processors;

import java.util.HashMap;

import client.Connection;
import client.EventType;

/**
 * Classe utilisée par une connexion pour traiter les messages reçus du serveur
 */
public class MessageProcessor {
    /**
     * Processeurs de commandes.
     */
    private HashMap<String, CommandProcessor> commandProcessors;

    /**
     * Contructeur.
     * 
     */
    public MessageProcessor() {
        commandProcessors = new HashMap<>();
        commandProcessors.put("#alias", new AliasProcessor());
        commandProcessors.put("#list", new ListProcessor());
        commandProcessors.put("#connected", new ConnectedProcessor());
        commandProcessors.put("#private", new PrivateProcessor());
        commandProcessors.put("#renamed", new RenamedProcessor());
        commandProcessors.put("#disconnected", new DisconnectedProcessor());
    }

    /**
     * Traite un message en provenance du serveur. En fonction de la nature de celui-ci (commande,
     * message "normal), différents événements pourront être émis.
     * 
     * @param message message à traiter
     * @param connection connexion ayant reçu le message
     */
    public void processMessage(String message, Connection connection) {
        // suppression des caractères blancs (espaces,...) en début et fin de
        // message
    	if(message.charAt(0)=='#'){
    		String[] parts = message.split(" ",2);
    		String cmd = parts[0];
    		
    		if(commandProcessors.get(cmd) != null){
    			commandProcessors.get(cmd).processCommand(parts[1],connection);
    		}
    		else{
    			connection.emitEvent(EventType.ERROR, cmd);
    		}
    	}
    	else {
            message = message.trim();
            connection.emitEvent(EventType.MESSAGE, message);
    	}
       
    }

}
