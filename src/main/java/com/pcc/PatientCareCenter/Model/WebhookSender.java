package com.pcc.PatientCareCenter.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcc.PatientCareCenter.Database.Defaults;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class WebhookSender {

    public static int sendMessage(String message) throws Exception {
        String webhookUrl = Defaults.getDefault("WEBHOOK_URL");
        HttpURLConnection conn = getHttpURLConnection(webhookUrl, message);
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode;
    }

    private static HttpURLConnection getHttpURLConnection(String webhookUrl, String message) throws IOException {
        message = String.format("{\"content\": %s}", new ObjectMapper().writeValueAsString(message));
        System.out.println(message);
        URL url = new URL(webhookUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = message.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return conn;
    }

    public static void main(String[] args) {
        String[] msgs;
        try {
            msgs = new String[]{new ObjectMapper().writeValueAsString("Name:	Sunil Fernand\nAge:	46 years\nAdd from our stock:\nAmoxline 2mg	| Frequency: 3	| Days: 3 |\nAmoxicillin 250mg	| Frequency: 3	| Days: 3 |\nLosartan 50mg	| Frequency: 3	| Days: 3 |\nTotal:	Rs. 479.25"),
                    "\"My name is adithya\"", "\"Name:\tSunil Fernand\\nAge:\t46 years\\nAdd from our stock:\\nAmoxline 2mg\t| Frequency: 3\t| Days: 3 |\\nAmoxicillin 250mg\t| Frequency: 3\t| Days: 3 |\\nLosartan 50mg\t| Frequency: 3\t| Days: 3 |\\nTotal:\tRs. 479.25\""
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (String msg : msgs) {
            try {
                System.out.println(sendMessage(msg));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
