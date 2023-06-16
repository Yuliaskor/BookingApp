package com.example.bookingapp.security.filters;

import com.example.bookingapp.security.service.GoogleAuthService;
import com.example.bookingapp.security.user.GoogleUser;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RequiredArgsConstructor
@Component
public class GoogleTokenFilter extends OncePerRequestFilter {

    public static final String AUTHENTICATION_HEADER = "Authorization";
    public static final String AUTHENTICATION_HEADER_TOKEN_PREFIX = "Bearer ";
    private static final String OPENAPI_DOCS_URL = "/v3/api-docs";
    private static final String SWAGGER_UI_URL = "/swagger-ui/";

    private final GoogleAuthService googleAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith(SWAGGER_UI_URL) || path.startsWith(OPENAPI_DOCS_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        // if no authentication header is present, continue with the filter chain and don't check credentials
        String authenticationHeader = request.getHeader(AUTHENTICATION_HEADER);
        if (authenticationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        GoogleIdToken decodedToken;
        try {
            decodedToken = validateAndGetTokenFromHeader(authenticationHeader);
        } catch (GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token!");
        }

        grantUserAuthorities(decodedToken);

        filterChain.doFilter(request, response);
    }

    private GoogleIdToken validateAndGetTokenFromHeader(String authenticationHeader) throws GeneralSecurityException, IOException {
        if (authenticationHeader == null || !authenticationHeader.startsWith(AUTHENTICATION_HEADER_TOKEN_PREFIX)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token!");
        }

        int authenticationHeaderPrefixLength = AUTHENTICATION_HEADER_TOKEN_PREFIX.length();
        String token = authenticationHeader.substring(authenticationHeaderPrefixLength);
        return googleAuthService.validateToken(token)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token!"));
    }

    private void grantUserAuthorities(GoogleIdToken token) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        GoogleUser.fromTokenPayload(token.getPayload()),
                        null,
                        null));
    }
}
