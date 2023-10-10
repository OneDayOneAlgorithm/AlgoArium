package com.ssafy.algoarium.KakaoLogin;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoOauthToken {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("expires_in")
	private int expiresIn;

	@JsonProperty("refresh_token_expires_in")
	private int refreshTokenExpiresIn;

	@JsonProperty("scope")
	private String scope;

}
