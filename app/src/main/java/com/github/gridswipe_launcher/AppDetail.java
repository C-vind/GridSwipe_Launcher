package com.github.gridswipe_launcher;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;

import java.io.File;

public class AppDetail {

    private final Context cont;
    private final ActivityInfo appInfo;

    private String appLabel;
    private Drawable icon;

    private boolean mounted;
    private final File apkFile;

    AppDetail(Context context, ActivityInfo info) {
        cont = context;
        appInfo = info;

        apkFile = new File(info.applicationInfo.sourceDir);
    }

    public String getAppPackageName() {
        return appInfo.packageName;
    }

    public String getLabel() {
        return appLabel;
    }

    public Drawable getIcon() {
        if (icon == null)
            if (apkFile.exists()) {
                icon = appInfo.loadIcon(cont.getPackageManager());

                return icon;
            } else // !apkFile.exists()
                mounted = false;
        else // icon != null
            if (!mounted) {
                // If the app wasn't mounted but is now mounted, reload its icon.
                if (apkFile.exists()) {
                    mounted = true;
                    icon = appInfo.loadIcon(cont.getPackageManager());

                    return icon;
                }
            } else // mounted
                return icon;

        return cont.getDrawable(android.R.drawable.sym_def_app_icon);
    }

    void loadLabel(Context context) {
        if (appLabel == null || !mounted)
            if (!apkFile.exists()) {
                mounted = false;
                appLabel = getAppPackageName();
            } else { // apkFile.exists()
                mounted = true;
                CharSequence label = appInfo.loadLabel(context.getPackageManager());
                appLabel = (label != null) ? label.toString() : getAppPackageName();
            }
    }
}
