/**
 * 
 */
package server.processors;

import java.util.HashMap;

import server.ServerConnection;

/**
 * Classe utilisée par une connexion pour traiter les messages reçus du client
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
    }
    
    /**
     * Traite un message en provenance du client. En fonction de la nature de celui-ci (commande,
     * message "normal), différents traitements seront effectués
     * 
     * @param message message à traiter
     * @param connection connexion ayant reçu le message
     */
    public void processMessage(String message, ServerConnection connection) {
        message = message.trim();

        // code temporaire : diffusion systématique
        connection.getActiveConnections().broadcast(message);
    }
}
