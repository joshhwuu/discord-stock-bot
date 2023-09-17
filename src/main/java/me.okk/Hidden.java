package me.okk;

public class Hidden {
    private static String api_key;
    private static String bot_token;
    private static Hidden hidden;

    private Hidden() {
        api_key = "key";
        bot_token = "token";
    }

    public static String getApi_key() {
        hidden = new Hidden();
        return api_key;
    }

    public static String getBot_token() {
        hidden = new Hidden();
        return bot_token;
    }
}
