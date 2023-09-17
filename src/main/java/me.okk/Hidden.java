package me.okk;

public class Hidden {
    private static String api_key;
    private static String bot_token;
    private static Hidden hidden;

    private Hidden() {
        api_key = System.getenv("api_key");
        bot_token = System.getenv("bot_token");
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
