package com.developerputra.movieuidanux;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MenuFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    public MenuFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));
        tabLayout = view.findViewById(R.id.tab);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    private class sliderAdapter extends FragmentPagerAdapter {
        String sedang_tayang = getResources().getString(R.string.sedang_tayang);
        String akan_datang = getResources().getString(R.string.akan_datang);
        final String tabs[] = {sedang_tayang, akan_datang};

        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SedangFragment();
                case 1:
                    return new DatangFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}

