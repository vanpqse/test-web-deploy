package com.example.user_web_service.helper;





import com.auth0.jwt.algorithms.Algorithm;
import com.example.user_web_service.config.ResourceConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getCurrentDateTime() {
		return new Date();
	}

	public static final Integer ACTIVE_STATUS = 1;
	public static final Integer PENDING_STATUS = 0;
	public static final Integer INACTIVE_STATUS = -1;


	public static final String SERVER_PUBLIC_FOLDER_LINK = "http://localhost:" + ResourceConfig.serverPort + "/public/";

	public static final String USER_ROLE = "USER";
	public static final String ADMIN_ROLE = "ADMIN";

	public static Algorithm ENCODE_ALGORITHM = Algorithm.HMAC256("game_rpg".getBytes());
	public static Integer ACCESS_TIME_EXPIRED = 30 * 1000 * 60;

	public static String GOOGLE_CLIENT_ID = "232049583848-lgpfr9t4gadm8m6mgmu4jpu160ld6khv.apps.googleusercontent.com";

	public static String GOOGLE_CLIENT_SECRET = "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";

	public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/dental-demo-2/LoginGoogleController";

	public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

	public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

	public static String GOOGLE_GRANT_TYPE = "authorization_code";
}