package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "BQCC0rKyH5fwMNISeeSvhmPiXWORR23HI8W9pJ-lCiQSh4oLuBkjlK556hL-pwe2D6cN_8TKrN7l9ZeXq_etk4Mn_RWdBjllq08NVR-SP-PTuYts4Z0LhF-I78NodVrPa3qCZJtDhOqGpBnhIPA2Bni0DGlHOVJfjb2uMssVr9LGmQdoEbrpmgwPAN5YLvfm1rxamvOl0HUqLK7Tpj-xMpmmoZTcbzW4lqGqDbuDYjd8KSmW_63a2mcD4gizTljErnSYfI2EZzyiqmyqFeD1MlL1asJ0Hg";

    @BeforeClass //from test NG
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization", "Bearer " + access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build(); //--> lekcja 81

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().

                        log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void ShouldBeAbleToCreateAPlaylist() { //lekcja 193 i 198 199
        Playlist requestPlaylist = new Playlist().
            setName("New PlayList").
            setDescription("New playlist description").
            setPublic(false);

        Playlist responsePlaylist = given(requestSpecification).
                body(requestPlaylist).
        when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
        then().spec(responseSpecification).
                assertThat().statusCode(201).
                extract().
                response().
                as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        //assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() { //lekcja 194
        Playlist requestPlaylist = new Playlist().
            setName("Updated Playlist Name").
            setDescription("Updated playlist description").
            setPublic(false);

        Playlist responsePlaylist = given(requestSpecification).
        when().get("/playlists/3m5qfL92Ytzj3q3AGxLth2").
        then().
                spec(responseSpecification).
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

        given(requestSpecification).
                body(requestPlaylist).
        when().put("/playlists/3m5qfL92Ytzj3q3AGxLth2").
        then().spec(responseSpecification).
                assertThat().
                statusCode(200);
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithName() { //lekcja 196, 199
        Playlist requestPlaylist = new Playlist().
            setName("").
            setDescription("Updated playlist description").
            setPublic(false);

        Error error = given(requestSpecification).
                body(requestPlaylist).
        when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
        then().spec(responseSpecification).
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
            setName("Updated Playlist Name").
            setDescription("Updated playlist description").
            setPublic(false);

        Error error = given().
                baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization", "Bearer " + "12345").
                contentType(ContentType.JSON).
                log().all().
                body(requestPlaylist).
                when().post("users/31xxj6sy7e2cornflo4cnsygfsyu/playlists").
                then().spec(responseSpecification).
                assertThat().statusCode(401).
                extract().
                response().
                as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }

}
