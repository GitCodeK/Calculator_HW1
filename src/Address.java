import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

//Class which get the address information
public class Address {
	//Default address information setting
	private String ip="127.0.0.1";
	private int port=1234;
	
	//When class created, Get the address information
	Address(){
		// result=Check if an input value exists & Store the input value
		String result="-1";
		
		//File Input
		File file=null;
		BufferedReader input=null;
		
		// Get the address information
		try {
			//Connected File
			file= new File("./server_info.dat");
			input= new BufferedReader(new FileReader(file));
			
			//Read 1 line from server_info.dat
			result=input.readLine();
			
			//If input value exists, Divided IP & port
			if(!result.equals("-1")&&result.indexOf(":")!= -1) {
				ip=result.substring(0,result.indexOf(":"));
				port=Integer.parseInt(result.substring(result.indexOf(":")+1));
			}
			
			input.close();	
		}
		catch(Exception e) {
			e.printStackTrace();}
	}

	//IP information return
	public String get_ip() {
		return ip;
	}
	
	//port information return
	public int get_port() {
		return port;
	}
}
