package in.expansetrackerapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import in.expansetrackerapi.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

public class JwtRequestFilter extends OncePerRequestFilter
{
	@Autowired
	private CustomUserDetailsService userDetailServce;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String jwtToken=null;
		String username=null;
		final String requestTokenHeader = request.getHeader("Authorization");
		
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
		{
			 jwtToken = requestTokenHeader.substring(7);
			 
			 try 
			 {
				 username =jwtTokenUtil.getUserNameFromToken(jwtToken);
			} catch (IllegalArgumentException e) 
			 {
				throw new RuntimeException("unable to get jwt Token");
			}
			 catch (ExpiredJwtException e) 
			 {
				throw new RuntimeException("jwt Token has expired");
			}
		}
		
		
//		Once we get the token , we validate the Token
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = userDetailServce.loadUserByUsername(username);
			
			if(jwtTokenUtil.validateToken(jwtToken, userDetails))
			{
				UsernamePasswordAuthenticationToken authToken = new 
						UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
			    SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
   
}
