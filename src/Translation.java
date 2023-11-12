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
		float[] num=new float[Math.length()];
		int i_n=1,i_o=0;
		float result;
		String[] operation=new String[Math.length()];
		Math=Math.concat(" ");
		
		try {
			num[0]=Float.parseFloat(Math.substring(0, Math.indexOf(" ")));
			Math=Math.substring(Math.indexOf(" ")+1);
			while(Math.indexOf(" ")!=-1){
				operation[i_o]=Math.substring(0,Math.indexOf(" "));
				Math=Math.substring(Math.indexOf(" ")+1);
				i_o++;
				
				num[i_n]=Float.parseFloat(Math.substring(0, Math.indexOf(" ")));
				Math=Math.substring(Math.indexOf(" ")+1);
				i_n++;
			}

			if(i_o+1!=i_n) {
				error=-1;
				return 401;
			}
			else {
				for(int i=0;i<i_o;i++) {
					if(operation[i].equals("+")&&operation[i].equals("-")&&operation[i].equals("*")&&operation[i].equals("/")) {
						error=-1;
						return 401;
					}
				}
			}
			
			for(int i=0;i<i_o;i++) {
				if(operation[i].equals("*")) {
					operation[i]="+";
					num[i]=num[i]*num[i+1];
					num[i+1]=0;
				}
				else if(operation[i].equals("/")) {
					if(num[i+1]==0.0) {
						error=-1;
						return 400;
					}
					operation[i]="+";
					num[i]=num[i]/num[i+1];
					num[i+1]=0;
				}
			}
			
			result=num[0];
			for(int i=0;i<i_o;i++) {
				if(operation[i].equals("+")) {
					result+=num[i+1];
				}
				else if(operation[i].equals("-")) {
					result-=num[i-1];
				}
				else {
					error=-1;
					return 401;
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
