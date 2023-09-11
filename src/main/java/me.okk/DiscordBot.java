package me.okk;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import yahoofinance.YahooFinance;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.math.BigDecimal;

public class DiscordBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {

//        if (args.length < 1) {
//            System.out.println("You have to provide a token as first argument!");
//            System.exit(1);
//        }
        // Creates JDA object bot, replace "token" with your token.
        JDA bot = JDABuilder.createDefault("MTE1MDE4MjYxNjc3NjA2NTE4NQ.GxbMQg.uUn-V6nvwZTzo6saR-JH17brDsEcylDnk6f0lE")
                .setActivity(Activity.playing("Type /ss + ticker symbol!"))
                .addEventListeners(new DiscordBot())
                .build();

        bot.updateCommands().addCommands(
                Commands.slash("ss", "Search stock")
                        .addOption(OptionType.STRING, "symbol", "Ticker symbol")
        ).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
        // handles all slash-commands given to bot
        switch (e.getName()) {
            case "ss":
                String symbol = e.getOption("symbol", OptionMapping::getAsString);
                Stock stock = YahooFinance.get(symbol);
                BigDecimal price = stock.getQuote().getPrice();
                BigDecimal change = stock.getQuote().getChangeInPercent();
                BigDecimal peg = stock.getStats().getPeg();
                BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

                e.reply(stock.printAsString())
                        .setEphemeral(true).queue();
                break;
        }
    }
}