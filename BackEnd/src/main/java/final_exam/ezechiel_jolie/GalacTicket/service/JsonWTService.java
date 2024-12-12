package final_exam.ezechiel_jolie.GalacTicket.service;


import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.repository.AccessKeyRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.function.Function;

@Service
public class JsonWTService {

    private final String SECRET_KEY = "5cf4f39180923687d32e9eecb114d967d527d2d411a5f4c77d4915dfd6db07d6971efd119a79ff59f8d0703d1889064f2e58599f2c193800fb959d4ec7584ec103b8f5a5539dcb9d818dc517853ca08bec519b673dcb98dae8b3a0571f24964643efaaae2f034410d15b86ba3deba1edb9636d782941845523ba69b2959b8b9a08b0bde10277e0c78f03e9261bb99f8532ab45898e79626be3b004971bbdfb8e611894f063e900dba64a9632f82bc66ff316bad2be95e11e6a4c75b6a91aa6979914437ab189511f646b1c73992e3e06d569d7023cd85a9cabb9c9bcd7b88be551350fa4f3fd152e92b3b46a3cdde9dbf1f4272c5488f5dcfe91187121427a0e";
    private final AccessKeyRepository accessKeyRepository;

    public JsonWTService(AccessKeyRepository accessKeyRepository) {
        this.accessKeyRepository = accessKeyRepository;
    }

    public String extractUsername(String accessKey) {
        return extractClaim(accessKey, Claims::getSubject);
    }


    public boolean isValid(String accessKey, UserDetails user) {
        String username = extractUsername(accessKey);

        boolean validToken = accessKeyRepository
                .findByKeyValue(accessKey)
                .map(t -> !t.isDeactivated())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(accessKey) && validToken;
    }

    private boolean isTokenExpired(String accessKey) {
        return extractExpiration(accessKey).before(new Date());
    }

    private Date extractExpiration(String accessKey) {
        return extractClaim(accessKey, Claims::getExpiration);
    }

    public <T> T extractClaim(String accessKey, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(accessKey);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String accessKey) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(accessKey)
                .getPayload();
    }


    public String generateToken(User user) {
        String accessKey = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();

        return accessKey;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
