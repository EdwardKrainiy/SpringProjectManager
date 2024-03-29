package com.innowise.springprojectmanager.security.jwt.provider;

import com.innowise.springprojectmanager.utils.literal.PropertySourceClasspath;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * TokenProvider class. Methods of this class creates and decodes tokens.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Component
@PropertySource(PropertySourceClasspath.JWT_PROPERTIES_CLASSPATH)
public class TokenProvider implements Serializable {
  @Value("${jwt.delimiter}")
  private String delimiter;

  @Value("${jwt.auth.token.expiration.time.multiplicator}")
  private int authTokenExpirationTimeMultiplicator;

  @Value("${jwt.confirm.token.expiration.time.multiplicator}")
  private int confirmTokenExpirationTimeMultiplicator;

  @Value("${jwt.authorities.key}")
  private String authoritiesKey;

  @Value("${jwt.token.validity}")
  private long tokenValidity;

  @Value("${jwt.signing.key}")
  private String signingKey;

  @Value("${jwt.confirmation.key}")
  private String confirmationKey;

  /**
   * getUsernameFromToken method.
   *
   * @param token Token, from which we want to obtain username.
   * @param key Secret key to decode our token correctly.
   * @return username Returns obtained username.
   */
  public String getUsernameFromToken(String token, String key) {
    return getClaimFromToken(token, Claims::getSubject, key);
  }

  /**
   * getExpirationDateFromToken method.
   *
   * @param token Token, from which we want to obtain expDate.
   * @param key Secret key to decode our token correctly.
   * @return expirationDate Returns obtained date.
   */
  public Date getExpirationDateFromToken(String token, String key) {
    return getClaimFromToken(token, Claims::getExpiration, key);
  }

  /**
   * getClaimFromToken method.
   *
   * @param token Token, from which we want to obtain any info(claim).
   * @param claimsResolver Function, that said our method about what information to get.
   * @param key Secret key to decode our token correctly.
   * @return T Returns obtained info.
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String key) {
    final Claims claims = getAllClaimsFromToken(token, key);
    return claimsResolver.apply(claims);
  }

  /**
   * getAllClaimsFromToken method.
   *
   * @param token Token, from which we want to obtain all infos(claims).
   * @param key Secret key to decode our token correctly.
   * @return claims Returns list of all infos, which was coded into the token.
   */
  private Claims getAllClaimsFromToken(String token, String key) {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
  }

  /**
   * generateConfirmToken method. Generates JWT to confirm email.
   *
   * @param userId Id of user we need to confirm and activate.
   * @return String Returns us generated JWT for confirmation.
   */
  public String generateConfirmToken(Long userId) {
    return Jwts.builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(
                System.currentTimeMillis()
                    + tokenValidity * confirmTokenExpirationTimeMultiplicator))
        .signWith(SignatureAlgorithm.HS256, confirmationKey)
        .compact();
  }

  /**
   * isTokenExpired method.
   *
   * @param token Token, from which we want to check for expiration date.
   * @param key Secret key to decode our token correctly.
   * @return boolean Checks and returns info about expiration of our token.
   */
  public Boolean isTokenExpired(String token, String key) {
    final Date expiration = getExpirationDateFromToken(token, key);
    return expiration.before(new Date());
  }

  /**
   * generateAuthToken method. Generates JWT to authenticate.
   *
   * @param authentication Contains info about user, which want to authenticate.
   * @return String Returns us generated JWT for authentication.
   */
  public String generateAuthToken(Authentication authentication) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(delimiter));

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(authoritiesKey, authorities)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(
                System.currentTimeMillis() + tokenValidity * authTokenExpirationTimeMultiplicator))
        .signWith(SignatureAlgorithm.HS256, signingKey)
        .compact();
  }

  /**
   * validateToken method. Validates our token.
   *
   * @param token Token, which we need to validate.
   * @param userDetails Contains all info about user.
   * @param key Secret key to decode our token correctly.
   * @return Boolean Checks and returns info about validity of our token.
   */
  public Boolean validateToken(String token, UserDetails userDetails, String key) {
    final String username = getUsernameFromToken(token, key);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, key));
  }

  /**
   * getAuthenticationToken method. Generates UsernamePasswordAuthenticationToken from token.
   *
   * @param token Token, which we need to transform.
   * @param userDetails Contains all info about user.
   * @return UsernamePasswordAuthenticationToken Returns UsernamePasswordAuthenticationToken for
   *     authentication.
   */
  public UsernamePasswordAuthenticationToken getAuthenticationToken(
      final String token, final UserDetails userDetails) {

    final JwtParser jwtParser = Jwts.parser().setSigningKey(signingKey);

    final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

    final Claims claims = claimsJws.getBody();

    final Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(authoritiesKey).toString().split(delimiter))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }
}
