/**
 * 
 */
package client;

/**
 * Classe utilisée pour communiquer avec les observateurs d'une connexion
 *
 */
public class Event {
    /**
     * Type de l'événement
     */
    public final EventType type;
    
    /**
     * Données de l'événement. En fonction du type d'événement, ce tableau
     * contiendra plus ou moins d'éléments (voir la documentation de
     * l'énumération {@link EventType}).
     * 
     */
    public final String[] data;

    /**
     * Constructeur.
     * 
     * @param type
     *            type de l'événement
     * @param data
     *            données de l'événement
     */
    public Event(EventType type, String... data) {
        this.type = type;
        this.data = data;
    }
}