package in.tarunchauhan.bitmapfonthelper;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    BitmapFontTextView img;

//-----------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.iv_char);

        try {
            img.setBitmapFont(
                    BitmapFactory.decodeStream(getAssets().open("jackpot-popup.png")),
                    "jackpot-popup.fnt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        showAllBitmaps();
    }

//-----------------------------------------------------------------------------------------

    int counter = 100;

    private void showAllBitmaps() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                img.setText("$"+String.valueOf(counter));

                counter++;

                showAllBitmaps();
            }
        }, 10);

    }

//-----------------------------------------------------------------------------------------
}
