package in.expansetrackerapi.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtTokenUtil 
{
// this is the process of creating jwt token
	private static final long JWT_TOKEN_VALIDITY = 5*60*60;
	@Value("${jwt.secret}")  // this is how we can take the value from application. properties
	private String secret;

	public String generateToken(UserDetails userDetails) 
	{
		Map<String,Object> claims = new HashMap<>();
		
		
		return Jwts.builder()
		.setClaims(claims)
		.setSubject(userDetails.getUsername()) // here username means email
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
		.signWith(SignatureAlgorithm.HS512, secret)
		.compact();
		
	}
	
	public String getUserNameFromToken(String jwtToken)
	{
		return getClaimFormToken(jwtToken, Claims:: getSubject);
	}
	
	private<T> T getClaimFormToken(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims= Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); // this will return the claims 
//		and once we get the calims so we can get all the property whatever we set setSubject,setIssuedAt etc.we can take it all from token
		
		return claimsResolver.apply(claims);
	}

	public boolean validateToken(String jwtToken, UserDetails userDetails)
	{
		final String username = getUserNameFromToken(jwtToken);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
	}

	private boolean isTokenExpired(String jwtToken)
	{
		Date expiration = getExpirationDateFromToken(jwtToken);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String jwtToken)
	{
		return getClaimFormToken(jwtToken, Claims::getExpiration);
		
	}

}
