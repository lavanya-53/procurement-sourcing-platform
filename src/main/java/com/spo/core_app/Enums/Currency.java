package com.spo.core_app.Enums;

public enum Currency {
    INR("Indian Rupee", "₹"),
    USD("US Dollar", "$"),
    EUR("Euro", "€"),
    GBP("British Pound", "£"),
    JPY("Japanese Yen", "¥"),
    AUD("Australian Dollar", "A$"),
    CAD("Canadian Dollar", "C$"),
    CHF("Swiss Franc", "CHF"),
    CNY("Chinese Yuan", "¥"),
    SGD("Singapore Dollar", "S$"),
    AED("UAE Dirham", "د.إ"),
    SAR("Saudi Riyal", "﷼");

    private String DisplayName;
    private String Symbol;
    Currency(String DisplayName,String Symbol){
        this.DisplayName=DisplayName;
        this.Symbol=Symbol;
    }
    public String getDisplayNamee(){
        return DisplayName;
    }
    public String GetSymbol()
    {
        return Symbol;
    }
}
