package in.tarunchauhan.bitmapfonthelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * This class is specially designed to be used on places
 * where you need to show amount to the user and you have images for the
 * amount. For example, You have images of characters 0-9 and special characters
 * to be used in the amount. For example, comma(,) or dollar($), etc.
 * <p>
 * In simple words, You can use this class when you have to use images
 * instead of text to show some amount to the users.
 * <p>
 * If you want to show any character to the user instead of just amount,
 * then you can use {@link SpriteTextView} by passing all of your Images along with the characters.
 * <p>
 * To use this class, you must set the value of all numeric characters using {@link #setNumbers(int[])}
 * or {@link #setNumbers(Bitmap[])}.
 * <p>
 * If you want to show currency symbol, you can do it using method {@link #setCurrencyCode(char, int)}
 * or {@link #setCurrencyCode(char, Bitmap)}.
 * <p>
 * To enable comma formatting use method {@link #showAmountWithCommaFormat(int)} or {@link #showAmountWithCommaFormat(Bitmap)}
 * <p>
 * If amount is in Double(or includes decimal places) then you have to set the .(dot) character using
 * {@link #setCharacter(char, int)} or {@link #setCharacter(char, Bitmap)} by passing '.' as the
 * first parameter before setting the value.
 * <p>
 * To set the value after added all necessary characters, use method {@link #setValue(int)} for non-decimal values
 * and {@link #setValue(double, int)} for decimal values.
 */
public class SpriteNumberView extends SpriteTextView {

    private boolean isCommaFormatted = false;

    private char currencyCode;

//-----------------------------------------------------------------------------------------

    public SpriteNumberView(Context context) {
        super(context);
    }

    public SpriteNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpriteNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpriteNumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//-----------------------------------------------------------------------------------------

    public void setValue(int value) {
        setValue(value, 0);
    }

//-----------------------------------------------------------------------------------------

    public void setValue(double value, int decimalPlaces) {

        if (decimalPlaces > 0 && characters.get('.') == null) {
            throw new RuntimeException("You need to set the .(dot) character for decimal places. Use setCharacter('.', DotCharBitmap) method.");
        }

        StringBuilder amount = new StringBuilder();

        // Add currency code
        if (currencyCode != 0) {
            amount.append(currencyCode);
        }

        // Add Formatted amount
        amount.append(getFormattedAmount(value, decimalPlaces));

        setText(amount.toString());
    }

//-----------------------------------------------------------------------------------------

    public void setCurrencyCode(char currencyCode, @DrawableRes int currencyCodeSprite) {
        setCurrencyCode(currencyCode, BitmapFactory.decodeResource(getResources(), currencyCodeSprite));
    }

//-----------------------------------------------------------------------------------------

    public void setCurrencyCode(char currencyCode, Bitmap currencyCodeSprite) {
        this.currencyCode = currencyCode;
        setCharacter(currencyCode, currencyCodeSprite);
    }

//-----------------------------------------------------------------------------------------

    public void showAmountWithCommaFormat(@DrawableRes int comma) {
        showAmountWithCommaFormat(BitmapFactory.decodeResource(getResources(), comma));
    }

//-----------------------------------------------------------------------------------------

    public void showAmountWithCommaFormat(Bitmap comma) {
        isCommaFormatted = true;
        setCharacter(',', comma);
    }

//-----------------------------------------------------------------------------------------

    public void removeCommaFormat() {
        isCommaFormatted = false;
    }

//-----------------------------------------------------------------------------------------

    public boolean isValueDefined() {
        return getChildCount() > 0;
    }

//-----------------------------------------------------------------------------------------

    private String getFormattedAmount(double amount, int decimalPlaces) {

        StringBuilder pattern = new StringBuilder();

        if (isCommaFormatted) {
            pattern.append("#,###,##0");
        } else {
            pattern.append("0");
        }

        if (decimalPlaces > 0) {
            pattern.append(".");
        }

        for (int i = 0; i < decimalPlaces; i++) {
            pattern.append("0");
        }

        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);

        df.applyPattern(pattern.toString());

        return df.format(amount);
    }

//-----------------------------------------------------------------------------------------
}
