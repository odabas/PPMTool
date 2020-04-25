package io.odabas.ppmtool.security;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWSTs";
    public static final String TOKEN_PREFIX ="Onur ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final int   EXPRATION_TIME = 300_000; // 30 Seconds
}
