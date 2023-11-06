import java.io.*;
import java.util.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		Address address= new Address();
		Socket socket=null;
		Scanner in=null;
		Scanner sc=new Scanner(System.in);
		PrintWriter out=null;
		Translation tl=new Translation();
		String math, message;
		
		try {
			socket=new Socket(address.get_ip(),address.get_port());
			System.out.println("Server connected");
			
			in=new Scanner(socket.getInputStream());
			out=new PrintWriter(socket.getOutputStream(), true);
			
			System.out.println("Type the mathematical expressions");
			math=sc.nextLine();	
			
			message = tl.MathToMessage(math, 100);
			int[] ASCII = tl.MessagetoASCII(message);

			for(int i=0;i<ASCII.length;i++) {
				out.println(ASCII[i]);
			}
			out.println('\0');
			
			int temp;
			String input= "", str;
			while(in.hasNextInt()) {
				temp=in.nextInt();
				str = tl.ASCIItoString(temp);
				input = input.concat(str);
			}
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
