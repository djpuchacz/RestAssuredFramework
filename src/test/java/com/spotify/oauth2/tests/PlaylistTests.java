package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    @Test
    public void ShouldBeAbleToCreateAPlaylist() { //lekcja 193, 198, 199, 201
        Playlist requestPlaylist = new Playlist().
            setName("New Playlist").
            setDescription("New playlist description").
            setPublic(false);

        Playlist responsePlaylist = given(getRequestSpec()).
                body(requestPlaylist).
        when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
        then().spec(getResponseSpec()).
                assertThat().statusCode(201).
                extract().
                response().
                as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        //assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() { //lekcja 194
        Playlist requestPlaylist = new Playlist().
            setName("New Playlist").
            setDescription("New playlist description").
            setPublic(false);

        Playlist responsePlaylist = given(getRequestSpec()).
        when().get("/playlists/3m5qfL92Ytzj3q3AGxLth2").
        then().
                spec(getResponseSpec()).
                assertThat().
                statusCode(200).
                extract().
                response().
                as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist() { //lekcja 195
        Playlist requestPlaylist = new Playlist().
            setName("New Playlist").
            setDescription("New playlist description").
            setPublic(false);

        given(getRequestSpec()).
                body(requestPlaylist).
        when().put("/playlists/1wdZvwLxNRb2hab8V9Jpm9").
        then().spec(getResponseSpec()).
                assertThat().
                statusCode(200);
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithName() { //lekcja 196, 199
        Playlist requestPlaylist = new Playlist().
            setName("").
            setDescription("Updated playlist description").
            setPublic(false);

        Error error = given(getRequestSpec()).
                body(requestPlaylist).
        when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
        then().spec(getResponseSpec()).
                assertThat().statusCode(400).
                extract().
                response().
                as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() { //lekcja 196
        Playlist requestPlaylist = new Playlist().
            setName("New Playlist").
            setDescription("New playlist description").
            setPublic(false);

        Error error = given().
                baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization", "Bearer " + "12345").
                contentType(ContentType.JSON).
                log().all().
                body(requestPlaylist).
                when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
                then().spec(getResponseSpec()).
                assertThat().statusCode(401).
                extract().
                response().
                as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }

}
