package com.forever.ckcprinter.modle.dataManager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;

public class ActivityManager {
    private static ArrayList<Activity> activities = new ArrayList();

    public static void addActivity(Activity paramActivity) {
        activities.add(paramActivity);
    }

    public static void finishAll() {
        Iterator localIterator = activities.iterator();
        while (localIterator.hasNext())
            ((Activity) localIterator.next()).finish();
    }

    public static void removeActivity(Activity paramActivity) {
        activities.remove(paramActivity);
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.dataManager.ActivityManager
 * JD-Core Version:    0.6.0
 */