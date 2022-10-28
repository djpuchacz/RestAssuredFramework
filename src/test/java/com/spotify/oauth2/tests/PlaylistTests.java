package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;

import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth 2.0") //lesson 224
@Feature("Playlist API")
public class PlaylistTests extends BaseTest { //lesson 232

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) { //lesson 215 i 216
        return Playlist.builder().
                name(name).
                description(description).
                _public(_public).
                build();
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        //assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));

    }

    @Step //lesson 225
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertError(Error responseErr, StatusCode statusCode) {
        assertThat(responseErr.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(), equalTo(statusCode.msg));
    }

    @Story("create a playlist story")
    @Link("https://example.org") //lekcja 223
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is description") //lekcja 222
    @Test(description = "should be able to create a playlist") //lekcja 221
    public void ShouldBeAbleToCreateAPlaylist() { //lessons 193, 198, 199, 201, 202, 212, 230
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(responsePlaylist, requestPlaylist); //or:
        //assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() { //lekcja 194, 202, 211, 212
        Playlist requestPlaylist = playlistBuilder("New Playlist", "New playlist description", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist() { //lekcja 195, 202, 212
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
    }

    @Story("create a playlist story")
    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithName() { //lekcja 196, 199, 202, 212
        Playlist requestPlaylist = playlistBuilder("",generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400);
        assertError(response.as(Error.class), StatusCode.CODE_400);
    }

    @Story("create a playlist story")
    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() { //lekcja 196, 202
        String invalid_token = "12345";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);
        assertError(response.as(Error.class), StatusCode.CODE_401);
    }
}
