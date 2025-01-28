package tests;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.ApiConfig;
import utils.LoggerUtil;
import services.ApiService;
import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class ApiTest {
    private ApiService apiService;
    private String accessToken;

    @BeforeClass
    public void setUp() throws IOException {
        ApiConfig apiConfig = new ApiConfig();
        apiService = new ApiService(apiConfig);
        accessToken = apiService.generateToken();
    }

    @Test
    public void testDigitalAptitudeDetails() {
        Response response = apiService.postDigitalAptitudeDetails(accessToken);
        assertEquals(200, response.getStatusCode());
        LoggerUtil.info("Response: " + response.asString());
    }

    @Test
    public void testSendPushToApp() {
        Response response = apiService.postSendPushToApp(accessToken);
        assertEquals(200, response.getStatusCode());
        LoggerUtil.info("Response: " + response.asString());
    }
}
