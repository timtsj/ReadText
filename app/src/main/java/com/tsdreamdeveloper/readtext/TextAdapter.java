package com.tsdreamdeveloper.readtext;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 02.10.2019
 */
public class TextAdapter extends FragmentPagerAdapter {

    private List<String> list = new ArrayList<>();

    public TextAdapter(FragmentManager mgr, List<String> list) {
        super(mgr);
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list.size());
    }

    @Override
    public Fragment getItem(int position) {
        return (PageFragment.newInstance(list.get(position)));
    }
}