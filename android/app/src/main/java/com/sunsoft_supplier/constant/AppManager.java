package com.sunsoft_supplier.constant;

import android.app.Activity;

import com.sunsoft_supplier.utils.LogUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * Created by MJX on 2017/1/4.
 */
public class AppManager {

  private static Stack<Activity> activityStack;
  private static AppManager instance;
  //存放TabActivity的集合
  public static List<Activity> mList = new LinkedList<Activity>();
  private AppManager(){
  }
  public static AppManager getAppManager(){
    if(instance==null){
      instance=new AppManager();
    }
    return instance;
  }

  /**
   * 出栈一个Activity
   */
  public void popActivity(){
    Activity activity=activityStack.lastElement();
    if(activity!=null){
      activity.finish();
      activity=null;
    }
  }

  /**
   * 出栈当前的传入的Activity
   * @param activity
     */
  public void popActivity(Activity activity){
    if(activity!=null){
      activity.finish();
      activityStack.remove(activity);
      activity=null;
    }
  }

  /**
   * 获取当前的Activity
   * @return
     */
  public Activity currentActivity(){
    Activity activity;
    try{
       activity=activityStack.lastElement();
    }catch (Exception e){
      LogUtil.logMsg("Activity的集合为空,获取主tab");
      activity = mList!=null && mList.size()>0 ? mList.get(0):null;
    }
    return activity;
  }

  /**
   * 把当前的Activity压入栈
   * @param activity
     */
  public void pushActivity(Activity activity){
    if(activityStack==null){
      activityStack=new Stack<Activity>();
    }
    activityStack.add(activity);
  }

  /**
   *通过类名，反射弹出当前Activity之上的所有Activity
   * @param cls
   */
  public void popAllActivityExceptOne(Class cls){
    while(true){
      Activity activity=currentActivity();
      if(activity==null){
        break;
      }
      if(activity.getClass().equals(cls) ){
        break;
      }
      popActivity(activity);
    }
  }


  /**
   * 结束所有Activity
   */
  public void finishAllActivity() {
    if(activityStack == null){
      return;
    }
    int size = activityStack.size();
    for (int i = size - 1; i > -1; i--) {
      if (null != activityStack.get(i)) {
        popActivity(activityStack.get(i));
      }
    }
    activityStack.clear();
  }

  /**
   * 退出当前的应用程序
   */
  public void AppExit() {
    /*Context context = UIUtil.getContext();
    try {
      finishAllActivity();
      if(mList!=null&&mList.size()>0){
        mList.get(0).finish();
      }
      UMengUtil.onKillProcess(context);
      ActivityManager activityMgr =
              (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
      activityMgr.killBackgroundProcesses(context.getPackageName());
      System.exit(0);    } catch (Exception e) {
      LogUtil.logMsg("退出APK异常");
    }*/
  }

  /**
   * 退出APP的登录，跳转到登录页面
   * @param activity
     */
  public void exitLogin(Activity activity){
    /*ClearSpData.clearData();
    if(activity == null){
      return;
    }
    finishAllActivity();
    *//*if(SupplierTabActivity.getSupplierTabActivity() != null){
      JPushSp.saveSupplierTabDestory(false);
      SupplierTabActivity.getSupplierTabActivity().finish();
    }*//*
    if(mList!=null&&mList.size()>0){
      mList.get(0).finish();
      JPushSp.saveSupplierTabDestory(false);
    }
//    activity.startActivity(new Intent(activity, LoginActivity.class));
    ARouter.getInstance().build("/app/login").navigation();*/
  }


  /**
   * 添加TaSupplierActivity
   * @param activity
     */
  public void addActivity(Activity activity) {
    if(mList == null){
      return;
    }
    mList.add(activity);
  }

  /**
   * 移除TaSupplierActivity
   * @param activity
   */
  public void removeActivity(Activity activity) {
    if(mList == null){
      return;
    }
    mList.remove(activity);
  }

  public void clearMList() {
    LogUtil.logMsg("删除集合中的activity-----------------------");
    try {

      if(mList == null){
        return;
      }

      for (Activity activity : mList) {
        if (activity != null)
          activity.finish();
      }
    }catch (Exception e){
      e.printStackTrace();
      return;
    }
  }


}