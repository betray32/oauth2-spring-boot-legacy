package co.demo.Oauth2_Demo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.provisioning.*;

@EnableWebSecurity
@SpringBootApplication
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(WebSecurityConfiguration.class, args);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {

    UserDetails user = User.builder().username("user").password(passwordEncoder().encode("secret")).
        roles("USER").build();
    UserDetails userAdmin = User.builder().username("admin").password(passwordEncoder().encode("secret")).
        roles("ADMIN").build();
    return new InMemoryUserDetailsManager(user, userAdmin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/index", "/webpublico").permitAll()
        .antMatchers("/webprivado").authenticated()
        .antMatchers("/webadmin").hasRole("ADMIN").and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout() // Metodo get pues he desabilitado CSRF
        .permitAll();
  }

}
