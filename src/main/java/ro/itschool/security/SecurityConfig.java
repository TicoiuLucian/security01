package ro.itschool.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.itschool.entity.RoleEnum;
import ro.itschool.repository.MyRoleRepository;
import ro.itschool.repository.MyUserRepository;
import ro.itschool.service.MyUserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private static final String[] WHITE_LIST_URL = {
          "/users/all/nickname",
          "/v3/api-docs/**",
          "/swagger-ui/**"};

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                                           .requestMatchers(HttpMethod.POST, "/users")
                                           .permitAll()
                                           .requestMatchers(WHITE_LIST_URL)
                                           .permitAll()
                                           .requestMatchers("/users/all/username")
                                           .hasAnyAuthority(RoleEnum.ROLE_USER.name(), RoleEnum.ROLE_ADMIN.name())
                                           .requestMatchers("/users/all")
                                           .hasAuthority(RoleEnum.ROLE_ADMIN.name())
                                           .anyRequest().authenticated()
                                  )
            .httpBasic(withDefaults())
            .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  private UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
    UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
    filter.setFilterProcessesUrl("/login");
    return filter;
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(
          MyUserRepository myUserRepository,
          MyRoleRepository myRoleRepository) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(new MyUserService(myUserRepository, myRoleRepository));
    authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
    return authProvider;
  }

}
