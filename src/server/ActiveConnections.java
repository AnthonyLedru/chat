/**
 * 
 */
package server;
 
import java.util.ArrayList;

/**
 * Liste des connexions actives.
 *
 */
public class ActiveConnections {
    // Liste des connexions actives
    private ArrayList<ServerConnection> connectionsList = new ArrayList<>();

    /**
     * Diffusion d'un message à tous les clients connectés
     * 
     * @param message
     *            message à diffuser
     */
    public synchronized void broadcast(String message) {
        for(int i =0;i<connectionsList.size();i++){
        	this.connectionsList.get(i).sendToClient(message);
        }
    }

    /**
     * Ajout d'une nouvelle connexion.
     * 
     * @param connection
     *            connexion à ajouter
     */
    synchronized void add(ServerConnection connection) {
        connectionsList.add(connection);
    }

    /**
     * Suppression d'une connexion.
     * 
     * @param connection
     *            connexion à supprimer
     */
    synchronized void remove(ServerConnection connection) {
        connectionsList.remove(connection);
    }

    /**
     * Retourne une connexion à partir du pseudonyme du client associé. La
     * recherche ne sera pas sensible à la casse des pseudonymes.
     * 
     * @param alias
     *            pseudonyme du client
     * @return connexion correspondant au pseudonyme, ou {@code null} si aucune
     *         connexion n'existe pour ce pseudonyme
     */
    public synchronized ServerConnection get(String alias) {
        // à écrire
        return null;
    }

    /**
     * Retourne la liste des pseudonymes des clients connectés
     * 
     * @return pseudonymes séparés par des espaces
     */
    public synchronized String toString() {
    	String str = "";
    	for(int i =0; i<this.connectionsList.size();i++){
    		str += this.connectionsList.get(i).getAlias() + " ";
    	}
        return str;
    }

}
