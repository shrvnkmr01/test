package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ApiConfig;
import utils.ApiConfigLoader;
import utils.LoggerUtil;

public class ApiService {
    private final ApiConfig apiConfig;
    private final String environment;

    public ApiService(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.environment = apiConfig.getEnvironment();
    }

    public String generateToken() {
        String url = ApiConfigLoader.getApiUrl("getToken", environment);
        LoggerUtil.info("Resolved URL for 'generateToken': {}" + url);

        Response response = RestAssured.given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", apiConfig.getAuthorization())
                .when()
                .post(url);

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getString("access_token");
        } else {
            throw new RuntimeException("Failed to generate token: " + response.getStatusLine());
        }
    }

    public Response postDigitalAptitudeDetails(String accessToken) {
        String url = ApiConfigLoader.getApiUrl("getDigitalAptitudeDetails", environment);
        String bodyPayload = ApiConfigLoader.getApiPayload("getDigitalAptitudeDetails");

        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(bodyPayload)
                .when()
                .post(url);
    }

    public Response postSendPushToApp(String accessToken) {
        String url = ApiConfigLoader.getApiUrl("sendPushToApp", environment);
        String bodyPayload = ApiConfigLoader.getApiPayload("sendPushToApp");

        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(bodyPayload)
                .when()
                .post(url);
    }
}
