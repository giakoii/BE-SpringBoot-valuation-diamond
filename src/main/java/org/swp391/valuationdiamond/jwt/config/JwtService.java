//package org.swp391.valuationdiamond.jwt.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
////class này sẽ giúp chúng ta tạo ra token, kiểm tra token có hợp lệ không, lấy thông tin từ token
//@Service
//public class JwtService {
//
//    private static final String SECRET_KEY = "f0364d0aa800949cf3fc345759ba389c80d0222789cbfaaf201de973b18a68630f51d8f6969baea6abcd3020d0ae88d251b1146f26ac5c75b2e097c24f7011bc";
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    //check xem token có hết hạn không
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    //check xem token có hợp lệ không
//    public boolean isValidToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    //check xem token có hết hạn không
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    //check xem token có hết hạn không
//    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
//        return Jwts
//                .builder()
//                .setClaims(extractClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis())) //check xem ngày hiện tại có phải là ngày hợp lệ không
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //nghĩa là token sẽ hết hạn sau 24h
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    //check xem token có hết hạn không
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJwt(token)
//                .getBody();
//
//    }
//
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}
