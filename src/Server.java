import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Server class
public class Server {

	public static void main(String[] args) {
		//Address class create for get address
		Address address= new Address();
		
		//Make listener for Multi-thread
		ServerSocket listener=null;
		//Make pool for make Multi-thread
		ExecutorService pool = Executors.newFixedThreadPool(20);
		
		try {
			//Connect Socket
			listener=new ServerSocket(address.get_port());
			System.out.println("Server Running (Port = "+address.get_port()+")");
			
			//When you have a client, listener pass client to thread
			while(true) {
				Socket sock=listener.accept();
				pool.execute(new thread(sock));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

//Thread Class
class thread implements Runnable{
	private Socket socket;
	
	thread(Socket socket){
		this.socket=socket;
	}
	
	//Run thread
	public void run() {
		Scanner in=null;
		PrintWriter out=null;
		//Translation class create for translation protocol
		Translation tl=new Translation();
		
		System.out.println("\nClient Connection\t"+socket);
		
		try {
			//Create a class for server-client communication
			in=new Scanner(socket.getInputStream());
			out=new PrintWriter(socket.getOutputStream(), true);
			
			//temp=The client's ASCII temporary storage
			int temp;
			//message=Message set converted to char
			String message= "", str;
			
			//Get ASCII until the next int is found
			while(in.hasNextInt()) {
				//Get ASCII
				temp=in.nextInt();
				//Translation ASCII to Char
				str = tl.ASCIItoString(temp);
				//Concatenate translated char in message
				message = message.concat(str);
			}
			//Print Received Protocol
			System.out.println("Recieve Protocol\t"+message);
			
			//Translate Message & Make Respond Message
			String output=tl.MessagetoMessage(message);
			//Translate Respond Message to ASCII
			int[] ASCII = tl.MessagetoASCII(output);
			//Propagation ASCII Protocol to Client
			for(int i=0;i<ASCII.length;i++) {
				out.println(ASCII[i]);
			}
			out.println('\0');
			
			//Print Respond Protocol
			System.out.println("Send Protocol\t\t"+output);
			
			in.close();
			out.close();
			
			System.out.println("Client Disconnection\t"+socket);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}