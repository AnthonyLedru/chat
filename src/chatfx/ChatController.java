package chatfx;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import client.Connection;
import client.Event;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class ChatController implements Observer{
	
	Connection connection = null;
	String txt ="";
	
	@FXML
	private TextField inputField;
	
	@FXML
	private TextArea messageArea;
	
	@FXML
    private ListView<String> userList;

	private List<String> L = new ArrayList<String>();
	
	@Override
	public void update(Observable o, Object arg) {
		
		Platform.runLater(new Runnable() {
			public void run() {
		
				Event event = (Event) arg;
		        switch(event.type) {
		            case CONNECTION_ENDED:
		            	L.clear();
		            	ChatController.this.userList.getItems().setAll(L);
		                txt += ("*** Vous êtes maintenant déconnecté ***" + "\n");
		                ChatController.this.connection =null;
		        	break;
		            	
		            case MESSAGE:
		            	if(event.data[0].charAt(0)!='/')
		            		txt += (event.data[0] + "\n");
		            break;
		            
		            case ALIAS_UPDATED:
		            	txt += ("*** Vous êtes maintenant connecté avec le pseudonyme " + event.data[0] + " ***" + "\n");
		            	ChatController.this.connection.sendToServer("/list");
		            	 Main.getStage().setTitle(event.data[0]);
		            break;
		                
		            case ERROR:
		            	txt += ("ERREUR - " + event.data[0] + "\n");
		            break;
		                
		            case LIST:
		            	String[] parts = event.data[0].split(" ");
		            	L.clear();
		            	for(int i =0; i < parts.length ; i++ )
		            		L.add(parts[i]);
		            	ChatController.this.userList.getItems().setAll(L);
		        	break;
		            	
		            case USER_CONNECTED :
		            	txt += ("** Connection de " +event.data[0] + " **\n");
		            	ChatController.this.connection.sendToServer("/list");
		        	break;
		            	
		            case USER_DISCONNECTED:
		            	txt += ("** Deconnection de " +event.data[0] + " **\n");
		            	ChatController.this.connection.sendToServer("/list");
		        	break;
		            	
		            case USER_RENAMED:
		            	txt += ("** "+event.data[0] + " a changer d'alias pour " +event.data[1] + " **\n");
		            	ChatController.this.connection.sendToServer("/list");
		        	break;
		            case PRIVATE:
		            	txt += ("*** Message privé de " + event.data[0] + " : \n" + event.data[1] + "\n" + "*** Fin du message"+"\n");
		        	break;
		            	
		            default:
		        	break;  
		        }
		        ChatController.this.messageArea.setText(txt);
		        ChatController.this.messageArea.setScrollTop(90000);
        
			}
		});
	}
	
	public void onSend(){
		if(this.inputField.getCharacters().toString() == ""){}
		else if(connection == null){
			try {
				this.connection = new Connection( "localhost" , 3333, inputField.getCharacters().toString() , this);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			this.connection.sendToServer(inputField.getCharacters().toString());
		}
		this.inputField.setText("");
	}
	
}
