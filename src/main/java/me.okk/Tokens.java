package me.okk;

public class Tokens {
    private static String api_key;
    private static String bot_token;
    private static Tokens hidden;

    private Tokens() {
        api_key = System.getenv("api_key");
        bot_token = System.getenv("bot_token");
    }

    public static String getApi_key() {
        hidden = new Tokens();
        return api_key;
    }

    public static String getBot_token() {
        hidden = new Tokens();
        return bot_token;
    }
}
