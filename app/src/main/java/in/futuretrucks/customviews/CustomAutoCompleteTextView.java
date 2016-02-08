package in.futuretrucks.customviews;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;

import in.futuretrucks.R;

/**
 * Created by amit.mohite on 12/4/2015.
 */
public class CustomAutoCompleteTextView extends AutoCompleteTextView {
    private Typeface tf = null, tfhint = null;
    private String customhintfont, customFont;
    boolean inputtypepassword;
    private CharSequence chartype;


    /** Returns the place description corresponding to the selected item */
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        /** Each item in the autocompetetextview suggestion list is a hashmap object */
        HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
        return hm.get("description");
    }

    public CustomAutoCompleteTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        // initViews();
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        // initViews();
        setCustomFontEdittext(context, attrs);

    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        // initViews();
        setCustomFontEdittext(context, attrs);
    }

    private void setCustomFontEdittext(Context ctx, AttributeSet attrs) {
        final TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomEditText);
        customFont = a.getString(R.styleable.CustomEditText_edittextfont);
        customhintfont = a
                .getString(R.styleable.CustomEditText_edittextfontHint);

        // custompwd = a.getString(R.styleable.CustomEditText_edittextpwd);
        inputtypepassword = a.getBoolean(
                R.styleable.CustomEditText_edittextpwd, false);
        setCustomFontEdittext(ctx, customFont);
        setCustomFontEdittextHint(ctx, customhintfont);

        chartype = (CharSequence) a
                .getText(R.styleable.CustomEditText_editcharpwd);
        setCustompwd(inputtypepassword);
        a.recycle();
    }

    public boolean setCustompwd(boolean pwd) {
        if (pwd) {
            this.setTransformationMethod(new PasswordCharacterChange());
        }
        return pwd;
    }

    public boolean setCustomFontEdittext(Context ctx, String asset) {
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tf);
        return true;
    }

    public boolean setCustomFontEdittextHint(Context ctx, String asset) {
        try {
            tfhint = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tfhint);

        return true;
    }

    public class PasswordCharacterChange extends PasswordTransformationMethod {

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            // TODO Auto-generated method stub
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return chartype.charAt(0); // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }

}
