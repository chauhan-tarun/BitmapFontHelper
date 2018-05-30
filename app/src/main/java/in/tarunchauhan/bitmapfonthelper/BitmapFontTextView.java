package in.tarunchauhan.bitmapfonthelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BitmapFontTextView extends SpriteTextView {

    private Bitmap src;

//-----------------------------------------------------------------------------------------

    public BitmapFontTextView(Context context) {
        super(context);
    }

    public BitmapFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BitmapFontTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//-----------------------------------------------------------------------------------------

    public void setBitmapFont(Bitmap source, String fntFileName) {

        this.src = source;

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open(fntFileName)));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.split(" ")[0].equalsIgnoreCase("char")) {

                    setBitmapFromPos(line);

                }

                Log.d("log", "LINE : " + line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//-----------------------------------------------------------------------------------------

    @Override
    public void setText(String text) {

        if (src == null) {
            throw new RuntimeException("No bitmap is defined. Call BitmapFontTextView.setBitmapFont(Bitmap source, String fntFileName) method to define the bitmap fonts.");
        }

        super.setText(text);
    }


//-----------------------------------------------------------------------------------------

    private void setBitmapFromPos(String detailsLine) {

        if (src == null) return;

        String[] data = detailsLine.split(" ");

        int ascii, x, y, width, height;
        ascii = x = y = width = height = 0;

        for (String charDetails : data) {

            String[] kv = charDetails.split("=");

            if (kv.length == 0) continue;

            switch (kv[0].toLowerCase()) {

                case "id":
                    ascii = Integer.valueOf(kv[1]);
                    break;
                case "x":
                    x = Integer.valueOf(kv[1]);
                    break;
                case "y":
                    y = Integer.valueOf(kv[1]);
                    break;
                case "width":
                    width = Integer.valueOf(kv[1]);
                    break;
                case "height":
                    height = Integer.valueOf(kv[1]);
                    break;

            }
        }

        if (width > 0 && height > 0)
            setCharacter((char) ascii, Bitmap.createBitmap(src, x, y, width, height));
    }

//-----------------------------------------------------------------------------------------
}
