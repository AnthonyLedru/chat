/**
 * 
 */
package client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Interface utilisateur d'un client en mode texte
 *
 */
public class TextClient implements Observer {

    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @SuppressWarnings("incomplete-switch")
    @Override
    public void update(Observable o, Object arg) {
        Event event = (Event) arg;
        
        switch(event.type) {
            case CONNECTION_ENDED:
                System.out.println("*** Vous êtes maintenant déconnecté ***");
                System.exit(0);
                break;
                
            case MESSAGE:
                System.out.println(event.data[0]);
                break;
            
            case ALIAS_UPDATED:
                System.out.println("*** Vous êtes maintenant connecté avec le pseudonyme " + event.data[0] + " ***");
                break;
                
            case ERROR:
                System.err.println("ERREUR - " + event.data[0]);
                break;
                
            case LIST:
            	System.out.println("User(s) : " +event.data[0]);
            	break;
            	
            case USER_CONNECTED :
            	System.out.println("Connection de " +event.data[0]);
            	break;
            	
            case USER_DISCONNECTED:
            	System.out.println("Deconnection de " +event.data[0] + "\n");
            	break;
            	
            case USER_RENAMED:
            	System.out.println(event.data[0] + " a changer d'alias pour " +event.data[1] + "\n");
            	break;
        	
            case PRIVATE:
            	System.out.println("*** Message privé de " + event.data[0] + " : \n" + event.data[1] + "\n" + "*** Fin du message"+"\n");
            	break;
        }
    }
    
    /**
     * Porgramme principal
     * @param args arguments de ligne de commande
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            
            System.out.println("Entrez votre identifiant :");
            String alias = input.nextLine();
                        
            Connection connection = new Connection("linux", 3333, alias, new TextClient());
            
            while (true) {
                // lecture des entrées e l'utilisateur
                String message = input.nextLine();
                // si le message comporte autre chose que des caractères blancs...
                if (!message.trim().equals(""))
                    // ...il est envoyé au serveur
                    connection.sendToServer(message);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
