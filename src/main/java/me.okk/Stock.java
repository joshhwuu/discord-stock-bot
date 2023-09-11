package me.okk;

import java.lang.reflect.Field;

public class Stock extends yahoofinance.Stock {

    public Stock(String symbol) {
        super(symbol);
    }

    public String printAsString() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        int count = declaredFields.length;
        String returnString = this.getSymbol() +
                "-----------------------------";

        for (int i = 0; i < count; ++i) {
            Field field = declaredFields[i];
            String s;
            try {
                s = field.getName() + ": " + field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            returnString.concat("\n" + s);
        }

        return returnString;
    }
}
