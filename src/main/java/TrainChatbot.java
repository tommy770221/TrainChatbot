import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.submerge.api.parser.SRTParser;
import com.submerge.api.parser.exception.InvalidFileException;
import com.submerge.api.parser.exception.InvalidSubException;
import com.submerge.api.subtitle.common.SubtitleLine;
import com.submerge.api.subtitle.srt.SRTLine;
import com.submerge.api.subtitle.srt.SRTSub;

import java.io.*;
import java.util.Set;

/**
 * Created by Tommy on 2017/4/19.
 */

public class TrainChatbot {

    public static void main(String[] args) throws IOException, InvalidSubException, InvalidFileException {

       // BufferedReader br = new BufferedReader(new FileReader("I:\\字幕訓練\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE.繁体.txt"));
        Writer writer = null;
        writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("I:\\字幕訓練\\The.Good.Fight.S01E10.720p.WEBRip.X264-DEFLATE\\fight-season-one.corpus.json"), "utf-8"));
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonArray arrayTwo = new JsonArray();
        JsonObject jsonObject = new JsonObject();
       for(int z=1;z<23;z++) {

           String zString = String.format("%02d", z);
           File file = new File("I:\\字幕訓練\\chd-pb1" + zString + "-dts-bdrip.cht.srt");
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
                       String text = srtLine.getTextLines().get(srtLine.getTextLines().size() - 1);
                       System.out.println(srtLine.getTextLines().get(srtLine.getTextLines().size() - 1));

                       if (text.matches("[0-9]{2}") || text.startsWith("{\\an") || text.startsWith("{\\pos")) {

                       } else {
                           if (!"".equals(text.trim())) {
                               text=text.replace("\"", "");
                               text=text.replace("-", "").replace("- ","");
                               arrayTwo.add(new JsonPrimitive(text));

                           }
                           if (i % 5 == 0) {
                               j++;
                               array.add(arrayTwo);
                               arrayTwo = new JsonArray();
                           }
                       }

                   }

               }

           }


       }
       // array.add(arrayTwo);
        jsonObject.add("fight-season-one", array);
        writer.write(gson.toJson(jsonObject));
        writer.close();



    }
}
