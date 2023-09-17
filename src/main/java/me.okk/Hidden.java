package me.okk;

public class Hidden {
    private static String api_key;
    private static String bot_token;
    private static Hidden hidden;

    private Hidden() {
        api_key = "4O40VJGL7N24KQKP";
        bot_token = "MTE1MDE4MjYxNjc3NjA2NTE4NQ.GxbMQg.uUn-V6nvwZTzo6saR-JH17brDsEcylDnk6f0lE";
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
