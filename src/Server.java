import java.util.*;
import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) {
		Address address= new Address();
		
		ServerSocket listener=null;
		ExecutorService pool = Executors.newFixedThreadPool(20);
		
		try {
			listener=new ServerSocket(address.get_port());
			System.out.println("Server Running (Port = "+address.get_port()+")");
			
			while(true) {
				Socket sock=listener.accept();
				pool.execute(new Calculation(sock));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class Calculation implements Runnable{
	private Socket socket;
	
	Calculation(Socket socket){
		this.socket=socket;
	}
	
	public void run() {
		Scanner in=null;
		PrintWriter out=null;
		Translation tl=new Translation();
		
		System.out.println("\nClient Connection\t"+socket);
		
		try {
			in=new Scanner(socket.getInputStream());
			out=new PrintWriter(socket.getOutputStream(), true);
			
			int temp;
			String message= "", str;
			while(in.hasNextInt()) {
				temp=in.nextInt();
				str = tl.ASCIItoString(temp);
				message = message.concat(str);
			}
			System.out.println("Recieve Protocol\t"+message);
			
			String output=tl.MessagetoMessage(message);
			int[] ASCII = tl.MessagetoASCII(output);
			for(int i=0;i<ASCII.length;i++) {
				out.println(ASCII[i]);
			}
			out.println('\0');
			
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