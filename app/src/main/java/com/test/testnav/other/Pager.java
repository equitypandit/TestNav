package com.test.testnav.other;

/**
 * Created by Administrator on 09-11-2018.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

  //integer to count number of tabs
  int tabCount;

  //Constructor to the class
  public Pager(FragmentManager fm, int tabCount) {
    super(fm);
    //Initializing tab count
    this.tabCount = tabCount;
  }

  //Overriding method getItem
  @Override
  public Fragment getItem(int position) {
    super.getPageTitle(position);
    //Returning the current tabs
    switch (position) {
      case 0:
        TabEquity tabEquity = new TabEquity();
        return tabEquity;
      case 1:
        TabCommodity tabCommodity = new TabCommodity();
        return tabCommodity;
      case 2:
        TabCurrency tabCurrency = new TabCurrency();
        return tabCurrency;
      default:
        return null;
    }
  }

  //Overriden method getCount to get the number of tabs
  @Override
  public int getCount() {
    return tabCount;
  }
}