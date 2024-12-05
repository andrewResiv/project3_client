import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class MeasurementClient {
    private static final String BASE_URL = "http://localhost:8080"; // Укажите правильный адрес вашего API

    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();

        Integer a = 120;
        Integer b = 120;
        Integer c = 130;
        Integer d = 130;
        System.out.println(a==b);
        System.out.println(c==d);
        // 1. Регистрация нового сенсора
//        String sensorName = "TestSensor";
//        registerSensor(restTemplate, sensorName);
//
//        // 2. Отправка 1000 измерений
//        sendMeasurements(restTemplate, sensorName, 10);
//
//        // 3. Получение всех измерений
//        getMeasurements(restTemplate);
    }



    private static void registerSensor(RestTemplate restTemplate, String sensorName) {
        String url = BASE_URL + "/sensors/registration";
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        String requestBody = String.format("{\"name\":\"%s\"}", sensorName);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Sensor registered successfully");
        }else{
            System.out.println("Sensor registration failed");
        }
    }
    private static void sendMeasurements(RestTemplate restTemplate, String sensorName, int count) {
        String url = BASE_URL + "/measurements/add";
        Random random = new Random();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        for (int i = 0; i < count; i++) {
            double value = random.nextDouble();
            boolean raining = random.nextBoolean();

            String requestBody = String.format("{\"value\": %.2f, \"raining\": %s, \"sensor\": \"name\": \"%s\"}",
                    value, raining, sensorName);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            try {
                restTemplate.postForEntity(url, request, String.class);
                System.out.println("Measurement " + (i + 1) + " sent.");
            } catch (Exception e) {
                System.out.println("Error sending measurement " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    private static void getMeasurements(RestTemplate restTemplate) {
        String url = BASE_URL + "/measurements";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Measurements received: ");
                System.out.println(response.getBody());
            } else {
                System.out.println("Failed to get measurements: " + response.getBody());
            }
        } catch (Exception e) {
            System.out.println("Error fetching measurements: " + e.getMessage());
        }
    }

}

