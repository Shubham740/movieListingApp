package com.assignment.root.movielistingapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.assignment.root.movielistingapp.fragment.LastViewedFragment;
import com.assignment.root.movielistingapp.model.Results;
import com.assignment.root.movielistingapp.utils.SaveDataThroughSharedPreferences;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by user on 19/7/17.
 * author Pallavi Tripathi
 */

public class ViewPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener {
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTabTitles;
    private Context context;

    public ViewPagerAdapter(Context context, FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        mFragments = new ArrayList<>();
        mTabTitles = new ArrayList<>();
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public void addFragments(Fragment fragment, String title) {
        this.mFragments.add(fragment);
        this.mTabTitles.add(title);

    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mFragments.get(position);
        if (fragment instanceof LastViewedFragment) {
            LastViewedFragment lastViewedFragment = (LastViewedFragment) fragment;

            LinkedList<Results> linkedList = SaveDataThroughSharedPreferences.getNewInstance(context).getSharedPReferenceLinkedList();
            if (linkedList != null) {
                lastViewedFragment.setLastViewedList(linkedList);
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setTitle(String s) {
        mTabTitles.set(0,s);
        notifyDataSetChanged();
    }

}
