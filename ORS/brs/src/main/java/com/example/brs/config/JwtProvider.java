    package com.example.brs.config;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import java.util.Collection;
    import java.util.Date;
    import java.util.HashSet;
    import java.util.Set;

    @Component
    public class JwtProvider {

        private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET.getBytes());

        public String generateToken(Authentication auth) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            String roles = populateAuthorities(authorities);

            String jwt = Jwts.builder()
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + JwtConstant.EXPIRATION_TIME))
                    .claim("username", auth.getName())
                    .claim("authorities", roles)
                    .signWith(key)
                    .compact();

            return jwt;
        }
    //    getusernamebytoken

        public String getUsernameFromJwtToken(String jwt){
            jwt=jwt.substring(7);
            Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String username=String.valueOf(claims.get("username"));
            return username;
        }

        private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
            Set<String> auth=new HashSet<>();
            for(GrantedAuthority authority:authorities){
                auth.add(authority.getAuthority());
            }
            return String.join(",",auth);
        }
    }
