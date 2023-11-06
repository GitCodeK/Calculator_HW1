public class Translation {
	public int[] MessagetoASCII(String temp) {
		String[] str=temp.split("");
		int[] ASCII=new int[str.length];
		
		for(int i=0;i<str.length;i++) {
			int n=str[i].charAt(0);
			ASCII[i]=n;
		}
		
		return ASCII;
	}
	
	public String ASCIItoString(int temp) {
		return String.valueOf((char) temp);
	}

	public String MathToMessage(String math, int state) {
		int length= 10 + math.length(),temp;	//checksum=3+3+3+message+(length+1);
		temp=length;
		
		while(temp/10 != 0) {
			temp/=10;
			length+=1;
		}
		
		String message;

		message=length+":"+state+":"+checksum(math)+":"+math;
		
		return message;
	}
	
	private int checksum(String messgae){
		int result=0, temp;
		int[] ASCII = MessagetoASCII(messgae);
		
		for(int i=0;i<ASCII.length;i++) {
			result+=ASCII[i];
		}
		while(result/1000!=0) {
			temp=result/1000;
			result-=temp*1000;
			result+=temp;
		}
		
		return result;
	}
	
	public String MessagetoMessage(String str) {
		int state, checksum, temp;
		String Math;
		
		temp=str.indexOf(":");
		str=str.substring(temp+1);
		
		temp=str.indexOf(":");
		state=Integer.valueOf(str.substring(0, temp));
		str=str.substring(temp+1);
		
		temp=str.indexOf(":");
		checksum=Integer.valueOf(str.substring(0,temp));
		Math=str.substring(temp+1);
		
		String result= translation(Math, checksum, state);
		
		return result;
	}
	
	private int error=0;
	
	public String translation(String Math, int checksum, int state) {
		float result=0;
		String Message="";
		
		if(state==100) {
			if(checksum(Math)!=checksum) {
				result = 502;
				error=-1;
			}
			else {
				result = calculation(Math);
			}
			
			if(error==-1) {
				Message=MathToMessage(Math, (int)result);
			}
			else {
				Message=MathToMessage(""+result, 200);
			}
		}
		else if(checksum(Math)!=checksum) {
			Message="Incorrect : Checksum Error!";
		}
		else if(state==200) {
			Message="Answer : " + Math;
		}
		else if(state==400) {
			Message="Incorrect : Divided by Zero!";
		}
		else if(state==401) {
			Message="Incorrect : Bad Request(Undefine Form)";
		}

		return Message;
	}
		

	private float calculation(String Math) {
		float result = -1, value=0;
		int n;
		Math=Math.concat(" ");
		
		String temp, operation;
		try {
			n=Math.indexOf(" ");
			temp=Math.substring(0, n);
			Math=Math.substring(n+1);
			result=Float.parseFloat(temp);
			
			while(!Math.equals("")) {
				n=Math.indexOf(" ");
				operation=Math.substring(0, n);
				Math=Math.substring(n+1);
				
				n=Math.indexOf(" ");
				temp=Math.substring(0, n);
				Math=Math.substring(n+1);
				value=Float.parseFloat(temp);
				
				if(operation.equals("+")) {
					result+=value;
				}
				else if(operation.equals("-")) {
					result-=value;
				}
				else if(operation.equals("*")) {
					result*=value;
				}
				else if(operation.equals("/")) {
					if(value==0.0) {
						error=-1;
						result=400;
						break;
					}
					result/=value;
				}
				else {
					error=-1;
					result=401;
					break;
				}
			}
		}
		catch(NumberFormatException e) {
			error=-1;
			result=401;
		}
		return result;
	}
}
