package at.kropf.waketer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import at.kropf.waketer.R;
import at.kropf.waketer.adapter.CustomPageAdapter;
import at.kropf.waketer.fragments.StartupFirst;
import at.kropf.waketer.fragments.StartupSecond;
import at.kropf.waketer.fragments.StartupThird;

/**
 * Created by martinkropf on 05.07.14.
 */
public class StartUpAcitivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        CustomPageAdapter pageAdapter;
        List<Fragment> fragments = getFragments();
        pageAdapter = new CustomPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        //Bind the title indicator to the adapter
        CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        pager.setAdapter(pageAdapter);
        mIndicator.setViewPager(pager);
    }

    private static List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(StartupFirst.newInstance());
        fList.add(StartupSecond.newInstance());
        fList.add(StartupThird.newInstance());


        return fList;
    }

}
