package com.sunsoft_supplier.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 管理Fragment
 * Created by MJX on 2017/1/11.
 */
public class MyFragmentManager {
    private static FragmentManager fgmentManager;

    /**
     *添加入栈
     * @param fragmentActivity
     * @param id
     * @param fragment
     */
    public static void addFragmentForBack(FragmentActivity fragmentActivity, int id, Fragment fragment){
        fgmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fgmentManager.beginTransaction();
        fragmentTransaction.add(id,fragment,null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     *不用添加入栈
     * @param fragmentActivity
     * @param id
     * @param fragment
     */
    public static void addFragmentNoBack(FragmentActivity fragmentActivity, int id, Fragment fragment){
        fgmentManager = fragmentActivity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fgmentManager.beginTransaction();
        fragmentTransaction.add(id,fragment,null);
        fragmentTransaction.commit();
    }

    /**
     *不用添加入栈
     * @param fragmentActivity
     * @param fragment
     */
    public static void removeFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fgmentManager = fragmentActivity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fgmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }



    /**
     *
     * @param fragmentActivity
     * @param id
     * @param newFragment
     * @param currentFragment
     */
    public static void addFragmentForBackHideCurrent(FragmentActivity fragmentActivity, int id, Fragment newFragment, Fragment currentFragment){
        fgmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fgmentManager.beginTransaction();
        fragmentTransaction.add(id,newFragment,null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.hide(currentFragment);
        fragmentTransaction.commit();
    }

}
