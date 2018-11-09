package com.sergeybelkin.test;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity{

    SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("log", "MainActivity onCreate()");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    /*
    @Override
    public void handleEvent(String keywords, String category) {
        Log.i("log", "MainActivity send data to ResultFragment" + ", presenter == null: " + String.valueOf(((ResultFragment)sectionsPagerAdapter.getItem(1)).mResultPresenter == null));
        ((ResultFragment)sectionsPagerAdapter.getItem(1)).requestSubmitted(keywords, category);
    }
    */

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private Fragment[] items;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            SearchFragment searchFragment = new SearchFragment();
            ResultFragment resultFragment = new ResultFragment();
            searchFragment.addObserver(resultFragment);
            items = new Fragment[]{searchFragment, resultFragment};
        }

        @Override
        public Fragment getItem(int position) {
            return items[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
