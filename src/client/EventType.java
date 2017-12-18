/**
 * 
 */
package client;

/**
 * Définition des différents types d'événements pouvant être émis par une connexion
 */
public enum EventType {
    /**
     * l'événement correspond à un changement du pseudonyme de ce client
     * 
     * <br><p>Données de l'événement : nouveau pseudonyme
     *  
     */
    ALIAS_UPDATED,
    /**
     * l'événement est émis lorsque la connexion avec le serveur est terminée
     * 
     * <br><p>Données de l'événement : aucune
     * 
     */
    CONNECTION_ENDED,
    /**
     * l'événement est émis lorsque le serveur envoie une commande d'erreur
     * 
     * <br><p>Données de l'événement : identifiant de l'erreur
     * 
     */
    ERROR,
    /**
     * l'événement est émis lorsque le serveur envoie la liste des utilisateurs
     * 
     * <br><p>Données de l'événement : pseudonymes des utilisateurs connectés
     * 
     */
    LIST, 
    /**
     * l'événement est émis lorsque le serveur envoie un message "normal"
     * 
     * <br><p>Données de l'événement : message
     * 
     */
    MESSAGE, 
    /**
     * l'événement est émis lorsque le serveur signale la connexion d'un nouvel utilisateur
     * 
     * <br><p>Données de l'événement : pseudonyme du nouvel utilisateur
     * 
     */
    USER_CONNECTED, 
    /**
     * l'événement est émis lorsque le serveur signale la déconnexion d'un utilisateur
     * 
     * <br><p>Données de l'événement : pseudonyme de l'utilisateur déconnecté
     * 
     */
    USER_DISCONNECTED, 
    /**
     * l'événement est émis lorsque le serveur signale le changement de pseudonyme d'un utilisateur
     * 
     * <br><p>Données de l'événement : ancien pseudonyme, nouveau pseudonyme
     * 
     */
    USER_RENAMED,
    
    
    PRIVATE;
}