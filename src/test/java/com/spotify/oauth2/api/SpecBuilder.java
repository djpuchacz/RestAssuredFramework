package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder { //lekcja 201
    static String access_token = "BQDsFCunyoxMjUn8kGAw1sUV0qv7c6DBY3QZJuYpFvzwRIGF4MZYfDq66phLh0q7Rl0PfKgQbBG7Kjw-ZB7W8vdq4QCtdvo0jtkiSb-l1xEvGsSFA5noFm610hRzdMEYFsFUasgTNLbUzYEYoUfHNtH8mVL-DNSgDVlhT5d0k-9bA_dE8_nVp6Gki5f7Yc0qUJquiX1-mnzaaAPqlvnud7t-LU2ec6NxyPOJWcXm-XUgJno3ig5t8dOsJvpQKskg_RBtrvCAeLqdGotccTkzts0_ViOwJg";
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization", "Bearer " + access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL).
                build();        //--> lekcja 81
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }
}
