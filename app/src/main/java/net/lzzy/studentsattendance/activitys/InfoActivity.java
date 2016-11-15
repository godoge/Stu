package net.lzzy.studentsattendance.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import net.lzzy.studentsattendance.R;
import net.lzzy.studentsattendance.fragments.BasicInfoFragment;
import net.lzzy.studentsattendance.fragments.ComprehensiveFragment;
import net.lzzy.studentsattendance.fragments.MeLearnFragment;


public class InfoActivity extends AppCompatActivity {
    private Fragment[] fragments;
    private String[] titles;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
    }


    private void initView() {
        pager = (ViewPager) findViewById(R.id.activity_info_viewpager);
        titles = new String[3];
        titles[0] = "基本情况";
        titles[1] = "我的学习";
        titles[2] = "综合测评";
        fragments = new Fragment[3];
        BasicInfoFragment fragment_basic = new BasicInfoFragment();
        ComprehensiveFragment fragment_compre = new ComprehensiveFragment();
        MeLearnFragment fragment_meLearn = new MeLearnFragment();
        fragments[0] = fragment_basic;
        fragments[1] = fragment_meLearn;
        fragments[2] = fragment_compre;
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_info_toolbar);
        setSupportActionBar(toolbar);
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_into_back);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.activity_info_tabs);
        assert mTabLayout != null;
        mTabLayout.setupWithViewPager(pager);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
