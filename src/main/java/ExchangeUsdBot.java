import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExchangeUsdBot {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("5533986715:AAH6k-FUimhfb8RXQYdpV9WBjc6jqHsuTCc");
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                try {
                    System.out.println(upd);
                    long chatId = upd.message().chat().id();
                    String incomeMessage = upd.message().text();
                    //logic
                    String date = incomeMessage;
                    Document doc = Jsoup.connect("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date).get();
                    Elements valutes = doc.select("Valute");
                    String result = "";
                    for (Element valute : valutes) {
                        if (valute.attr("ID").equals("R01235")) {
                            result = "Курс доллара: " + valute.select("Value").text() + " рублей";
                        }
                    }
                    //send response
                    SendMessage request = new SendMessage(chatId, result);
                    bot.execute(request);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
