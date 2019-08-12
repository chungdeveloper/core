package com.core.utility.utils;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by Le Duc Chung on 2017-08-05.
 * on project 'TrackingProject'
 */

public class JWTUtils {

    public static final String SECRET_KEY = "GGS-NTV-API";

    public static JSONObject getJSONFromToken(String token) {
        try {
            return new JSONObject(getStringJsonFromToken(token));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static String getStringJsonFromToken(String token) {
        return getStringJsonFromToken(token, SECRET_KEY);
    }

    public static String getStringJsonFromToken(String token, String secretKey) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes(Constants.UTF_8)).parseClaimsJws(token).getBody();
            return new Gson().toJson(claims);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Constants.EMPTY_STRING;
        }
    }
}
