import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NewsBot {
    public static void main(String[] args) {

        TelegramBot bot = new TelegramBot("5533986715:AAH6k-FUimhfb8RXQYdpV9WBjc6jqHsuTCc");

        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();

                    try {
                        int number = Integer.parseInt(upd.message().text());
                        int index = number - 1;
                        Document doc = Jsoup.connect("https://lenta.ru/rss").get();
                        Element news = doc.select("item").get(index);
                        String category = news.select("category").text();
                        String title = news.select("title").text();
                        String link = news.select("link").text();
                        String description = news.select("description").text();

                        String result = category + "\n" + title + "\n" + description + "\n" + link + "\n";
                        bot.execute(new SendMessage(chatId, result));
                    } catch (Exception e) {
                        System.out.println("Неверный параметр");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
