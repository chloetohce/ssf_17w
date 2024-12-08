package sg.edu.nus.iss.ssf_17t.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.ssf_17t.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("")
    public ModelAndView getMethodName() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("countries", currencyService.getAllCountries());

        return mav;
    }

    @PostMapping("/convert")
    public ModelAndView convertCurrency(@RequestParam MultiValueMap<String, String> map) {
        ModelAndView mav = new ModelAndView();
        String from = map.getFirst("from");
        String to = map.getFirst("to");
        long amt = Long.parseLong(map.getFirst("amount"));
        
        mav.setViewName("redirect:/convert?q=%s_%s&amt=%d".formatted(from,to,amt));

        return mav;
    }

    @GetMapping("/convert")
    public ModelAndView convert(@RequestParam("q") String param, @RequestParam("amt") String amount) {
        ModelAndView mav = new ModelAndView("exchange");
        double result = currencyService.convert(param, amount);
        String from = param.split("_")[0];
        String to = param.split("_")[1];

        mav.addObject("from", from)
            .addObject("to", to)
            .addObject("amt", amount)
            .addObject("result", result);
        return mav;
    }
    
    
    
}
