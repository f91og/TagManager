package yuxue.tagmanager.adapter;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import yuxue.tagmanager.fragment.ContentFragment;
import yuxue.tagmanager.fragment.MemoFragment;

/**
 * Created by yuxue on 2017/3/5.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;


    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }


    public Fragment getItem(int position){
        return fragments.get(position);
    }

    public int getCount(){
        return fragments.size();
    }

}
