package com.forever.ckcprinter.utils;

import android.widget.Toast;

import com.forever.ckcprinter.app.App;

public class ToastUtil {
    private static Toast toast;

    public static void showText(String content) {
        if (toast == null) {
            toast = Toast.makeText(App.getGlobalContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.utils.ToastUtil
 * JD-Core Version:    0.6.0
 */