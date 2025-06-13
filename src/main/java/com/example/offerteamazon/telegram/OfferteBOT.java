package com.example.offerteamazon.telegram;

import com.example.offerteamazon.model.OffertaAmazon;
import com.example.offerteamazon.repository.OffertaAmazonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Configuration
public class OfferteBOT extends TelegramLongPollingBot {
    @Value("${myChatId}")
    Long MY_CHAT_ID;
    @Autowired
    OffertaAmazonRepository repository;

    String tokenBot;
    OfferteBOT(@Value("${tokenOfferte}")  String tokenBot){
        this.tokenBot=tokenBot;
    }

    private BotSession registerBot;
    private OfferteBOT offerteBOT;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Long chatId = update.getMessage().getChatId();
                String text = update.getMessage().getText();
                if (update.getMessage().hasText()) {
                    if (text.equals("killMe")) {
                        offerteBOT.stopBot();
                    } else if (text.equals("/elenca")) {
                        elencaRicerca(chatId);
                    } else if (text.equals("/proponi")) {
                        execute(creaSendMessage(chatId, "Al momento il sito non salva le ricerche, a breve verrà data la possibilità schedulare le ricerche ed inviarti un messaggio appena verrà trovato un match.", false));
                    } else {
                        execute(creaSendMessage(chatId, text, true));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            //			throw new RuntimeException(e);
        }
    }

    private void elencaRicerca(Long chatId) throws TelegramApiException {
        List<OffertaAmazon> offerte = repository.findAll();
        for (OffertaAmazon offerta : offerte) {
            StringBuilder msg = new StringBuilder();
            msg.append(offerta.getNomeProdotto() + "\n");
            msg.append(offerta.getDescrizione() + "\n");
            msg.append("Prezzo originale: " + offerta.getPrezzoOriginale() + "\n");
            msg.append("Prezzo segnalato: " + offerta.getPrezzoSegnalato() + "\n");
            msg.append(offerta.getUrlAffiliato() + "\n");
            SendPhoto sendPhoto = sendImageToChat(offerta.getUrlImmagine(), msg.toString(), String.valueOf(chatId));
            execute(sendPhoto);
        }
    }

    @Override
    public String getBotUsername() {
        return "FantaCronacaLiveBot";
    }

    @Override
    public String getBotToken() {
        return tokenBot;
    }


    @Bean
    public OfferteBOT inizializza() throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        System.out.println(tokenBot);
        offerteBOT = new OfferteBOT(tokenBot);
        registerBot = telegramBotsApi.registerBot(offerteBOT);
        offerteBOT.inviaMessaggio(MY_CHAT_ID, "AVVIATO");
        return offerteBOT;
    }


    public void inviaMessaggio(long chatId, String msg) throws TelegramApiException {
        try {
            while (msg.length() > 4000) {
                execute(creaSendMessage(chatId, msg.substring(0, 4000)));
                msg = msg.substring(4000);
            }

            execute(creaSendMessage(chatId, msg));

        } catch (TelegramApiException e) {
            throw e;
        }
    }

    private SendMessage creaSendMessage(long chatId, String msg) {
        return creaSendMessage(chatId, msg, false);
    }

    private SendMessage creaSendMessage(long chatId, String msg, boolean bReply) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setParseMode("html");
        sendMessage.setChatId(Long.toString(chatId));
        String messaggio = "";
        String rep = " ";
        if (bReply) {
            for (int i = 0; i < msg.length(); i++) {
                rep = rep + "\\u" + Integer.toHexString(msg.charAt(i)).toUpperCase();
            }
            rep = rep + " ";

            rep = rep + " --> ";
            byte[] bytes = msg.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                rep = rep + bytes[i] + ",";
            }
            messaggio = "<b>sono il bot reply</b> per  " + chatId;
        }
        messaggio = messaggio + "\n" + msg;
        if (bReply) {
            messaggio = messaggio + "\n" + rep;
        }
        sendMessage.setText(messaggio);
        return sendMessage;
    }

    public void startBot() {
        registerBot.start();
    }

    public void stopBot() {
        registerBot.stop();
    }

    public boolean isRunning() {
        return registerBot.isRunning();
    }

    private SendPhoto sendImageToChat(String imageUrl, String msg, String chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(imageUrl));
        sendPhoto.setCaption(msg);
        return sendPhoto;
    }


}
