package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ApiConfigLoader {
    private static final JsonNode urlConfig;
    private static final JsonNode payloadConfig;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream isUrls = ApiConfigLoader.class.getClassLoader().getResourceAsStream("apiUrls.json");
             InputStream isPayloads = ApiConfigLoader.class.getClassLoader().getResourceAsStream("apiPayLoads.json")) {

            if (isUrls == null) throw new RuntimeException("apiUrls.json file not found in resources.");
            if (isPayloads == null) throw new RuntimeException("apiPayloads.json file not found in resources.");

            urlConfig = mapper.readTree(isUrls);
            payloadConfig = mapper.readTree(isPayloads);

            LoggerUtil.info("Loaded API URLs: {}" + urlConfig.toString());
            LoggerUtil.info("Loaded API Payloads: {}" + payloadConfig.toString());
        } catch (IOException e) {
            LoggerUtil.error("Failed to load API configuration.", e);
            throw new RuntimeException("Failed to load API configuration.", e);
        }
    }

    public static String getApiUrl(String urlKey, String environment) {
        if (!urlConfig.has(urlKey)) {
            throw new RuntimeException("URL for '" + urlKey + "' not found in apiUrls.json.");
        }
        String url = urlConfig.get(urlKey).asText();
        return url.replace("${environment}", environment);
    }

    public static String getApiPayload(String payloadKey) {
        if (!payloadConfig.has(payloadKey)) {
            throw new RuntimeException("Payload for '" + payloadKey + "' not found in apiPayloads.json.");
        }
        return payloadConfig.get(payloadKey).toString();
    }
}
