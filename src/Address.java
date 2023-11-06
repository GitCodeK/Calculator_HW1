import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Address {
	private String ip="127.0.0.1";
	private int port=1234;
	
	Address(){
		String result="-1";
		File file=null;
		BufferedReader input=null;
		
		try {
			file= new File("./server_info.dat");
			input= new BufferedReader(new FileReader(file));
			
			result=input.readLine();
			
			if(!result.equals("-1")) {
				ip=result.substring(0,result.indexOf(":"));
				port=Integer.parseInt(result.substring(result.indexOf(":")+1));
			}
			
			input.close();	
		}
		catch(Exception e) {
			e.printStackTrace();}
	}

	public String get_ip() {
		return ip;
	}
	public int get_port() {
		return port;
	}
}
