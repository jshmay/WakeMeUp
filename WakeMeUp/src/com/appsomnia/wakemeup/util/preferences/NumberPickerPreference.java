package com.appsomnia.wakemeup.util.preferences;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

/**
 * A {@link android.preference.Preference} that displays a number picker as a dialog.
 */
public class NumberPickerPreference extends DialogPreference {

    private NumberPicker picker;
    private int value;
    private int maxValue;
    private int minValue;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

	public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);
        
        return dialogView;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            setValue(picker.getValue());
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, minValue);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedInt(minValue) : (Integer) defaultValue);
    }

    public void setValue(int value) {
        this.value = value;
        persistInt(this.value);
    }

    public int getValue() {
        return this.value;
    }
    
    private void init(AttributeSet attrs) {
        minValue = attrs.getAttributeIntValue
        		("http://schemas.android.com/apk/lib/com.sunapps.wakemeup.util.preferences.NumberPickerPreference", "minValue", 1);
        maxValue = attrs.getAttributeIntValue
        		("http://schemas.android.com/apk/lib/com.sunapps.wakemeup.util.preferences.NumberPickerPreference", "maxValue", 60);		
	}
}