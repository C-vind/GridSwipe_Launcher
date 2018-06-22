package com.github.gridswipe_launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class AppGridList extends GridFragment implements LoaderManager.LoaderCallbacks<ArrayList<AppDetail>> {

    AppAdapter appAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No Applications");

        appAdapter = new AppAdapter(getActivity());
        setGridAdapter(appAdapter);

        // Till the data is loaded display a spinner
        setGridShown(false);

        // Create the loader to load the apps list in background
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<AppDetail>> onCreateLoader(int id, Bundle bundle) {
        return new AppLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppDetail>> loader, ArrayList<AppDetail> apps) {
        int position = getArguments().getInt("section");
        appAdapter.setData(apps, position);

        if (isResumed())
            setGridShown(true);
        else // !isResumed()
            setGridShownNoAnimation(true);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppDetail>> loader) {
        appAdapter.setData(null, 0);
    }

    // Start the application after it is clicked
    @Override
    public void onGridItemClick(GridView g, View v, int position, long id) {
        AppDetail app = (AppDetail) getGridAdapter().getItem(position);
        if (app != null) {
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(app.getAppPackageName());

            if (intent != null)
                startActivity(intent);
        }
    }
}