package in.expansetrackerapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.expansetrackerapi.security.CustomUserDetailsService;
import in.expansetrackerapi.security.JwtRequestFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public JwtRequestFilter authenticatJwtTokenFilter()
	{
		return new JwtRequestFilter();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login", "/register").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticatJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.httpBasic();
	}
	
	
	// customize the multiple user [it will not give by default password]
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception
//	{
//		auth.inMemoryAuthentication()
//		.withUser("Abhishek").password("1234").authorities("admin")
//		.and()
//		.withUser("Aniket M").password("1234").authorities("user")
//		.and()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance());
//		
//		
//	}
	
	
//	Creating own custom userDeatils Service for validating the user 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailsService);
		
		
	} 
	

//    @Bean
//    PasswordEncoder passwordEncoder()
//    {
//        return NoOpPasswordEncoder.getInstance();
//    }
	
	  @Bean
	    PasswordEncoder passwordEncoder()
	    {
	        return new BCryptPasswordEncoder();
	    }
	
	  @Bean
	  @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
}
