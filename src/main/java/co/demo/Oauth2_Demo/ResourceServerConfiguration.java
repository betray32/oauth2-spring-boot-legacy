package co.demo.Oauth2_Demo;

import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.web.bind.annotation.*;


@EnableResourceServer
@RestController
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @RequestMapping("/publica")
  public String publico() {
    return "Pagina Publica";
  }

  @RequestMapping("/privada")
  public String privada() {
    return "Pagina Privada";
  }

  @RequestMapping("/admin")
  public String admin() {
    return "Pagina Administrador";
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize**", "/publica").permitAll();
//	 .anyRequest().authenticated();

    http.requestMatchers().antMatchers("/privada")  //  Denegamos el acceso a "/privada"
        .and().authorizeRequests()
        .antMatchers("/privada").access("hasRole('USER')")
        .and().requestMatchers().antMatchers("/admin") // Denegamos el acceso a "/admin"
        .and().authorizeRequests()
        .antMatchers("/admin").access("hasRole('ADMIN')");
  }

}
