package com.slim.ngq.utils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

public class JwtTokenUtils {

	
	public static String generateToken(String username, Set<String> roles, Long duration, String issuer) throws Exception {
		String privateKeyLocation = "/META-INF/resources/privatekey.pem";
		PrivateKey privateKey = readPrivateKey(privateKeyLocation);
		
		JwtClaimsBuilder claimsBuilder = Jwt.claims();
		long currentTimeInSecs = currentTimeInSecs();
		
		Set<String> groups = new HashSet<>();
		roles.forEach(role -> groups.add(role.toString()));

		claimsBuilder.issuer(issuer);
		claimsBuilder.subject(username);
		claimsBuilder.issuedAt(currentTimeInSecs);
		claimsBuilder.expiresAt(currentTimeInSecs + duration);
		claimsBuilder.groups(groups);

		return claimsBuilder.jws().sign(privateKey);
	}

	public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
			
		try (InputStream contentIS = JwtTokenUtils.class.getResourceAsStream(pemResName)) {
			byte[] tmp = new byte[4096];
			int length = contentIS.read(tmp);
			return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
		}
	}

	public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
		byte[] encodedBytes = toEncodedBytes(pemEncoded);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(keySpec);
	}

   public static byte[] toEncodedBytes(final String pemEncoded) {
		final String normalizedPem = removeBeginEnd(pemEncoded);
		return Base64.getDecoder().decode(normalizedPem);
	}

	public static String removeBeginEnd(String pem) {
		pem = pem.replaceAll("-----BEGIN (.*)-----", "");
		pem = pem.replaceAll("-----END (.*)----", "");
		pem = pem.replaceAll("\r\n", "");
		pem = pem.replaceAll("\n", "");
		return pem.trim();
	}

	public static int currentTimeInSecs() {
		long currentTimeMS = System.currentTimeMillis();
		return (int) (currentTimeMS / 1000);
	}
	
	public static  boolean verifyPassword(String originalPwd, String encryptedPwd) throws Exception {
		// convert encrypted password string to a password key
		Password rawPassword = ModularCrypt.decode(encryptedPwd);

		// create the password factory based on the bcrypt algorithm
		PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT);

		// create encrypted password based on stored string
		BCryptPassword restored = (BCryptPassword) factory.translate(rawPassword);

		// verify restored password against original
		return factory.verify(restored, originalPwd.toCharArray());
	}

}

