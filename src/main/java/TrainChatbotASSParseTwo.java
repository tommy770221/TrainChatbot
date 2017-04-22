import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.submerge.api.parser.ASSParser;
import com.submerge.api.parser.exception.InvalidFileException;
import com.submerge.api.parser.exception.InvalidSubException;
import com.submerge.api.subtitle.ass.ASSSub;
import com.submerge.api.subtitle.ass.Events;

import java.io.*;
import java.util.Set;

/**
 * Created by Tommy on 2017/4/19.
 */

public class TrainChatbotASSParseTwo {

    public static void main(String[] args) throws IOException, InvalidSubException, InvalidFileException {

        // BufferedReader br = new BufferedReader(new FileReader("I:\\字幕訓練\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE.繁体.txt"));
        Writer writer = null;
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("I:\\字幕訓練\\star-soccer.corpus.json"), "utf-8"));
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonArray arrayTwo = new JsonArray();
        JsonObject jsonObject = new JsonObject();


        File file = new File("I:\\字幕訓練\\avchd-shaolin.soccer.720p.mkv.chs.ass");
        System.out.println(file.getName());
        ASSParser parser = new ASSParser();
        ASSSub subtitle = parser.parse(file);
        Set<Events> subtitleLineSet = subtitle.getEvents();


        System.out.println(subtitleLineSet.size());
        int i = 0;
        for (Events events : subtitleLineSet) {
            for (String text : events.getTextLines()) {

                if (!(text.contains("{\\") || text.contains("[")) && !text.equals("") && !text.startsWith("\\u0")) {
                    i++;
                    String textTrain = text.replace("-", "").replace("- ", "");
                    System.out.println(textTrain);
                    arrayTwo.add(new JsonPrimitive(textTrain));
                }
                if (i % 5 == 0) {
                    array.add(arrayTwo);
                    arrayTwo = new JsonArray();
                }
            }
        }


        // array.add(arrayTwo);
        jsonObject.add("star-soccer", array);
        writer.write(gson.toJson(jsonObject));
        writer.close();


    }
}
