package at.kropf.waketer.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontedTextView extends TextView {
    public FontedTextView(Context context) {
        super(context);
        init(context);
    }

    public FontedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        String otfName = "CaviarDreams.ttf";
        Typeface font = Typeface.createFromAsset(context.getAssets(), otfName);
        isInEditMode();
        this.setTypeface(font);
    }
}