package in.tarunchauhan.bitmapfonthelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;

public class SpriteTextView extends LinearLayout {

    // Sprites(or Drawables) for characters
    protected HashMap<Character, Bitmap> characters = new HashMap<>();

    // Set to true by default to remove extra space between characters
    private boolean adjustViewBounds = true;

//-----------------------------------------------------------------------------------------

    public SpriteTextView(Context context) {
        super(context);
    }

    public SpriteTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpriteTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SpriteTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//-----------------------------------------------------------------------------------------

    public void setAdjustViewBounds(boolean adjustViewBounds) {
        this.adjustViewBounds = adjustViewBounds;
    }

//-----------------------------------------------------------------------------------------

    public void setNumbers(@DrawableRes int[] zeroToNineSortedNumbers) {

        // Check if all numbers are present
        if (zeroToNineSortedNumbers.length != 10) {
            throw new RuntimeException("Size of numbers should be 10 including all 0 to 9 characters bitmap");
        }

        // Convert res into bitmaps
        Bitmap[] chars = new Bitmap[zeroToNineSortedNumbers.length];
        for (int i = 0; i < zeroToNineSortedNumbers.length; i++) {
            chars[i] = BitmapFactory.decodeResource(getResources(), zeroToNineSortedNumbers[i]);
        }

        setNumbers(chars);
    }

//-----------------------------------------------------------------------------------------

    public void setNumbers(Bitmap[] zeroToNineSortedNumbers) {

        // Check if all numbers are present
        if (zeroToNineSortedNumbers.length != 10) {
            throw new RuntimeException("Size of numbers should be 10 including all 0 to 9 characters bitmap");
        }

        for (int i = 0; i < zeroToNineSortedNumbers.length; i++) {
            characters.put(Character.forDigit(i, 10), zeroToNineSortedNumbers[i]);
        }
    }

//-----------------------------------------------------------------------------------------

    public void setLowercaseCharacters(Bitmap[] sortedLowercaseCharacters) {

        if (sortedLowercaseCharacters.length != 26) {
            throw new RuntimeException("Size of characters should be 26 including all a to z lowercase characters bitmap");
        }

        // Ascii value of lowercase a to z is 97 to 122
        int ascii = 97;

        for (int i = 0; i < 26; i++) {

            characters.put((char) ascii, sortedLowercaseCharacters[i]);
            ascii++;

        }
    }

//-----------------------------------------------------------------------------------------

    public void setUppercaseCharacters(Bitmap[] sortedUppercaseCharacters) {

        if (sortedUppercaseCharacters.length != 26) {
            throw new RuntimeException("Size of characters should be 26 including all a to z lowercase characters bitmap");
        }

        // Ascii value of uppercase a to z is 97 to 122
        int ascii = 65;

        for (int i = 0; i < 26; i++) {

            characters.put((char) ascii, sortedUppercaseCharacters[i]);
            ascii++;

        }
    }

//-----------------------------------------------------------------------------------------

    public void setCharacter(char character, @DrawableRes int charRes) {
        setCharacter(character, BitmapFactory.decodeResource(getResources(), charRes));
    }

//-----------------------------------------------------------------------------------------

    public void setCharacter(char character, Bitmap characterSprite) {
        characters.put(character, characterSprite);
    }

//-----------------------------------------------------------------------------------------

    public void setCharacters(HashMap<Character, Bitmap> bitmapCharacters) {
        characters.putAll(bitmapCharacters);
    }

//-----------------------------------------------------------------------------------------

    public void setText(String text) {

        // Get characters
        char[] chars = text.toCharArray();

        // Adjust child views according to the characters needed
        adjustCharactersSize(chars.length);

        // Set characters
        for (int i = 0; i < chars.length; i++) {

            ((ImageView) getChildAt(i)).setImageBitmap(characters.get(chars[i]));

        }
    }

//-----------------------------------------------------------------------------------------

    private void adjustCharactersSize(int currentTextCharSize) {

        // Get the difference between current added child views and total child views needed
        int diff = currentTextCharSize - getChildCount();

        // Adjust current child views to total child views needed
        if (diff > 0) {
            addChildViews(diff);
        } else if (diff < 0) {
            removeChildViews(Math.abs(diff));
        }
    }

//-----------------------------------------------------------------------------------------

    private void addChildViews(int count) {

        for (int i = 0; i < count; i++) {

            addView(getSpriteChar());

        }

    }

//-----------------------------------------------------------------------------------------

    private void removeChildViews(int count) {

        for (int i = 0; i < count; i++) {

            removeViewAt(i);

        }

    }

//-----------------------------------------------------------------------------------------

    private ImageView getSpriteChar() {

        ImageView singleChar = new ImageView(getContext());
        singleChar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        singleChar.setAdjustViewBounds(adjustViewBounds);
        return singleChar;

    }

//-----------------------------------------------------------------------------------------
}
