package com.dim.komfort.config;

import com.dim.komfort.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configurable
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtRequestFilter jwtRequestFilter;
  private final UserDetailsServiceImpl userDetailsService;

  @Autowired
  public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService,
                              JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                              JwtRequestFilter jwtRequestFilter) {
    this.userDetailsService = userDetailsService;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
  }

  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
//    http.csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/").permitAll()
//            .antMatchers("/getAll").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
////            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .permitAll();
//  }
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // We don't need CSRF for this example
    httpSecurity.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
    httpSecurity.csrf().disable()
            // dont authenticate this particular request
            .authorizeRequests().antMatchers("/authenticate").
            permitAll().antMatchers("/**").permitAll().
            // all other requests need to be authenticated
                    anyRequest().authenticated().and().
            // make sure we use stateless session; session won't be used to
            // store user's state.
                    exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin(CorsConfiguration.ALL);
    config.addAllowedHeader(CorsConfiguration.ALL);
    config.addAllowedMethod(CorsConfiguration.ALL);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // configure AuthenticationManager so that it knows from where to load
    // user for matching credentials
    // Use BCryptPasswordEncoder
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
