package vn.ptit.qldaserver.util;

public final class Constants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 86400000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "api/register";
    public static final String DELETE_TOKEN = "api/deleteToken";
    public static final String LOGIN_URL = "api/login";
}