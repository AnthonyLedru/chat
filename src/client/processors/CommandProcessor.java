/**
 * 
 */
package client.processors;

import client.Connection;

/**
 * Interface pour les classes chargées de traiter les commandes reçues du serveur.
 *
 */
public interface CommandProcessor {

    /**
     * Effectue le traitement associé à une commande spécifique.
     * @param args arguments de la commande
     * @param connection connexion ayant reçu la commande
     */
    void processCommand(String args, Connection connection);
}
