/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Serveur de "chat"
 *
 */
public class Server {
    // Le socket en attente de demande de connexion entrante
    private ServerSocket serverSocket;

    /**
     * Constructeur du serveur
     * 
     * @param port
     *            port d'écoute
     * @throws IOException
     *             si le socket d'écoute ne peut pas être ouvert
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /**
     * Boucle d'écoute du serveur.
     * 
     * @throws IOException
     *             si une erreur survient pendant l'attente d'une connexion
     */
    public void listen() throws IOException {
        
        // Création du pool de threads à utiliser pour gérer les connexions
        ExecutorService pool = Executors.newCachedThreadPool();
        
        // Création de la liste des connexions
        ActiveConnections activeConnections = new ActiveConnections();
        
        // jusqu'à l'arrêt du programme...
        while (true) {
            // attente d'une demande de connexion d'un client
            Socket clientSocket = serverSocket.accept();
            
            // création et démarrage de la connexion associée
            pool.submit(new ServerConnection(clientSocket, activeConnections));
        }
    }

    /**
     * Programme principal
     * 
     * @param args
     *            arguments de ligne de commande
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(3333);
            server.listen();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
