package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource { //lesson 203

    public static Response post(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + token).
                when().post(path).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String path, String token) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + token).
                when().
                get(path).
                then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response update(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + token).
                body(requestPlaylist).
                when().put(path).
                then().spec(getResponseSpec()).
                extract().
                response();
    }
}
