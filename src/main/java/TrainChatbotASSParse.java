import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.submerge.api.parser.ASSParser;
import com.submerge.api.parser.SRTParser;
import com.submerge.api.parser.exception.InvalidFileException;
import com.submerge.api.parser.exception.InvalidSubException;
import com.submerge.api.subtitle.ass.ASSSub;
import com.submerge.api.subtitle.ass.Events;
import com.submerge.api.subtitle.srt.SRTLine;
import com.submerge.api.subtitle.srt.SRTSub;

import java.io.*;
import java.util.Set;

/**
 * Created by Tommy on 2017/4/19.
 */

public class TrainChatbotASSParse {

    public static void main(String[] args) throws IOException, InvalidSubException, InvalidFileException {

       // BufferedReader br = new BufferedReader(new FileReader("I:\\字幕訓練\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE.繁体.txt"));
        Writer writer = null;
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("I:\\字幕訓練\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE\\fight-season-two.corpus.json"), "utf-8"));
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonArray arrayTwo = new JsonArray();
        JsonObject jsonObject = new JsonObject();
       for(int z=1;z<23;z++) {

           String zString = String.format("%02d", z);
           File file = new File("I:\\字幕訓練\\[YYeTs][Prison.Break][S02E"+zString+"][CN][720P.HDTV][1280X720][H264].ass");
           System.out.println(file.getName());
           ASSParser parser = new ASSParser();
           ASSSub subtitle = parser.parse(file);
           Set<Events> subtitleLineSet = subtitle.getEvents();


           System.out.println(subtitleLineSet.size());
           int i = 0;
           for(Events events:subtitleLineSet){
               for(String text:events.getTextLines()){

                   if(!(text.contains("{\\")||text.contains("[")) && !text.equals("") && !text.startsWith("\\u0")){
                       i++;
                       String textTrain=text.replace("-","").replace("- ","");
                        System.out.println(textTrain);
                        arrayTwo.add(new JsonPrimitive(textTrain));
                   }
                   if (i % 5 == 0) {
                       array.add(arrayTwo);
                       arrayTwo = new JsonArray();
                   }
               }
           }

       }
       // array.add(arrayTwo);
        jsonObject.add("fight-season-two", array);
        writer.write(gson.toJson(jsonObject));
        writer.close();



    }
}
