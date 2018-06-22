package com.github.gridswipe_launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages. This provides the data for the {@link ViewPager}.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    Context cont;

    PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        cont = context;
    }

    /**
     * Get fragment corresponding to a specific position. This will be used to populate the
     * contents of the {@link ViewPager}.
     *
     * @param position Position to fetch fragment for.
     * @return Fragment for specified position.
     */
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        Fragment fragment = new AppGridList();
        Bundle args = new Bundle();
        args.putInt("section", position);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Get number of pages the {@link ViewPager} should render.
     *
     * @return Number of fragments to be rendered as pages.
     */
    @Override
    public int getCount() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = cont.getPackageManager().queryIntentActivities(intent, 0);

        return apps.size() / 24 + 1;
    }
}