package shop.mtcoding.jwtstudy.example;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTest {
    // ABC(메타코딩) -> 1313AB
    // ABC(시크) -> 5335KD

    @Test
    public void createJwt_test() throws Exception {
        // given
        String jwt = JWT.create().withSubject("토큰제목") // 토큰의 주인, 유일한 식별자
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 만료시간 - 일주일
                .withClaim("id", 1)
                .withClaim("role", "guest") // 권한설정 ex guest, manager,...
                .sign(Algorithm.HMAC512("메타코딩")); // 알고리즘이 들어가는 위치 // HMAC - 해시함수 + 대칭키
        System.out.println("테스트 : " + jwt);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLthqDtgbDsoJzrqqkiLCJyb2xlIjoiZ3Vlc3QiLCJleHAiOjE2Nzk4OTQ5NDd9.uDnJDntD9l2HqOOuOcgdGXHl79micf7baXhcjjDc8K7HaEJI3iEMwOf3ip-nthdny-Ds_XesnAgDTBfbsYuoYA
        // 복사후 jwt.io 이동
    }

    @Test
    public void verifyJwt_test() throws Exception {
        // given
        String jwt = JWT.create().withSubject("토큰제목") // 토큰의 주인, 유일한 식별자
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 만료시간 - 일주일
                .withClaim("id", 1)
                .withClaim("role", "guest") // 권한설정 ex guest, manager,...
                .sign(Algorithm.HMAC512("메타코딩")); // 알고리즘이 들어가는 위치 // HMAC - 해시함수 + 대칭키
        System.out.println("테스트 : " + jwt);
        
        // when
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                        .build().verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(id);
            System.out.println(role);
        } catch (SignatureVerificationException e) {
            System.out.println("검증 실패 - 시그니처 오류" + e.getMessage()); // 위조됨
        } catch (TokenExpiredException e){
            System.out.println("토큰 만료"+ e.getMessage());
        }
    }
}
