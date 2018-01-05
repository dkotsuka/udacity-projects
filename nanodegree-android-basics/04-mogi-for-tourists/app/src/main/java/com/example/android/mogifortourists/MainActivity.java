package com.example.android.mogifortourists;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        AppPagerAdapter adapter = new AppPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.about);
        tabLayout.getTabAt(1).setText(R.string.spots);
        tabLayout.getTabAt(2).setText(R.string.restaurants);
        tabLayout.getTabAt(3).setText(R.string.to_buy);
        tabLayout.getTabAt(4).setText(R.string.festivals);
    }
}
