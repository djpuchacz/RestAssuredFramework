package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

public class PlaylistApi { //lesson 202
    static String access_token = "BQBFOjk2bgX6_k1bg_AmxwnVhEyFWrA09BqCFO33w9KQBPVBQM9hyFXU0mocERSy4--YQH2NXoe3MfF5CLhzCveh-Pm42U_LKT3jNkQMgG0X1TWJtXGJZsQ0G2BrdnTueXroAxMoNbHBB6c1UIZDLvlPIC5MHMefflHvvFGW3u-_Md7o3UiI_dzxKyhWi6A3jr-nquPuMqN9h1U6o4niMWAyhRizUy8i4MAAWqCRkwndNEdsO_GT875SZpQwYrW5AxlS7Dgv0ZFwxaYBDp0WL-TxsGUAhw";

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists", access_token, requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist) {
        return RestResource.post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists", token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get("/playlists/" + playlistId, access_token);
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return RestResource.update("/playlists/" + playlistId, access_token, requestPlaylist);
    }
}
