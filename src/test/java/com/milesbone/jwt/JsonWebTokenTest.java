package com.milesbone.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonWebTokenTest {

	private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenTest.class);
	
//	private static String createJWT(String id, String issuer, String subject, long ttlMillis){
//		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
//		long nowMillis = System.currentTimeMillis();
//		Date now = new Date(nowMillis);
//		
//		
//		byte[] apiKeySecretByte = Base64.altBase64ToByteArray("12345678");
////		byte[] apiKeySecretByte = DatatypeConverter.parseBase64Binary("1234567890");
//		
//		Key singningkey = new SecretKeySpec(apiKeySecretByte, signatureAlgorithm.getJcaName());
//		
//		
//		//Let's set the JWT Claims
//		JwtBuilder builder = Jwts.builder().setId(id)
//		                                .setIssuedAt(now)
//		                                .setSubject(subject)
//		                                .setIssuer(issuer)
//		                                .signWith(signatureAlgorithm, singningkey);
//		
//		if(ttlMillis >= 0){
//			long expmillis = nowMillis + ttlMillis;
//			Date exp = new Date(expmillis);
//			builder.setExpiration(exp);
//		}
//		
//		return builder.compact();
//	}
//	
//	
//	
//	private static void parseJWT(String jwt){
//		Claims claims = Jwts.parser()
//						.setSigningKey(Base64.altBase64ToByteArray("12345678"))
//						.parseClaimsJws(jwt).getBody();
//		logger.debug("ID:{}",claims.getId());
//		logger.debug("subject:{}",claims.getSubject());
//		logger.debug("ISSUER:{}",claims.getIssuer());
//		logger.debug("Expiration:{}",claims.getExpiration());
//	}
//	
//	
//	@Test
//	public void testToString(){
//		String compat = createJWT("id1", "admin", "testtoken", 100*1000);
//		logger.debug("获取token: {}",compat);
//		logger.debug("开始验证token....");
//		parseJWT(compat);
//	}
//	
//	@Test
//	public void testValid(){
//		String jwtStr = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJpZDEiLCJpYXQiOjE0NjgyMjc3NzIsInN1YiI6InRlc3R0b2tlbiIsImlzcyI6ImFkbWluIiwiZXhwIjoxNDY4MjI3ODcyfQ.klEfMVOxEjA1H-nbWux2trAXe0SPNNnrC5FyEFxigvLD-L026FkkRPiEffsAKHlZKTbI7lD9zp2sTHopDn3iaA";
//		parseJWT(jwtStr);
//	}
}
