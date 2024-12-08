package sg.edu.nus.iss.ssf_17t.restapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.ssf_17t.restapi.service.CurrencyAPIService;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/currency")
public class CurrencyAPIController {
    @Autowired
    private CurrencyAPIService currencyAPIService;
    
    @GetMapping("/countries")
    public ResponseEntity<String> countries() {
        return currencyAPIService.getAllCountries();
    }

    @GetMapping("")
    public ResponseEntity<String> convert(@RequestParam("q") String query, @RequestParam("amt") String amt) {
        double amount = Double.parseDouble(amt);
        ResponseEntity<String> response = currencyAPIService.getConversionRate(query);
        double conversionRate = Json.createReader(new StringReader(response.getBody()))
            .readObject()
            .getJsonNumber(query)
            .doubleValue();

        JsonObject currency = Json.createObjectBuilder()
            .add("result", amount * conversionRate)
            .build();

        ResponseEntity<String> result = ResponseEntity.ok()
            .body(currency.toString());
        return result;
    }
    
    
}
