/**
 * 
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;

import client.processors.MessageProcessor;

/**
 * Classe fournissant les fonctionnalités de base d'une connexion au serveur.
 * Etant observable, cette classe communique avec le client à l'aide
 * d'événements.
 *
 */
public class Connection extends Observable {
    // flot de sortie associé à la connexion. Ce qui est écrit sur ce flot sera
    // envoyé au serveur.
    private PrintStream output;

    /**
     * Constructeur
     * 
     * @param server
     *            nom d'hôte ou adresse IP du serveur
     * @param port
     *            numéro de port sur lequel le serveur écoute
     * @param alias
     *            pseudonyme de l'utilisateur
     * @param observer
     *            observateur à associer à cet Observable
     * @throws UnknownHostException si l'adresse IP du serveur ne peut êtrte déterminée
     * @throws IOException si un problème survient durant l'ouverture de la connexion
     */
    public Connection(String server, int port, String alias, Observer observer)
            throws UnknownHostException, IOException {
        // Ouverture de la connexion
        Socket socket = new Socket(server, port);

        // Création du flot d'entrée associé à la connexion.
        // Ce qui est lu sur ce flot provient du serveur.
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Création du flot de sortie
        output = new PrintStream(socket.getOutputStream());

        // Association de l'observateur
        addObserver(observer);
        
        // Création du processeur de messages
        MessageProcessor messageProcessor = new MessageProcessor();

        // les échanges avec le serveur doivent avoir lieu en même temps que les
        // entrées de l'utilisateur.
        // La boucle de communication avec le serveur sera donc exécutée dans
        // son propre thread.
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                // envoi du pseudonyme au serveur
                sendToServer(alias);
                
                boolean active = true;

                // tant que la connexion est active
                while (active) {

                    // attente et lecture d'une ligne en provenance du serveur
                    String message = input.readLine();
                    // si le message est valide ...
                    if (message != null) {
                        // ... on le traite ...
                        messageProcessor.processMessage(message, this);
                    }
                    else
                        // sinon, cela signifie que le serveur a mis fin à la
                        // connexion
                        active = false;
                }
            }
            catch (IOException e) {
            }
            // avant de terminer, un peu de ménage...
            finally {
                try {
                    // fermeture de la connexion
                    socket.close();

                    // affichage d'un message pour l'utilisateur
                    emitEvent(EventType.CONNECTION_ENDED);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Envoie un message au serveur
     * 
     * @param message
     *            message à envoyer
     */
    public void sendToServer(String message) {
        output.println(message);
    }

    /**
     * Emet un événement à destination de tous les observateurs enregistrés
     * auprès de cette connexion.
     * 
     * @param type
     *            type de l'événement
     * @param data
     *            données de l'événement
     */
    public void emitEvent(EventType type, String... data) {
        setChanged();
        notifyObservers(new Event(type, data));
    }
}
