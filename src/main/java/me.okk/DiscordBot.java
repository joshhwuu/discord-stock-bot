package me.okk;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        // Creates JDA object bot, replace "token" with your token.
        Config cfg = Config.builder()
                .key(Tokens.getApi_key())
                .timeOut(10)
                .build();

        AlphaVantage.api().init(cfg);

        JDA bot = JDABuilder.createDefault(Tokens.getBot_token())
                .setActivity(Activity.playing("Type /ss + ticker symbol!"))
                .addEventListeners(new DiscordBot())
                .build();

        setCommands(bot);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        // handles all slash-commands given to bot
        switch (e.getName()) {
            case "ss":
                String symbol = e.getOption("symbol", OptionMapping::getAsString);
                TimeSeriesResponse response = AlphaVantage.api()
                        .timeSeries()
                        .intraday()
                        .interval(Interval.SIXTY_MIN)
                        .forSymbol(symbol)
                        .outputSize(OutputSize.COMPACT)
                        .fetchSync();
                String temp = response.toString();
                String retString;
                try {
                    retString = formatString(temp, symbol);
                } catch (StringIndexOutOfBoundsException event) {
                    retString = "Oops! We couldn't find that stock!";
                }
                e.reply(retString).queue();
                break;
            case "ssr":
                String realTimeSymbol = e.getOption("symbol", OptionMapping::getAsString);
                TimeSeriesResponse realTimeResponse = AlphaVantage.api()
                        .timeSeries()
                        .intraday()
                        .interval(Interval.ONE_MIN)
                        .forSymbol(realTimeSymbol)
                        .outputSize(OutputSize.COMPACT)
                        .fetchSync();
                String rttemp = realTimeResponse.toString();
                String rttRetString;
                try {
                    rttRetString = formatRealTime(rttemp, realTimeSymbol);
                } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    rttRetString = "Oops! We couldn't find that stock!";
                }
                e.reply(rttRetString).queue();
                break;
        }
    }

    private static void setCommands(JDA bot) {
        bot.updateCommands().addCommands(
                Commands.slash("ss", "Search Stock")
                        .addOption(OptionType.STRING, "symbol", "Ticker symbol"),
                Commands.slash("ssr", "Search Stock real-time")
                        .addOption(OptionType.STRING, "symbol", "Ticker symbol"),
                Commands.slash("fx", "Search Forex")
                        .addOption(OptionType.STRING, "currency", "Currency"),
                Commands.slash("ex", "Exchange Rate")
                        .addOption(OptionType.STRING, "currency", "Currency"),
                Commands.slash("cr", "Crypto")
                        .addOption(OptionType.STRING, "name", "Name")
        ).queue();
    }

    // Formats given string into symbol name, symbol open, high, low, and close as well as time of request
    private String formatString(String out, String symbol) throws StringIndexOutOfBoundsException {
        java.util.Date date = new java.util.Date();
        String dateStr = String.valueOf(date);
        String retString = symbol.toUpperCase() + " - " + dateStr + "\n" + "------------------------------------";
        String open = "Open: " + out.substring(out.indexOf("open=")+5, out.indexOf("high=")-2);
        String high = "High: " + out.substring(out.indexOf("high=")+5, out.indexOf("low=")-2);
        String low = "Low: " + out.substring(out.indexOf("low=")+4, out.indexOf("close=")-2);
        String close = "Close: " + out.substring(out.indexOf("close=")+6, out.indexOf("adjustedClose=")-2);
        return retString + "\n"
                + open + "\n"
                + high + "\n"
                + low + "\n"
                + close;
    }

    // Formats given string into symbol name and symbol price, as well as time of request
    private String formatRealTime(String out, String symbol) throws StringIndexOutOfBoundsException {
        java.util.Date date = new java.util.Date();
        String dateStr = String.valueOf(date);
        String retString = symbol.toUpperCase() + " - " + dateStr + "\n" + "------------------------------------";
        String price = "Price: " + out.substring(out.indexOf("high=")+5, out.indexOf("low=")-2);
        return retString + "\n"
                + price;
    }
}