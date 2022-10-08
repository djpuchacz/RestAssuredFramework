package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi { //lesson 202

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists", getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists", token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get("/playlists/" + playlistId, getToken());
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return RestResource.update("/playlists/" + playlistId, getToken(), requestPlaylist);
    }
}
