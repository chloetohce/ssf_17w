package sg.edu.nus.iss.ssf_17t.model;

public class Currency {
    private String currencyId;
    private String symbol;
    private String name;

    public Currency(String currencyId, String symbol, String name) {
        this.currencyId = currencyId;
        this.symbol = symbol;
        this.name = name;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String currencySymbol) {
        this.symbol = currencySymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
