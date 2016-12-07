package votes.jani;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import votes.jani.web.UserDetailServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl userDetailsService;	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable() 					//For H2
        .headers().frameOptions().disable()	//For H2
        .and()
        .authorizeRequests().antMatchers("/css/**", "/imgs/**").permitAll() // Enable css even if the user is logged out, we want to look cool :)
        .and()
        .authorizeRequests().antMatchers("/console", "/register", "/h2-console", "/h2-console/**", "/adduser", "/").permitAll()
        .and()
    	.authorizeRequests().antMatchers("/testarea").permitAll() // Enable testing area for developing this program
    	.and()
    	.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN") //This is for the admin area to create new votes
    	.and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .failureUrl("/loginerror")
          .defaultSuccessUrl("/votingsystem")          
          .permitAll()
          .and()
      .logout()
      	  .logoutUrl("/logout")
      	  .logoutSuccessUrl("/login")
          .permitAll();
          
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        .withUser("user").password("password").roles("USER").and()
        .withUser("admin").password("password").roles("ADMIN");
    	
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
