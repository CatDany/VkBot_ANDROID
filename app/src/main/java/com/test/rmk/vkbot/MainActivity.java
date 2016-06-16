package com.test.rmk.vkbot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "5479273";
    private static Logger log = Logger.getLogger(MainActivity.class.getName());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread botThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VKAPI vkapi = new VKAPI("5479273",
                            "71ea34dcb6efef47bf87ce3a681becd7efc38af42ac00d8ecfce490b7226e209bfd2354b663a319fdddd3");
                    //VKAPI.auth(APP_ID);
                    String groupID = "2000000001";
                    Bot bot = new Bot();
                    BotActions botActions = new BotActions();
                    StringBuilder groupMessage = new StringBuilder(); //TODO: Отдельным потоком
                    StringBuilder rawMessage = new StringBuilder();
                    TextView textView = (TextView) findViewById(R.id.textView);
                    while (true) {
                        rawMessage.append(vkapi.getHistory(groupID, 0, 1, false));
                        System.out.println(rawMessage.toString());
                        groupMessage.append(botActions.getLastHistoryMessage(rawMessage.toString()));
                        textView.append("Nice roflite!");
                        textView.setText("kek");
                        log.log(Level.INFO, "Got messages rawMessage and groupMessage: " + rawMessage + " " + groupMessage);
                        if (groupMessage.toString().equals("Годноту")) {
                            StringBuilder concatMaster = new StringBuilder();
                            concatMaster.append("photo-73598440_");
                            concatMaster.append(botActions.getPhotoMemes((vkapi.getPhotos("-73598440", "216920632", 500)), 500)); //TODO УСКОРИТЬ РАНДОМ (SECURERANDOM JAVA)
                            String finalString = concatMaster.toString();
                            vkapi.sendMessage("group", "1", "Держи, дружище!", finalString);
                        }
                        if (botActions.equalsCitatku(groupMessage.toString())) {
                            vkapi.sendMessage("group", "1", "", "wall-119328367_" + botActions.getWallNumber(vkapi.getWall("-119328367", "100")));
                        }
                        if (botActions.equalsROK(groupMessage.toString())) {
                            vkapi.sendMessageWithSticker("group", "1", "", "2050");
                        }
                        if (groupMessage.toString().equals("!песня")) {
                            String[] songArray = {"Ленинград - Мамба", "Без Билета – Мечтатели", "JetBoss – That's not my name", "Мумий тролль – Забавы"
                                    , "Awoltalk – Wylin Out", "Muzzy – The Factory", "Conro – City Lights", "Hush – Vonk", "Ozzy Osbourne – Dreamer", "Jetta – I'd Love to Change the World",
                                    "Halsey – Castle", "Imany feat. Filatov – Don't be so shy", "Queen – Friends will be Friends", "AC\\DC - TNT", "Michael Jackson - Billie Jean,",
                                    "Varien - Lilith", "Varien - Valkyrie", "System Of A Down – Lonely Day"};
                            int randomNumber = (int) (Math.random() * songArray.length - 1);
                            String[] wordsArray = {"Грустненькая", "Люблю её", "еее Роцк", "В наушниках самое то", "Т о п 4 и к", "Для души", "Спешл фор ю",
                                    "Для седбойчиков"};
                            int randomNumber2 = (int) (Math.random() * wordsArray.length - 1);
                            vkapi.sendMessage("group", "1", wordsArray[randomNumber2] + ": " + songArray[randomNumber]);
                        }
                        if (groupMessage.toString().contains("!ответь")) {
                            String[] wordArray = {"да", "нет", "маловероятно", "скорее всего", "сложновато", "хех мда", "я хз вообще, братан"};
                            int RandomNumber = (int) (Math.random() * 7);
                            vkapi.sendMessage("group", "1", wordArray[RandomNumber]);
                        }
                        if (groupMessage.toString().equals("Пореверсим")) {
                            log.log(Level.INFO, "Entering Reverse mode!");
                            vkapi.sendMessage("group", "1", "Reverse mode!");
                            while (true) {
                                groupMessage.append(botActions.getLastHistoryMessage(vkapi.getHistory(groupID, 0, 1, false)));
                                StringBuilder reverse = new StringBuilder();
                                reverse.append(groupMessage);
                                if (groupMessage.toString().equals("сревеР")) {
                                    log.log(Level.INFO, "Exiting reverse mode.");
                                    vkapi.sendMessage("group", "1", "Выхожу из режима реверсов(нет)(да)");
                                    break;
                                }
                                vkapi.sendMessage("group", "1", reverse.reverse().toString());
                                groupMessage.delete(0, groupMessage.length());
                                reverse.delete(0, reverse.length());
                                Thread.sleep(5000);
                            }
                        }
                        if (groupMessage.toString().equals("!слоник")) {
                            String[] memesArray = {"Ты понимаешь, что ты поехавший? Уже всё. Не я, блядь, поехавший… не он, блядь, а ты!",
                                    "Не, я вроде ебался  один раз, заебись было!", "Меня твои истории просто доебали уже, я уже не могу их слушать, блядь! Одна история охуительней другой просто! Про говно, блядь, про какую-то хуйню, молофью... Чё ты несешь-то вообще? Ты можешь заткнуться? «Шишка, блядь, встанет — возбудимся». Чего, блядь? Про что несешь? Вообще охуеть.", "НАЧАЛЬНИК, БЛЯДЬ, ЭТОТ ПИДАРАС ОБОСРАЛСЯ! ИДИТЕ МОЙТЕ ЕГО НАХУЙ, Я С НИМ ЗДЕСЬ СИДЕТЬ НЕ БУДУ!",
                                    "ХУЛИ ВЫ МЕНЯ С СУМАСШЕДШИМ ПОСЕЛИЛИ, БЛЯДЬ, ОН ЖЕ МУДАК ПОЛНЫЙ, БЛЯДЬ!!!", "Сколько сейчас времени-то, не знаешь? Так, примерно, можешь почувствовать?", "Ну… Три семёрки выпил, блядь, ну, бутылку, с одной дурой. Ну, а потом поебалися.",
                                    "Говно... Ну так, знаешь... Как земля.", "Ну чё ты такой сердитый человек-то, ну будьте людьми вы, й… ребята, я всегда вам говорю. Чего вы сразу начинаете?",
                                    "Хочешь я на одной ноге постою, а ты мне погону отдашь? Как цапля, хочешь?", "Покушать принёс чуть-чуть еды, это сладкий хлеб!..",
                                    "— Как п… как поспал, братишка? Проголодался, наверное! Братишка…\n" +
                                            "— Ёб твою мать, блядь, иди отсюда нахуй, блядь!\n" +
                                            "— Что, что случилося-то?\n" +
                                            "— Ты что, обосрался что ли, мудак, блядь?!\n" +
                                            "— Не, я не какал. Я тебе покушать принёс!", "ЭТО ЗНАТЬ НАДО! ЭТО КЛАССИКА, БЛЯДЬ!"};
                            String randomize = VKAPI.invokeApi("https://www.random.org/integers/?num=1&min=1&max=" + memesArray.length + "&col=1&base=10&format=plain&rnd=new");
                            vkapi.sendMessage("group", "1", memesArray[Integer.parseInt(randomize) - 1]);
                        }
                        if (groupMessage.toString().equals("!игру")) {
                            String[] gamesArray = {
                                    "Tom Clancy's The Division\n https://ru.wikipedia.org/wiki/Tom_Clancy%E2%80%99s_The_Division",
                                    "Uncharted 4\n https://ru.wikipedia.org/wiki/Uncharted_4:_A_Thief%E2%80%99s_End",
                                    "MAFIA III\n https://ru.wikipedia.org/wiki/Mafia_III",
                                    "Mirror’s Edge: Catalyst\n https://ru.wikipedia.org/wiki/Mirror%E2%80%99s_Edge:_Catalyst",
                                    "Homefront: The Revolution\n https://ru.wikipedia.org/wiki/Homefront:_The_Revolution",
                                    "Doom 4\n https://ru.wikipedia.org/wiki/Doom_(%D0%B8%D0%B3%D1%80%D0%B0,_2016)",
                                    "Far Cry Primal\n https://ru.wikipedia.org/wiki/Far_Cry_Primal",
                                    "Dishonored 2\n https://ru.wikipedia.org/wiki/Dishonored_2",
                                    "XCOM 2\n https://ru.wikipedia.org/wiki/XCOM_2",
                                    "Deus Ex: Mankind Divided\n https://ru.wikipedia.org/wiki/Deus_Ex:_Mankind_Divided",
                                    "Dark Souls III\n https://ru.wikipedia.org/wiki/Dark_Souls_III",
                                    "Overwatch\n https://ru.wikipedia.org/wiki/Overwatch",
                                    "Hearthstone\n https://ru.wikipedia.org/wiki/Hearthstone:_Heroes_of_Warcraft",
                                    "No Man's Sky\n https://ru.wikipedia.org/wiki/No_Man%E2%80%99s_Sky"};
                            String randomize = VKAPI.invokeApi("https://www.random.org/integers/?num=1&min=1&max="
                                    + gamesArray.length + "&col=1&base=10&format=plain&rnd=new");
                            vkapi.sendMessage("group", "1", gamesArray[Integer.parseInt(randomize) - 1]);
                        }
                        if (botActions.isLastMessageMem(groupMessage.toString())) {
                            vkapi.sendMessage("group", "1", "10 мертвых пол кокеров из 10!");
                            log.log(Level.INFO, "LastMessageMem called. Nice meme! sent");
                        }
                        if (botActions.isLastMessageKM(groupMessage.toString())) {
                            if (Math.random() < 0.5) {
                                vkapi.sendMessage("group", "1", "Орёл!");
                            } else {
                                vkapi.sendMessage("group", "1", "Решка!");
                            }
                            log.log(Level.INFO, "LastMessageKM called.");
                        }
                        if (botActions.whoIsUser(rawMessage.toString(), vkapi, "last_name", "Бушуев")) {
                            vkapi.sendMessage("group", "1", "Грузинам слова не давали!");
                            log.log(Level.INFO, "WhoisUser(Gruzin) called.");
                        }
                        if (botActions.isLastMessageSMILEK(groupMessage.toString())) {
                            if (bot.whoIsUser(rawMessage.toString(), vkapi, "id", "328729931") && botActions.isLastMessageSMILEK(groupMessage.toString())) {
                                rawMessage.delete(0, rawMessage.length());
                                groupMessage.delete(0, groupMessage.length());
                                Thread.sleep(300);
                                continue;
                            } else vkapi.sendMessage("group", "1", "&#128515;");
                            log.log(Level.INFO, "LastMessageSMILEK called.");
                        }
                        rawMessage.delete(0, rawMessage.length());
                        groupMessage.delete(0, groupMessage.length());
                        Thread.sleep(300);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            });
            botThread.start();
        }
}
