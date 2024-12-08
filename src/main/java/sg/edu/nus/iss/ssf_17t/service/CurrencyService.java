package sg.edu.nus.iss.ssf_17t.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.ssf_17t.model.Currency;

@Service
public class CurrencyService {
    private RestTemplate restTemplate = new RestTemplate();
    
    public List<Currency> getAllCountries() {
        RequestEntity<Void> request = RequestEntity.get("http://localhost:8080/api/currency/countries")
            .accept(MediaType.APPLICATION_JSON)
            .build();

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        
        JsonObject countries = Json.createReader(new StringReader(response.getBody())).readObject();
        Set<Entry<String, JsonValue>> setCountries = countries.entrySet();
        List<Currency> listCountries = new ArrayList<>();
        
        for (Entry<String, JsonValue> e : setCountries) {
            JsonObject c = e.getValue().asJsonObject();
            listCountries.add(new Currency(
                c.getString("currencyId"),
                c.getString("currencySymbol"),
                c.getString("currencyName")
            ));
        }

        return listCountries;
    }

    public double convert(String query, String amt) {
        String uri = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(8080)
            .pathSegment("api")
            .pathSegment("currency")
            .queryParam("q", query)
            .queryParam("amt", amt)
            .toUriString();

        System.out.println(uri);

        RequestEntity<Void> request = RequestEntity.get(uri)
            .build();
        
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        double result = Json.createReader(new StringReader(response.getBody()))
            .readObject()
            .getJsonNumber("result")
            .doubleValue();
        return result;
    }
}
