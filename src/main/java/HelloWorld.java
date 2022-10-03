import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class HelloWorld {

    private static final TelegramBot bot = new TelegramBot("5533986715:AAH6k-FUimhfb8RXQYdpV9WBjc6jqHsuTCc");

    public static void main(String[] args) {
        bot.setUpdatesListener(updates -> {
            updates.forEach(upd -> {
                System.out.println(upd);
                long chatId = upd.message().chat().id();
                try {
                    int number = Integer.parseInt(upd.message().text());
                    String result = menu(number, upd);
                    bot.execute(new SendMessage(chatId, result));
                } catch (Exception e) {
                    System.out.println("Неверный параметр");
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private static String menu(int number, Update upd) {
        switch (number) {
            case 1:
                return "Я могу ответить!";
            case 2:
                return "Твоё имя " + upd.message().from().firstName();
            case 3:
                return "Твой ник " + upd.message().from().username();
            default:
                return "Неверный параметр";
        }
    }
}
