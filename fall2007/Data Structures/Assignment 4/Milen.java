import java.io.*;
import java.util.LinkedList;

class Milen {
	public static void main( String args[] ) {
		BufferedReader fin;
		String line="";
		int num_sims = 0;
		
		try {
			fin = new BufferedReader( new FileReader( "testdata.txt" ) );
			
			num_sims = Integer.parseInt(fin.readLine());

			while((line = fin.readLine())!=null) {
				if(line.equals(""))
					continue; //Just ignore it.
				else if(line.equals("Q"))
					break; //Oh yeah. I went there.
				else 
					evaluate(line);
			}


		} catch( IOException e ) {
			System.out.println("I/O Error.");
			System.exit(1);
		}
		
	}
	
	private static boolean evaluate(String line) {
		int value;
		
		String lhs = line.split(" = ")[0];
		String rhs = line.split(" = ")[1];
		System.out.println(rhs);
		
		value = simplify(rhs);
		
		return true;
	}
	
	private static int simplify(String expr) {
		int result;
		LinkedList els;
		
		
		
	}
	
	private class Hash {
		
		
		
		public Hash() {
			
		}
		
	}
}