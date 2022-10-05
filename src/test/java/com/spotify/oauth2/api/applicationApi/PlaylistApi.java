package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class PlaylistApi { //lesson 202
    static String access_token = "BQDboYdt_I5z85PFXi9cPc03dXThg9A1hjWBbtIcNrs0K1uFu6Z6n7H7JZQgOFYd7XAClS-nWhJvnUWmHkoCtvstJdxEJ8YjqCDzBmaNS4UnLLxp5NtapSPq8dvZCo3Vx_N_moOF5h1M2nsK1M1dTwnZmJK7NvFVD7a4YWNnfQW-iw4mMwS-RQu9P0RSpVnYkBXfZggwbQyElXIatwR9YuDSYnJ8zpIXgMYQz0s7LTYM08FgEdMFpQg_jCq72SU7Feed0YuzbVwQnvOKIDaSRpX5iQjgbA";
    public static Response post(Playlist requestPlaylist) {
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + access_token).
                when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response post(String token, Playlist requestPlaylist) {
        return given(getRequestSpec()).
                body(requestPlaylist).
                header("Authorization", "Bearer " + token).
                when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
                then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String playlistId) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + access_token).
                when().
                get("/playlists/" + playlistId).
                then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + access_token).
                body(requestPlaylist).
                when().put("/playlists/" + playlistId).
                then().spec(getResponseSpec()).
                extract().
                response();
    }
}
