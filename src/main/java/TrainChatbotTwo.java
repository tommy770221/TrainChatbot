import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.submerge.api.parser.SRTParser;
import com.submerge.api.parser.exception.InvalidFileException;
import com.submerge.api.parser.exception.InvalidSubException;
import com.submerge.api.subtitle.srt.SRTLine;
import com.submerge.api.subtitle.srt.SRTSub;

import java.io.*;
import java.util.Set;

/**
 * Created by Tommy on 2017/4/19.
 */

public class TrainChatbotTwo {

    public static void main(String[] args) throws IOException, InvalidSubException, InvalidFileException {

        Writer writer = null;
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("I:\\字幕訓練\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE\\star-king-distroy.corpus.json"), "utf-8"));
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonArray arrayTwo = new JsonArray();
        JsonObject jsonObject = new JsonObject();



           File file = new File("I:\\字幕訓練\\(Love+On+Delivery).srt");
           System.out.println(file.getName());

           SRTSub subtitle = new SRTParser().parse(file);
           Set<SRTLine> subtitleLineSet = subtitle.getLines();


           System.out.println(subtitleLineSet.size());
           int i = 0;
           int j = 0;

           for (SRTLine srtLine : subtitleLineSet) {
               i++;
               if (srtLine.getTextLines() != null) {
                   if (srtLine.getTextLines().size() > 0) {
                       for(String text :srtLine.getTextLines()){
                      // String text = srtLine.getTextLines().get(srtLine.getTextLines().size() - 1);
                      // System.out.println(srtLine.getTextLines().get(srtLine.getTextLines().size() - 1));
                          System.out.println(text);
                       if (text.matches("[0-9]{2}") || text.startsWith("{\\an") || text.startsWith("{\\pos")) {

                       } else {
                           if (!"".equals(text.trim())) {
                               text=text.replace("\"", "");
                               text=text.replace("-", "").replace("- ","");
                               arrayTwo.add(new JsonPrimitive(text));

                           }
                        /*   if (i % 5 == 0) {
                               j++;
                               array.add(arrayTwo);
                               arrayTwo = new JsonArray();
                           }*/
                       }

                   }
                   }

               }

           }



        array.add(arrayTwo);
        jsonObject.add("star-king-distroy", array);
        writer.write(gson.toJson(jsonObject));
        writer.close();



    }
}
