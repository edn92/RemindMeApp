package com.example.edward.remindme.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.edward.remindme.AddNewReminder;
import com.example.edward.remindme.ViewReminders;

/**
 * Created by Edward on 21/03/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String tabTitles[] = new String[] { "Reminders", "New Reminder" };

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0:
                return new ViewReminders();
            case 1:
                return new AddNewReminder();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
