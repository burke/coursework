import java.math.BigInteger;
import java.io.*;
import java.util.Random;

class RSAObject {
	private class RSAKey {
		
		private BigInteger n, d, e;
		
		public RSAKey() {
			BigInteger one = new BigInteger("1");
			
			BigInteger p = new BigInteger(300,20, new Random());
			BigInteger q = new BigInteger(300,20, new Random());
			
			n = p.multiply(q);
			
			BigInteger phi = p.subtract(one).multiply(q.subtract(one));
			
			d = new BigInteger(4,1, new Random());
			
			BigInteger gcd = phi.gcd(d);
			while (!gcd.equals(one)){
				d = new BigInteger(300,1, new Random());
				gcd = phi.gcd(d);
			}
			
			e = d.modInverse(phi);
		}
		
		public String toString() {
			return null;
		}
		
		public boolean equals() {
			return false;
		}
		
		public BigInteger getExp() {
			return e;
		}
		
		public BigInteger getMod() {
			return n;
		}

	} //RSAKey

	public static void main(String args[]) {
		
	}

}
