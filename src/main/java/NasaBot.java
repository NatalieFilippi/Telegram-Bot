import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendVideo;
import org.jsoup.Jsoup;

public class NasaBot {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("5533986715:AAH6k-FUimhfb8RXQYdpV9WBjc6jqHsuTCc");
        //211433463
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String incomeMessage = upd.message().text(); //dd.MM.yyyy
                    //logic
                    String date = incomeMessage;
                    String jsonString = Jsoup.connect("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=" + date)
                            .ignoreContentType(true)
                            .execute()
                            .body();
                    ObjectMapper objectMapper = new ObjectMapper();
                    var jsonNode = objectMapper.readTree(jsonString);
                    String imageUrl = jsonNode.get("url").asText();
                    String explanation = jsonNode.get("explanation").asText();
                    String result = imageUrl + "\n" + explanation;
                    //send response
                    SendVideo request = new SendVideo(chatId, result);
                    bot.execute(request);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}