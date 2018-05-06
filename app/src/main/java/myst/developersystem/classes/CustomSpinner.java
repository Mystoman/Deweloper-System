package myst.developersystem.classes;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.AdapterView;

import java.lang.reflect.Method;

/**
 * Created by Michal on 03.05.18.
 */

public class CustomSpinner extends android.support.v7.widget.AppCompatSpinner {

    private int lastSelected = 0;
    private static Method s_pSelectionChangedMethod = null;


    static {
        try {
            Class noparams[] = {};
            Class targetClass = AdapterView.class;

            s_pSelectionChangedMethod = targetClass.getDeclaredMethod("selectionChanged", noparams);
            if (s_pSelectionChangedMethod != null) {
                s_pSelectionChangedMethod.setAccessible(true);
            }

        } catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void testReflectionForSelectionChanged() {
        try {
            Class noparams[] = {};
            s_pSelectionChangedMethod.invoke(this, noparams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(this.lastSelected == this.getSelectedItemPosition())
            testReflectionForSelectionChanged();
        if(!changed)
            lastSelected = this.getSelectedItemPosition();

        super.onLayout(changed, l, t, r, b);
    }

}
