/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;
import server.processors.MessageProcessor;

/**
 * Classe fournissant les fonctionnalités de communication avec un client. Une
 * instance de cette classe est créée chaque fois qu'un client se connecte.
 *
 */
public class ServerConnection implements Runnable {

    // Objet chargé de traiter les messages provenant des clients
    private static MessageProcessor messageProcessor = new MessageProcessor();

    // socket de communication avec le client qui a ouvert cette connexion
    private Socket socket;

    // flot de sortie associé au socket
    private PrintStream output;

    // flot d'entrée associé au socket
    private BufferedReader input;

    // instance du serveur sur lequel cette connexion a été ouverte
    private ActiveConnections activeConnections;

    // état de la connexion
    private boolean active = false;

    // copie locale du pseudonyme du client
    private String alias = null;

    /**
     * Constructeur
     * 
     * @param socket
     *            socket de communication avec le client
     * @param activeConnections
     *            instance du serveur sur lequel cette connexion a été ouverte
     * @throws IOException
     *             en cas d'erreur lors de la création des flots d'entrée/sortie
     */
    public ServerConnection(Socket socket, ActiveConnections activeConnections) throws IOException {
        this.socket = socket;
        this.activeConnections = activeConnections;
        this.output = new PrintStream(this.socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.active = false;
        this.alias = null; 
    }

    /*
     * Echange initial (lecture et validation du pseudonyme) et boucle de
     * communication avec le client.
     */
    @Override
    public void run() {
        try {
            String str = this.input.readLine();
        	this.active = updateAlias(str);
        	if(this.active){
                ServerConnection.messageProcessor.processMessage("#connected "+str, this);
                this.activeConnections.add(this);
                ServerConnection.messageProcessor.processMessage("#list "+ this.activeConnections.toString(), this);
        	}
            while (active) {
            	String message = input.readLine();
            	System.out.println(message);
            	
            	if (!message.equals("/quit")) {
            		
            		if(message.charAt(0)=='/'){
            			if(message.equals("/list")){
            				ServerConnection.messageProcessor.processMessage("#list "+ this.activeConnections.toString(), this);
            			}
            			else{
            				messageProcessor.processMessage(message, this);
            			}
                	}
            		
            		else {  
            			messageProcessor.processMessage(alias + " > " + message, this);
            		}
                }
                else
                    active = false;
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
            try {
            	ServerConnection.messageProcessor.processMessage("#disconnected "+ this.alias, this);
            	this.activeConnections.remove(this);
                this.socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Envoi d'un message au client qui a ouvert cette connexion
     * 
     * @param message
     *            message à envoyer
     */
    public void sendToClient(String message) {
        output.println(message);
    }

    /**
     * Retourne le pseudonyme du client.
     * 
     * @return pseudonyme du client
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Met à jour le pseudonyme du client. Si la mise à jour est effective, une
     * commande #alias est envoyée au client. Sinon, une commande #error est
     * émise.
     * 
     * @param newAlias
     *            nouveau pseudonyme
     * @return true si la mise à jour a pu être effectuée, false sinon (newAlias
     *         vaut null ou est déjà utilisé ou contient un caractère invalide)
     */
    public boolean updateAlias(String newAlias) {
    	if(newAlias == null)
    		return false;
    	
    	String[] tab = this.activeConnections.toString().split(" ");   	
    	for(int i = 0;i<tab.length ; i++){
    		if(tab[i].equals(newAlias))
    			return false;
    	}
    	
    	/* A FAIRE !!!!
    	if(newAlias. )
    		return false;
    	*/
    	
        this.alias = newAlias;
        sendToClient("#alias " + newAlias);

        return true;
    }

    /**
     * Retourne la liste des connexions actives.
     * 
     * @return la liste des connexions actives.
     */
    public ActiveConnections getActiveConnections() {
        return activeConnections;
    }

    /**
     * désactive cette connexion.
     */
    public void stop() {
        this.active = false;
    }
}
