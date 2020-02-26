package com.example.viewpagerexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

/**
 * Ejemplo simple de como implementar un ViewPager con Tablayaout, para desplazarse entre fragments.
 */
public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 2; //numero de paginas
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciar ViewPager y PagerAdapter

        mPager = (ViewPager)findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        //vincular el TabLayout
        TabLayout tab = findViewById(R.id.tab_layout);
        tab.setupWithViewPager(mPager);
    }


    // Adapter para el ViewPager
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fr= new FragmentSlice1();

            //cambia de fragment en función de la página seleccionada
            switch (position){

                case 1: fr= new FragmentSlice1();
                case 2: fr= new FragmentSlice2();
            }
            return fr;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Nullable
        @Override //Título de la página
        public CharSequence getPageTitle(int position) {
            return "Fragmento " + (position + 1);
        }
    }
}
