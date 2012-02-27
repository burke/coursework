/* Simple RSA Example.

- The RSA algorithm makes use of Java's support for large 
numbers, specifically the BigInteger class. This class 
provides direct support not only for arbitrarily large 
integers, but also provides special functions required to
calculate private/public key pairs.
- In this program a simple number is encrypted and decrypted
as a native BigInteger.
- In order to encrypt text data, the text string must first 
be broken into substrings and converted to a BigInteger. The
resulting BigInteger should not exceed the key size.

*/

import java.math.BigInteger;
import java.io.*;
import java.util.Random;

class rsa {

	static public void main(String a[]) {
		
		BigInteger one = new BigInteger("1");

		BigInteger p = new BigInteger(300,20, new Random());
		System.out.println("P = " + p);

		BigInteger q = new BigInteger(300,20, new Random());
		System.out.println("Q = " + q);

		BigInteger n = p.multiply(q);

		BigInteger phi = p.subtract(one).multiply(q.subtract(one));
		System.out.println("PHI = " + phi);

		BigInteger d = new BigInteger(4,1, new Random());
		BigInteger gcd = phi.gcd(d);
		while (!gcd.equals(one)){
			System.out.println("PHI gcd " + d + " = " + gcd + " (try again)");
			d = new BigInteger(300,1, new Random());
			gcd = phi.gcd(d);
		}	
		System.out.println("PHI gcd " + d + " = " + phi.gcd(d));

		BigInteger e = d.modInverse(phi);
		System.out.println("e = " + e);

		BigInteger tmp = d.multiply(e).mod(phi);
		System.out.println("d e mod phi = " + tmp);







		BigInteger m = new BigInteger("123456789012345678901234567890123456789012345678901234567890");

		BigInteger mc = m.modPow(e,n);	
		BigInteger mx = mc.modPow(d,n);
		System.out.println("m ** e d mod n = " + mx);
		
	}

}