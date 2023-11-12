import java.io.*;
import java.util.*;
import java.net.*;

//Client Class
public class Client {
	public static void main(String[] args) {
		//Address class create for get address
		Address address= new Address();
		Socket socket=null;
		Scanner in=null;
		Scanner sc=new Scanner(System.in);
		PrintWriter out=null;
		//Translation class create for protocol translation
		Translation tl=new Translation();
		//math= to store mathematical expressions, message= to store protocol
		String math, message;
		
		try {
			//Create client
			socket=new Socket(address.get_ip(),address.get_port());
			System.out.println("Server connected");
			
			//Create a class for server-client communication
			in=new Scanner(socket.getInputStream());
			out=new PrintWriter(socket.getOutputStream(), true);
			
			//Get Mathematical expression form user
			System.out.println("Type the mathematical expressions");
			math=sc.nextLine();	
			
			//Math to Message (math= expression, 100= state)
			message = tl.MathToMessage(math, 100);
			//Message to ASCII for propagation
			int[] ASCII = tl.MessagetoASCII(message);

			//Receive message to server
			for(int i=0;i<ASCII.length;i++) {
				out.println(ASCII[i]);
			}
			out.println('\0');
			
			//temp=The client's ASCII temporary storage
			int temp;
			//str=Message set converted to char, input= total message
			String input= "", str;
			
			
			while(in.hasNextInt()) {
				//Get ASCII
				temp=in.nextInt();
				//Translation ASCII to Char
				str = tl.ASCIItoString(temp);
				//Concatenate translated char in message
				input = input.concat(str);
			}
			//Translate Message
			String output=tl.MessagetoMessage(input);
			System.out.println(output);
			

			in.close();
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		sc.close();
	}

}
