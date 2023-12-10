package remote;

import java.net.Socket;
import java.io.IOException;
/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {
	
	private Socket socket;
	
	public void connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Conectado ao servidor em " + host + " : " + port);
            // Aqui você pode começar a enviar/receber dados do servidor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public static void main(String[] args) {
    	
        Client client = new Client();
       
        
        client.connectToServer("localhost", 12345); // Exemplo de host e porta
        
    }

    
}
