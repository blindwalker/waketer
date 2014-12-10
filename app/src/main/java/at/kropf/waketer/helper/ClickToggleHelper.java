package at.kropf.waketer.helper;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

/**
 * Created by martinkropf on 20.11.14.
 */
public class ClickToggleHelper {

    public static boolean toggle(Context context, TextView textView, int onColor, int offColor){
        if(textView.getCurrentTextColor() == context.getResources().getColor(onColor)){
            textView.setTextColor(context.getResources().getColor(offColor));
            return false;
        }else{
            textView.setTextColor(context.getResources().getColor(onColor));
            return true;

        }
    }
}
