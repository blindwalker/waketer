package at.kropf.waketer.helper;

import android.text.Html;

import org.jsoup.Jsoup;

/**
 * Created by martinkropf on 08.11.14.
 */
public class TextHelper {

    public static String lastfmTextHelper(String input){
        String output = input;
        if(output.contains("There are multiple artists")){
            output = output.substring(output.indexOf(": 1)"));
        }
        return Jsoup.parse(output).text();
    }
}
