package app.m.advise.endpoint.security;

import app.m.advise.model.User;
import app.m.advise.service.AuthService;
import app.m.advise.service.api.firebase.FUser;
import app.m.advise.service.api.firebase.FirebaseService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {
  private final AuthService authService;
  private final FirebaseService firebaseService;

  protected AuthFilter(
      RequestMatcher requestMatcher, AuthService authService, FirebaseService firebase) {
    super(requestMatcher);
    this.authService = authService;
    this.firebaseService = firebase;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authenticated)
      throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authenticated);
    chain.doFilter(request, response);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String token = AuthProvider.getBearer(request);
    if (token == null || token.isEmpty()) {
      throw new AuthenticationServiceException("Bearer token is missing or invalid");
    }
    FUser authUser = firebaseService.getUserByBearer(token);
    if (authUser == null) {
      throw new AuthenticationServiceException("Bearer token is expired or invalid");
    }
    User user = authService.findByAuthenticationIdAndEmail(authUser.getId(), authUser.getEmail());
    var principal = new Principal(token, user);
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(principal, token);

    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return getAuthenticationManager().authenticate(authentication);
  }
}
