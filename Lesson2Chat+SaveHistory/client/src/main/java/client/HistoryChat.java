package client;

import javafx.scene.shape.Path;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HistoryChat {
    private static PrintWriter print;
    private static int start = 0;


    public static String saveChat(String login) {
        return "chatHistory/history_" + login + ".txt";
    }

    public static void start(String login) {

        try {
            print = new PrintWriter(new FileOutputStream(saveChat(login),true ),true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (print != null) {
            print.close();
        }
    }

    public static void writerIn(String msg) {
        print.println(msg);
    }

    public static String getLast100Message(String login) {
        if (!Files.exists(Paths.get(saveChat(login)))){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        {
            try {
                List<String> historyChat = Files.readAllLines(Paths.get(saveChat(login)));
                if (historyChat.size() > 100) {
                    start = historyChat.size() - 100;
                }
                for (int i = start; i < historyChat.size(); i++) {

                    sb.append(historyChat.get(i)).append(System.lineSeparator());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
