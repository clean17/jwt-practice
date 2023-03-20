package shop.mtcoding.jwtstudy.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.jwtstudy.config.auth.JwtProvider;
import shop.mtcoding.jwtstudy.config.auth.LoginUser;


// public class JwtVerifyFilter implements Filter {
//     @Override
//     public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//         HttpServletRequest req = (HttpServletRequest) request;
//         HttpServletResponse resp = (HttpServletResponse) response;
//         String prefixJwt = req.getHeader(JwtProvider.HEADER);
//         String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
//         try {
//             DecodedJWT decodedJWT = JwtProvider.verify(jwt);
//             int id = decodedJWT.getClaim("id").asInt();
//             String role = decodedJWT.getClaim("role").asString();
//             // 세션을 임시적으로 넣어준다.
//             // 내부에서 사용할 권한처리에 필요한 권한을 세션에 넣어줌   
//             HttpSession session =  req.getSession();
//             LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
//             session.setAttribute("loginUser", loginUser);
//             chain.doFilter(req, resp);
//         }catch (SignatureVerificationException sve){
//             resp.setStatus(401);
//             response.setContentType("text/plain; charset=utf-8");
//             response.getWriter().println("로그인 다시해1");
//         }catch (TokenExpiredException tee){
//             resp.setStatus(401);
//             response.setContentType("text/plain; charset=utf-8");
//             response.getWriter().println("로그인 다시해2");
//         }
//     }

// }


public class JwtVerifyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String prefixJwt = req.getHeader(JwtProvider.HEADER);
        String jwt = prefixJwt.replace(JwtProvider.TOKEN_PREFIX, "");
        try {
            DecodedJWT decodedJWT = JwtProvider.verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();

            // 내부적으로 권한처리
            HttpSession session =  req.getSession();
            LoginUser loginUser = LoginUser.builder().id(id).role(role).build();
            session.setAttribute("loginUser", loginUser);
            chain.doFilter(req, resp);
        }catch (SignatureVerificationException sve){
            resp.setStatus(401);
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("로그인 다시해1");
        }catch (TokenExpiredException tee){
            resp.setStatus(401);
            resp.setContentType("text/plain; charset=utf-8");
            resp.getWriter().println("로그인 다시해2");
        }
    }

}