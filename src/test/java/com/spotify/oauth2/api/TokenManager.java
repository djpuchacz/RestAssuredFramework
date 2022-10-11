package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager { //204, 205
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken() {
        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("renewing token...");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("token is good to use");
            }
        } catch (Exception e) {
            throw new RuntimeException("ABORT!!! Failed to get token");
        }
        return access_token;
    }

    private static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("client_id", "a24b35f64dde4aa5a4457e97dd58654a");
        formParams.put("client_secret", "a1a21de6f83c4866bf84c725f7531175");
        formParams.put("refresh_token", "AQCGgk1uswegPwbANk1e3nnNXO76zV0C6BHqvDYK73cfMk8HGxClprYTQgd83_nzySjO_9xjyhM_2ol7881Kfv10PI1yRWAkgHSo1sk3ma2MctcoH-KIN9dPEOgb-YCR6kU");
        formParams.put("grant_type", "refresh_token");

        Response response = RestResource.postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("ABORT!!! Renew Token failed");
        }
        return response;
    }

}
