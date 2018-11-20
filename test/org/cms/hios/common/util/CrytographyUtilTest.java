package org.cms.hios.common.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Hex;
import org.junit.BeforeClass;
import org.junit.Test;

public class CrytographyUtilTest {

	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("KEY_FILE", "./test-resources/KeyFile.properties");
		
	}
	
	@Test
	public void testEncrypt() throws Exception{
		CryptographyUtil instance = CryptographyUtil.getInstance();
		String originalString = "T@@# is ERERdff";
		String encryptedString = instance.encrypt(originalString);
		assertNotNull(encryptedString);
		assertTrue(originalString.equals(decrypt(encryptedString)));
		
		
	}
	
	@Test
	public void testEncrypt1() throws Exception{
		
		CryptographyUtil instance = CryptographyUtil.getInstance();
		String originalString = "RbisTeestUser";
		String encryptedString = instance.encrypt(originalString);
		assertNotNull(encryptedString);
		
		
		
		CryptographyUtil instance1 = CryptographyUtil.getInstance();
		String originalString1 = "RbisTeestUser";
		String eStr = instance1.encrypt(originalString1);
		assertNotNull(eStr);
		assertTrue(eStr.equals(encryptedString));
		
		
	}
	@Test
	public void testDecrypt() throws Exception{
		CryptographyUtil instance1 = CryptographyUtil.getInstance();
		String originalString1 = "RbisTeestUser";
		String eStr = instance1.encrypt(originalString1);
		assertNotNull(eStr);
		String decStr= instance1.decrypt(eStr);
		assertTrue(originalString1.equals(decStr));
		
	}
	
	private String decrypt(String str) throws Exception {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream(System.getProperty("KEY_FILE")));
             
		String keyPhrase = prop.getProperty("KEY");
		
		DESedeKeySpec keySpec = new DESedeKeySpec(keyPhrase.getBytes());
		SecretKey key = SecretKeyFactory.getInstance(CryptographyUtil.DESEDE_ENCRYPTION_ALGORITHM).generateSecret(keySpec);
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		//encryptCipher = Cipher.getInstance(CryptographyUtil.TRANSFORMATION);
		Cipher decryptCipher = Cipher.getInstance(CryptographyUtil.TRANSFORMATION);
		
		decryptCipher.init(Cipher.DECRYPT_MODE, key, iv);
		
		
		byte[] aa = Hex.decodeHex(str.toCharArray());
		byte[] utf8 = decryptCipher.doFinal(aa);
		System.out.println(new String(utf8));
		return new String(utf8);
	}
}
