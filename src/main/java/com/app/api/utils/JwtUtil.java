package com.app.api.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.app.api.enums.AccessEnum;
import com.app.api.enums.TokenEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil{

	@Value("${prop.JWT_SECRET}")
	private String JWT_SECRET;

	@Value("${prop.REFRESH_SECRET}")
	private String REFRESH_SECRET;

	public String createToken(
		int id,
		String unique,
		TokenEnum type,
		AccessEnum tokenType
	) {
		SignatureAlgorithm signatureAlgorithm = 
			tokenType == AccessEnum.USER ? 
				SignatureAlgorithm.HS256 : 
				SignatureAlgorithm.HS512;
		Date expireTime  = new Date();
		
		expireTime.setTime(expireTime.getTime() + 1000 * 3600 * 24);

		String secretKeyEncodeBase64 = Encoders.BASE64.encode(
			type == TokenEnum.ACCESS_TOKEN ? 
				JWT_SECRET.getBytes() :
				REFRESH_SECRET.getBytes()
		);
		byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
		Key key = Keys.hmacShaKeyFor(keyBytes);

		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("unique", unique);
		map.put("access" , tokenType.ordinal());

		JwtBuilder builder = Jwts.builder()
			.setClaims(map);

		builder
			.setIssuer("jjh")
			.signWith(key, signatureAlgorithm);

		return builder.compact();
	}
  
	public Claims verifyToken(String jwt , TokenEnum type){
		String[] token = jwt.split("Bearer ");
		try {
			@SuppressWarnings("deprecation")
			Claims claims = Jwts.parser()
			.setSigningKey(
				TokenEnum.ACCESS_TOKEN == type ? 
					JWT_SECRET.getBytes() :
					REFRESH_SECRET.getBytes()
			)
			.parseClaimsJws(token[1])
			.getBody();

			return claims;

		} catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
			return null;
		}
	}

}
