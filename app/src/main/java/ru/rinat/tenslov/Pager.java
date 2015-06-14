package ru.rinat.tenslov;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 Created by rinat on 08.06.15.
 */

public class Pager extends Fragment implements TestPage.TestPageCallbacks {

    View rootView;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    Boolean[] array;

    int PAGE_COUNT = App.mDbHelper.getCountTenWords() + 2;
    int TEST_PAGE_COUNT = PAGE_COUNT - 2;
    int SUMMARY_PAGE = PAGE_COUNT - 1;
    int STUDY_PAGE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                Log.d("Page", Integer.toString(position));
                if (position == PAGE_COUNT - 1) {
                    Boolean b = true;
                    for (int i = 0; i < array.length; i++) {
                        if (!array[i]) b = false;
                    }

                    if (b) Log.d("log","succeses");
                    if (!b) Log.d("log","bad");
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        array = new Boolean[TEST_PAGE_COUNT];
        for (int i = 0; i < array.length; i++) {
            array[i] = false;
        }

        return rootView;
    }

    @Override
    public void onQuestItemSelected(int position, Boolean check) {
        array[position] = check;
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == STUDY_PAGE) return new StudyPage();
            if (position == SUMMARY_PAGE) return new SummaryPage();

            return TestPage.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }
}
