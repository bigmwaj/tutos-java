package com.bigmwaj.tuto.constant;

public class SecurityConstants {
	public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
	public static final String JWT_HEADER = "Authorization";
	public static final String VALIDATE_CREDENTIALS_URL = "/validate-credentials";
	public static final String JWT_LIFE_HEADER = "JWTMaxAge";
	public static final long SESSION_TIMEOUT = 30_000_000L;
}
