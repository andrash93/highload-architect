package ru.otus.architect.config.security;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.service.JWTService;

import javax.servlet.http.HttpServletRequest;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JWTService jwtService;

    public AuthArgumentResolver(JWTService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Auth auth = new Auth();
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        if (nativeWebRequest.getUserPrincipal() != null) {
            auth.setLogin(nativeWebRequest.getUserPrincipal().getName());
        }
        if (request.getHeader("Authorization") != null) {
            auth.setUserId( jwtService.extractId((request.getHeader("Authorization")).substring(7)));
        }
        return auth;
    }
}
