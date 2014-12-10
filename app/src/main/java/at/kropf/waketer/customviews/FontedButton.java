package at.kropf.waketer.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

class FontedButton extends Button {
    public FontedButton(Context context) {
        super(context);
        init(context);
    }

    public FontedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        String otfName = "CaviarDreams.ttf";
        Typeface font = Typeface.createFromAsset(context.getAssets(), otfName);
        this.setTypeface(font);
    }
}