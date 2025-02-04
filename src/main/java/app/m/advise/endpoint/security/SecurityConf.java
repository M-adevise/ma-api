package app.m.advise.endpoint.security;

import static app.m.advise.model.Role.DOCTOR;
import static app.m.advise.model.Role.PATIENT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import app.m.advise.endpoint.matcher.SelfUserMatcher;
import app.m.advise.model.exception.ForbiddenException;
import app.m.advise.service.AuthService;
import app.m.advise.service.api.firebase.FirebaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConf {
  private final HandlerExceptionResolver exceptionResolver;
  private final AuthProvider provider;
  private final AuthService authService;
  private final FirebaseService firebaseService;

  public SecurityConf(
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
      AuthProvider auth,
      AuthService authService,
      FirebaseService firebase) {
    exceptionResolver = resolver;
    provider = auth;
    this.authService = authService;
    this.firebaseService = firebase;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authenticationProvider(provider)
        .addFilterBefore(
            bearerFilter(
                new NegatedRequestMatcher(
                    new OrRequestMatcher(
                        new AntPathRequestMatcher("/ping"),
                        new AntPathRequestMatcher("/raw/*"),
                        new AntPathRequestMatcher("/hospitals", GET.name()),
                        new AntPathRequestMatcher("/signup", POST.name()),
                        new AntPathRequestMatcher("/**", OPTIONS.toString())))),
            AnonymousAuthenticationFilter.class)
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/ping")
                    .permitAll()
                    .requestMatchers(POST, "/signup")
                    .permitAll()
                    .requestMatchers(GET, "/hospitals")
                    .permitAll()
                    .requestMatchers(PUT, "/hospitals")
                    .authenticated()
                    .requestMatchers(GET, "/doctors")
                    .authenticated()
                    .requestMatchers(GET, "/department/*/doctors")
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/doctors/*", provider))
                    .hasRole(DOCTOR.getRole())
                    .requestMatchers(new SelfUserMatcher(PUT, "/doctors/*", provider))
                    .hasRole(DOCTOR.getRole())
                    .requestMatchers(GET, "/patients/*")
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/doctors/*/patients", provider))
                    .authenticated()
                    .requestMatchers(GET, "/doctors/*/feedbacks")
                    .authenticated()
                    .requestMatchers(PUT, "/doctors/*/feedbacks")
                    .hasRole(PATIENT.getRole())
                    .requestMatchers(POST, "/signin")
                    .authenticated()
                    .requestMatchers(GET, "/appointments/*")
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/doctors/*/appointments", provider))
                    .hasAnyRole(DOCTOR.getRole())
                    .requestMatchers(new SelfUserMatcher(GET, "/patients/*/appointments", provider))
                    .hasAnyRole(PATIENT.getRole())
                    .requestMatchers(PUT, "/appointments/*")
                    .hasRole(DOCTOR.getRole())
                    .requestMatchers(new SelfUserMatcher(POST, "/users/*/raw", provider))
                    .authenticated()
                    .requestMatchers(PUT, "/patients/*/medical_info")
                    .hasRole(DOCTOR.getRole())
                    .requestMatchers(GET, "/patients/*/medical_info")
                    .hasRole(DOCTOR.getRole())
                    .requestMatchers(POST, "/call/tokens")
                    .authenticated()
                    .requestMatchers(POST, "/raw/*")
                    .authenticated()
                    .requestMatchers(GET, "/raw/*")
                    .authenticated()
                    .requestMatchers(PUT, "/channel/*")
                    .authenticated()
                    .requestMatchers(new SelfUserMatcher(GET, "/users/*/channels", provider))
                    .authenticated()
                    .requestMatchers(PUT, "/channels/*/messages/*")
                    .authenticated()
                    .requestMatchers(GET, "/channels/*/messages")
                    .authenticated()
                    .requestMatchers(PUT, "/prompts")
                    .authenticated()
                    .anyRequest()
                    .denyAll())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(AbstractHttpConfigurer::disable);
    return http.build();
  }

  private Exception forbiddenWithRemoteInfo(Exception e, HttpServletRequest req) {
    log.info(
        String.format(
            "Access is denied for remote caller: address=%s, host=%s, port=%s",
            req.getRemoteAddr(), req.getRemoteHost(), req.getRemotePort()));
    return new ForbiddenException(e.getMessage());
  }

  private AuthFilter bearerFilter(RequestMatcher requestMatcher) {
    AuthFilter bearerFilter = new AuthFilter(requestMatcher, authService, firebaseService);
    bearerFilter.setAuthenticationManager(authenticationManager());
    bearerFilter.setAuthenticationSuccessHandler(
        (httpServletRequest, httpServletResponse, authentication) -> {});
    bearerFilter.setAuthenticationFailureHandler(
        (req, res, e) ->
            exceptionResolver.resolveException(req, res, null, forbiddenWithRemoteInfo(e, req)));
    return bearerFilter;
  }

  protected AuthenticationManager authenticationManager() {
    return new ProviderManager(provider);
  }
}
