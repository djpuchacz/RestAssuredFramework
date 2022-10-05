package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    @Test
    public void ShouldBeAbleToCreateAPlaylist() { //lekcja 193, 198, 199, 201, 202
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New playlist description").
                setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));

        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() { //lekcja 194, 202
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New playlist description").
                setPublic(false);

        Response response = PlaylistApi.get("3m5qfL92Ytzj3q3AGxLth2");
        assertThat(response.statusCode(), equalTo(200));

        Playlist responsePlaylist = response.as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist() { //lekcja 195, 202
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New playlist description").
                setPublic(false);

        Response response = PlaylistApi.update("1wdZvwLxNRb2hab8V9Jpm9", requestPlaylist);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithName() { //lekcja 196, 199, 202
        Playlist requestPlaylist = new Playlist().
                setName("").
                setDescription("Updated playlist description").
                setPublic(false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));

        Error error = response.as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() { //lekcja 196, 202
        String invalid_token = "12345";
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New playlist description").
                setPublic(false);

        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertThat(response.statusCode(), equalTo(401));

        Error error = response.as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }

}
