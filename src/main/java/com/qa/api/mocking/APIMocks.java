package com.qa.api.mocking;

import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class APIMocks {

    //create a mock
    public static void defineGetUserMock(){
        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "_id": 1,
                                    "name": "ash",
                                    "age": 30,
                                    "salary":15.1
                                }
                                """)
                )
        );
    }

    public static void defineGetUserMockWithJSON(){
        stubFor(get(urlEqualTo("/api/users"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("server-name", "bank-server")
                        .withBodyFile("mockuser.json")
                )
        );
    }

    public static void defineGetUserMockWithQueryParam(){
        stubFor(get(urlPathEqualTo("/api/users"))
                .withQueryParam("name", equalTo("ash"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("server-name", "bank-server")
                        .withBodyFile("mockuser.json")
                )
        );
    }

    public static void defineCreateUserMock(){
        stubFor(post(urlEqualTo("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("server-name", "bank-server")
                        .withBody("""
                                {
                                    "_id": 1,
                                    "name": "ash",
                                    "age": 30,
                                    "salary":15.1
                                }
                                """
                        )
                )
        );
    }
}
