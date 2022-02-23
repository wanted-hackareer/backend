package backend.core.global.security;

import backend.core.global.error.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            String memberId = tokenProvider.validateAndGetUserId(token);

            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(memberId, null, AuthorityUtils.NO_AUTHORITIES);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            log.info("expired token exception");
            setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            e.printStackTrace();
            log.info("invalid token exception");
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        } catch (SecurityException | MalformedJwtException e) {
            e.printStackTrace();
            log.info("wrong token exception");
            setErrorResponse(response, ErrorCode.WRONG_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            HashMap map = new HashMap();
            map.put("status", errorCode.getHttpStatus().value());
            map.put("error", errorCode.getHttpStatus().name());
            map.put("code", errorCode.name());
            map.put("message", errorCode.getMessage());

            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(map));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
