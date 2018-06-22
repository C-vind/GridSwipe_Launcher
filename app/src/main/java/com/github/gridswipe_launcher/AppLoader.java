package com.github.gridswipe_launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.content.AsyncTaskLoader;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @credit http://developer.android.com/reference/android/content/AsyncTaskLoader.html
 */

public class AppLoader extends AsyncTaskLoader<ArrayList<AppDetail>> {
    private ArrayList<AppDetail> installedApps;
    private final PackageManager pManager;
    private PackageIntentReceiver pObserver;

    AppLoader(Context context) {
        super(context);
        pManager = context.getPackageManager();
    }

    @Override
    public ArrayList<AppDetail> loadInBackground() {
        final Context cont = getContext();
        ArrayList<AppDetail> appList = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // Loop and fetch all the Intents that have a category of Intent.CATEGORY_LAUNCHER
        List<ResolveInfo> launchableApp = pManager.queryIntentActivities(intent, 0);
        for (ResolveInfo ri: launchableApp) {
            AppDetail app = new AppDetail(cont, ri.activityInfo);
            app.loadLabel(cont);

            //if (!"HorizontalGrid".equals(app.getLabel()))
            appList.add(app);
        }

        // Sort the list of applications
        Collections.sort(appList, ALPHA_COMPARATOR);

        return appList;
    }

    @Override
    public void deliverResult(ArrayList<AppDetail> apps) {
        installedApps = apps;

        // If the Loader is currently started,
        // its results can be immediately delivered.
        if (isStarted())
            super.deliverResult(apps);
    }

    @Override
    protected void onStartLoading() {
        // If currently there is a result available, deliver it immediately.
        if (installedApps != null)
            deliverResult(installedApps);

        // Watch for changes in app install and uninstall operation
        if (pObserver == null)
            pObserver = new PackageIntentReceiver(this);

        // If the data has changed since the last time it was loaded
        // or is not currently available, start a load.
        if (takeContentChanged() || installedApps == null )
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {
        // Ensure the loader is stopped
        onStopLoading();

        if (installedApps != null)
            installedApps = null;

        // Stop monitoring for changes.
        if (pObserver != null) {
            getContext().unregisterReceiver(pObserver);
            pObserver = null;
        }
    }

    /**
     * Perform alphabetical comparison of application entry objects.
     */
    private static final Comparator<AppDetail> ALPHA_COMPARATOR = new Comparator<AppDetail>() {
        private final Collator collator = Collator.getInstance();
        @Override
        public int compare(AppDetail object1, AppDetail object2) {
            return collator.compare(object1.getLabel(), object2.getLabel());
        }
    };
}
