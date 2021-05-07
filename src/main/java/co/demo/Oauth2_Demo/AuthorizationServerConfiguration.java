package co.demo.Oauth2_Demo;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.oauth2.config.annotation.configurers.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.*;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {


  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  WebSecurityConfiguration webSecurityConfiguration;


  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    clients.inMemory()
        .withClient("cliente")
        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
        .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
        .scopes("read", "write")
        .autoApprove(true)
        .secret(webSecurityConfiguration.passwordEncoder().encode("secret"));

  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .tokenStore(tokenStore);
  }

  @Bean
  public TokenStore tokenStore() {
    return new InMemoryTokenStore();
  }

}
