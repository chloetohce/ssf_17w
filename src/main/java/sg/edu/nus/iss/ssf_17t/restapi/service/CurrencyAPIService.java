package sg.edu.nus.iss.ssf_17t.restapi.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ssf_17t.utilities.Constant;

@Service
public class CurrencyAPIService {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${key-currency}")
    private String apiKey;
    
    public ResponseEntity<String> getAllCountries() {
        String url = UriComponentsBuilder.fromUriString(Constant.URL_COUNTRIES)
            .queryParam("apiKey", apiKey)
            .build()
            .toUriString();
        
        RequestEntity<Void> request = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JsonReader reader = Json.createReader(new StringReader(response.getBody()));
        JsonObject countries = reader.readObject().getJsonObject("results");

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(countries.toString());
    }

    public ResponseEntity<String> getConversionRate(String query) {
        String url = UriComponentsBuilder.fromUriString(Constant.URL_CURR)
            .queryParam("q", query)
            .queryParam("compact", "ultra")
            .queryParam("apiKey", apiKey)
            .toUriString();

        RequestEntity<Void> request = RequestEntity.get(url)
            .build();
        
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        System.out.println(response.getBody());

        JsonReader reader = Json.createReader(new StringReader(response.getBody()));
        double conversionRate = reader.readObject().getJsonNumber(query).doubleValue();

        JsonObject objResponse = Json.createObjectBuilder()
            .add(query, conversionRate)
            .build();

        ResponseEntity<String> result = ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(objResponse.toString());

        return result;

    }
}
