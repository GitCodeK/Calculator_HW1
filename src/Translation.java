//Translation Protocol class
public class Translation {
	//Store the protocol error (0=no error, -1=error)
	private int error=0;
	
	//String to ASCII
	//input temp = Original String
	///output = converted ASCII
	public int[] MessagetoASCII(String temp) {
		//Divided String to 1 character
		String[] str=temp.split("");
		//Create ASCII to store translated ASCII
		int[] ASCII=new int[str.length];
		
		//Translation String to ASCII
		for(int i=0;i<str.length;i++) {
			int n=str[i].charAt(0);
			ASCII[i]=n;
		}
		
		return ASCII;
	}
	
	//Char to ASCII
	//input temp = Original ASCII
	//output = converted String
	public String ASCIItoString(int temp) {
		return String.valueOf((char) temp);
	}

	//Mathematical expression to Protocol
	//input math = Mathematical expression, state = Protocol state
	public String MathToMessage(String math, int state) {
		//length = math.length(Mathematical expression) + number of ":"(+3) + state(+3) + checksum(+3) + minimum length(+1)
		int length= 10 + math.length();
		
		//For calculation length
		int temp=length;
		while(temp/10 != 0) {
			temp/=10;
			length+=1;
		}
		
		//message= protocol message
		String message;

		message=length+":"+state+":"+checksum(math)+":"+math;
		
		return message;
	}
	
	//Calculation checksum
	//input message= Protocol message
	//output checksum
	private int checksum(String messgae){
		//result = checksum, temp= Temporary storage of four or more digits
		int result=0, temp;
		//Convert Protocol to ASCII
		int[] ASCII = MessagetoASCII(messgae);
		
		//Calculation the sum of ASCII
		for(int i=0;i<ASCII.length;i++) {
			result+=ASCII[i];
		}
		//Four or more digits calculation
		while(result/1000!=0) {
			temp=result/1000;
			result-=temp*1000;
			result+=temp;
		}
		
		return result;
	}
	
	//Message Divided & Make message
	//Input str= received message
	//output = respond message
	public String MessagetoMessage(String str) {
		//state= protocol state, checksum=checksum temp=temporary storage of string point
		int state, checksum, temp;
		//Math = Mathematical expression
		String Math;
		
		//Divided by ":" & get length
		temp=str.indexOf(":");
		str=str.substring(temp+1);
		
		//Divided by ":" & storage protocol state
		temp=str.indexOf(":");
		state=Integer.valueOf(str.substring(0, temp));
		str=str.substring(temp+1);
		
		//Divided by ":" & Storage protocol checksum
		temp=str.indexOf(":");
		checksum=Integer.valueOf(str.substring(0,temp));
		//Storage Mathematical expression
		Math=str.substring(temp+1);
		
		//Make respond Message
		String result= translation(Math, checksum, state);
		
		return result;
	}
	
	//Message translation & make respond message
	//Input Math= Mathematical expression, checksum=checksum, state=protocol state
	//Output = Respond Message
	public String translation(String Math, int checksum, int state) {
		//result= Mathematical result
		float result=0;
		//Message= respond message
		String Message="";
		
		//If state=calculation
		if(state==100) {
			//check the checksum error
			if(checksum(Math)!=checksum) {
				//If exists error change error int & return checksum error state 502
				result = 502;
				error=-1;
			}
			else {
				//If no error calculation
				result = calculation(Math);
			}
			//If there is error
			if(error==-1) {
				Message=MathToMessage(Math, (int)result);
			}
			//No error
			else {
				Message=MathToMessage(""+result, 200);
			}
		}
		//If there is checksum error
		else if(checksum(Math)!=checksum) {
			Message="Incorrect : Checksum Error!";
		}
		//If there is respond result
		else if(state==200) {
			Message="Answer : " + Math;
		}
		//If there is Divided by zero exception
		else if(state==400) {
			Message="Incorrect : Divided by Zero!";
		}
		//If there is Bad Request exception
		else if(state==401) {
			Message="Incorrect : Bad Request(Undefine Form)";
		}

		return Message;
	}
		
	//Calculation Mathematical Expression
	// First divide number & operation
	// Second calculation "A * B" & "A /B " -> "Result + 0"
	// Third calculation "+" & "-"
	//Input Math = mathematical expression
	//output = mathematical result
	private float calculation(String Math) {
		//For storage number
		float[] num=new float[Math.length()];
		//i_n=number index, i_o=operation index
		int i_n=1,i_o=0;
		//result= Mathematical Result
		float result;
		//For storage operation
		String[] operation=new String[Math.length()];
		//Math end point setting
		Math=Math.concat(" ");
		
		try {
			//Divided first Number
			num[0]=Float.parseFloat(Math.substring(0, Math.indexOf(" ")));
			//Subtract the divided number and reset
			Math=Math.substring(Math.indexOf(" ")+1);
			
			//Divided number & opeation
			while(Math.indexOf(" ")!=-1){
				operation[i_o]=Math.substring(0,Math.indexOf(" "));
				Math=Math.substring(Math.indexOf(" ")+1);
				i_o++;
				
				num[i_n]=Float.parseFloat(Math.substring(0, Math.indexOf(" ")));
				Math=Math.substring(Math.indexOf(" ")+1);
				i_n++;
			}

			//Number & Operation not pair
			if(i_o+1!=i_n) {
				error=-1;
				return 401;
			}
			else {
				//check wrong operation 
				for(int i=0;i<i_o;i++) {
					if(operation[i].equals("+")&&operation[i].equals("-")&&operation[i].equals("*")&&operation[i].equals("/")) {
						error=-1;
						return 401;
					}
				}
			}
			
			//calculation A * B & A / B -> result + 0
			for(int i=0;i<i_o;i++) {
				if(operation[i].equals("*")) {
					operation[i]="+";
					num[i]=num[i]*num[i+1];
					num[i+1]=0;
				}
				//Divided by zero exception
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
			
			// Calculation +, -
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
		//Not Number exception
		catch(NumberFormatException e) {
			error=-1;
			result=401;
		}
		
		return result;
	}
}
