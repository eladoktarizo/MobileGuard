package arisryan.mobileeg.activity;

import android.util.Base64;

import org.libsodium.jni.Sodium;
import org.libsodium.jni.SodiumConstants;
import org.libsodium.jni.crypto.Box;
import org.libsodium.jni.crypto.Random;
import org.libsodium.jni.keys.KeyPair;

import java.nio.charset.StandardCharsets;

//import Sodium.*;
public class Asymmetric {
	private static int NONCE_SIZE_BYTES = 24;
	private static byte VERSION = 0x01;

	public static arisryan.mobileeg.activity.KeyPair GenerateKeyPair()
	{

		byte[] seed = new Random().randomBytes(SodiumConstants.SECRETKEY_BYTES);
		KeyPair keyPair = new KeyPair(seed);

		arisryan.mobileeg.activity.KeyPair key = new arisryan.mobileeg.activity.KeyPair();

		key.publickey = keyPair.getPublicKey().toBytes();
		key.privatekey = keyPair.getPrivateKey().toBytes();
		return key;
	}

	public static String Encrypts(String plaintext, byte[] publickey, byte[] privatekey) {
	try
	{
		byte[] nonce = GenerateNonce();
		Box crypto = new Box(publickey, privatekey);
		//byte[] nonce = new byte[Sodium.crypto_box_noncebytes()];
		byte[] result = crypto.encrypt(nonce, plaintext.getBytes(StandardCharsets.UTF_8));
		byte[] record = new byte[25 + result.length];

		record[0] = VERSION;
		int offset = 1;
		System.arraycopy(nonce, 0, record, offset, NONCE_SIZE_BYTES);
		offset += NONCE_SIZE_BYTES;
		System.arraycopy(result, 0, record, offset, result.length);

		return Base64.encodeToString(record, Base64.DEFAULT);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		return null;
	}

	public static String Decrypt(String chipertext, byte[] publickey, byte[] privatekey)
	{
		try
		{
			byte[] record = Base64.decode(chipertext, Base64.DEFAULT);
			if(record[0] == VERSION)
			{
				int offset = 1;
				byte[] nonce = new byte[NONCE_SIZE_BYTES];
				System.arraycopy(record, 1, nonce, 0, NONCE_SIZE_BYTES);

				offset += NONCE_SIZE_BYTES;
				byte[] chiper = new byte[record.length-25];
				System.arraycopy(record, offset, chiper, 0, record.length - 25);

				Box crypto = new Box(publickey, privatekey);
				//byte[] nonce = new byte[Sodium.crypto_box_noncebytes()];
				byte[] result = crypto.decrypt(nonce, chiper);
				String plaintext = new String(result, StandardCharsets.UTF_8);
				return plaintext;
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] GenerateNonce()
	{
		byte[] nonce = new byte[Sodium.crypto_box_noncebytes()];
		Sodium.randombytes_buf(nonce, nonce.length);
		return nonce;
	}

}