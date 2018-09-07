package com.sunsoft_supplier.mvp.updateapk.presenter;

import android.os.Handler;
import android.os.Message;

import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSCJavaScriptExecutorFactory;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.sunsoft_supplier.MainApplication;
import com.sunsoft_supplier.constant.AppManager;
import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.data.BundleInfoSp;
import com.sunsoft_supplier.data.GetSpInsance;
import com.sunsoft_supplier.mvp.base.BasePresenter;
import com.sunsoft_supplier.mvp.updateapk.UpdateFragmentDialog;
import com.sunsoft_supplier.mvp.updateapk.UpdateUtils;
import com.sunsoft_supplier.mvp.updateapk.contract.UpdateApkContract;
import com.sunsoft_supplier.mvp.updateapk.model.UpdateApkModel;
import com.sunsoft_supplier.net.network.IDownloadListen;
import com.sunsoft_supplier.net.network.ISecondaryCallBackData;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.UIUtil;
import com.sunsoft_supplier.utils.decompressionbundle.CreateFileUtil;
import com.sunsoft_supplier.utils.decompressionbundle.DecompressionUtil;
import com.sunsoft_supplier.utils.download.ApkUtil;
import com.sunsoft_supplier.utils.download.BundleUtil;
import com.sunsoft_supplier.utils.updatebundle.fullupdata.FullUpdataBundle;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 更新
 * Created by MJX on 2017/4/21.
 */
public class UpdateApkPresenter extends BasePresenter<UpdateFragmentDialog> implements UpdateApkContract.IUpdateApkPresenter, IDownloadListen {
    private UpdateApkModel updataApkModle;
    private String url;
    public String apkTag = "apk";
    public String bundleTag = "bundle";
    public String bundlezipTag = "bundlezip";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*是否下载成功 下载成功后防止网络切换导致的重复下载*/
    public boolean isDownSuccess = false;

    /*点击更新按钮  用来判断是否是第一次下载，防止断点续传时重复从0下载*/
    public boolean clickDown = false;

    public UpdateApkPresenter(UpdateFragmentDialog view) {
        super(view);
    }


    @Override
    protected void createModel() {
        updataApkModle = new UpdateApkModel(new ISecondaryCallBackData() {

            @Override
            public void OnSuccess(String tag, Object result) {

            }

            @Override
            public void OnError(String tag, String error) {

            }
        });
    }

    @Override
    public void onDownloadSuccess(String tag, String path) {
        isDownSuccess = true;
        if (apkTag.equals(tag)) {
            ApkUtil.installApk(path, mvpView.getActivity());
        } else if (bundleTag.equals(tag)) {
            updateAllBundle(path);
        } else if (bundlezipTag.equals(tag)) {
            updateIncreat(path);
        }
    }

    @Override
    public void onDownloading(final int progress, String tag) {
        //        Activity activity = AppManager.getInstance().currentActivity();
       /* Activity activity = mvpView.getActivity();
        if (activity == null) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mvpView.getProgressTv().setText(progress + "%");
                mvpView.getProgressPb().setProgress(progress);
            }
        });*/
        updateProgress(tag + "下载中：", progress);
    }

    @Override
    public void onDownloadFailed(String s, String tag) {
        LogUtil.logMsg("下载失败：s:" + s + "  tag:" + tag);
        MainApplication.getInstance().getMyMainReactPackage().mModule.downloadFail(tag);
        /*if ("bundle".equals(tag) || "bundlezip".equals(tag)) {//进入下一页
//            RestartAPP.restartAPP(MainApplication.getContext());
            return;
        }*/

        /*if(mvpView.getActivity()!=null) {
            mvpView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mvpView.getProgressTv().setText(0 + "%");
                    mvpView.getProgressPb().setProgress(0);
                }
            });
        }*/
    }


    @Override
    public void download(String tag, String url) {
        if (apkTag.equals(tag)) {
            String versionId = (String) GetSpInsance.getSpValue("download", tag+"versionId", "");
            if (!versionId.equals(mvpView.getUpdateBean().getObj().getBody().getVersionId())) {//本地存储的versionId与得到的不一样
                ApkUtil.delDownLoadApk();
                GetSpInsance.saveSp("download", tag+"versionId", mvpView.getUpdateBean().getObj().getBody().getVersionId());
            }
            long breakPoints = (long) GetSpInsance.getSpValue("download", tag+"current", 0L);
            long length = (long) GetSpInsance.getSpValue("download", tag+"length", 0L);
            if (breakPoints != 0 && length != 0 && breakPoints == length) { //已经下载完成了 直接安装
                ApkUtil.installApk(Constant.APK_PATH.getAbsolutePath(), mvpView.getActivity());
                return;
            }
            LogUtil.logMsg("Constant.APK_PATH:" + Constant.APK_PATH);
            updataApkModle.downloadUpdateFile(tag, url, Constant.APK_PATH, this);
        } else {
            updataApkModle.downloadUpdateFile(tag, url, BundleUtil.getLoadBundZipFile(mvpView.getUpdateBean().getObj().getBody().getBundleName()), this);
        }
    }

    private void updateAllBundle(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 下载成功后需要做的工作
                File file = null;
                //                sendMessageDelayed("解压合并：",20,3);
                //bundle的zip解压后的路径
                String bundleStr = CreateFileUtil.getFilePath("bundle", "");
                file = new File(path);
                DecompressionUtil.unZipFile(file, bundleStr);

                String hashCode = mvpView.getUpdateBean().getObj().getBody().getHashCode();
                String bundleName = mvpView.getUpdateBean().getObj().getBody().getBundleName();
                String bundleCode = mvpView.getUpdateBean().getObj().getBody().getBundleCode();
                String fileStr = BundleInfoSp.getBundleValue("fileStr");
                String initBundleFile = BundleInfoSp.getBundleValue("initBundleFile");
                String oldPath = fileStr + "/" + initBundleFile + "/index.android.bundle";
                File filesDir = UIUtil.getContext().getFilesDir();
                String newPath = filesDir + "/" + "bundle" + "/" + bundleName + "/index.android.bundle";
                FullUpdataBundle.startFullUpdataBundle(oldPath, newPath, hashCode, bundleName, bundleCode);
                //                mvpView.intoApp();
                bundleUpdateDone();
            }
        }).start();
    }

    private void updateIncreat(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String bundleStr = CreateFileUtil.getFilePath("bundle", "");
                File file = new File(path);
                DecompressionUtil.unZipFile(file, bundleStr);
                handler.sendEmptyMessage(0);
                String hashCode = mvpView.getUpdateBean().getObj().getBody().getHashCode();
                String bundleName = mvpView.getUpdateBean().getObj().getBody().getBundleName();
                String bundleCode = mvpView.getUpdateBean().getObj().getBody().getBundleCode();
                String fileStr = BundleInfoSp.getBundleValue("fileStr");
                String initBundleFile = BundleInfoSp.getBundleValue("initBundleFile");
                String oldPath = fileStr + "/" + initBundleFile + "/index.android.bundle";
                File filesDir = UIUtil.getContext().getFilesDir();
                String newPath = filesDir + "/" + "bundle" + "/" + bundleName + "/index.android.bundle";
                FullUpdataBundle.BundleIncrementalUpdate(oldPath, newPath, bundleName, bundleCode, hashCode, handler);
                if (initProgress != 100) {
                    updateProgress("", 100);
                }
                //                mvpView.intoApp();
                bundleUpdateDone();
            }
        }).start();
    }

    /*bundle合并后重启APP*/
    private void bundleUpdateDone() {
        //        RestartAPP.restartAPP(MainApplication.getContext());
        Constant.isShow = false;
        /*MainApplication.getInstance().initBundle();
//        AppManager.getAppManager().popActivity();
        Intent intent = new Intent(mvpView.getActivity(), MainActivity.class);
        mvpView.getActivity().startActivity(intent);*/
        handler.post(new Runnable() {
            @Override
            public void run() {
                UpdateUtils.dismiss();
                //                CreateReactViewManager.init(AppManager.getAppManager().currentActivity());
                //                ((MainActivity)AppManager.getAppManager().currentActivity()).initFragment();
                String fileStr = BundleInfoSp.getBundleValue("fileStr");
                String initBundleFile = BundleInfoSp.getBundleValue("initBundleFile");
                String oldPath = fileStr + "/" + initBundleFile + "/index.android.bundle";
                onJSBundleLoadedFromServer(oldPath);
            }
        });

    }

    public Handler getHandler() {
        return handler;
    }

    private int initProgress = 40;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String toast = (String) msg.obj;
                    updateProgress(toast, 20);
                    break;
            }
        }
    };

    public void sendMessageDelayed(String toast, int progress, int time) {
        Message message = new Message();
        message.what = 0;
        message.obj = toast;
        handler.sendMessageDelayed(message, time * 1000);
    }

    private void updateProgress(final String toast, final int progress) {
        if (mvpView == null || mvpView.getActivity() == null) {
            LogUtil.logMsg("mvpView == null ||mvpView.getActivity()==null");
            return;
        }
        mvpView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mvpView.getProgressTvToast().setText(toast);
                mvpView.getProgressTv().setText(progress + "%");
                mvpView.getProgressPb().setProgress(progress);

            }
        });
        //ProgressUtil.getProgressDialog().setProgress(toast,progress);

    }

    private void onJSBundleLoadedFromServer(String file) {
        LogUtil.logMsg("download success, reload js bundle");
        try {
            ReactApplication application = (ReactApplication) AppManager.getAppManager().currentActivity().getApplication();
            Class<?> RIManagerClazz = application.getReactNativeHost().getReactInstanceManager().getClass();
            Method method = RIManagerClazz.getDeclaredMethod("recreateReactContextInBackground",
                    JavaScriptExecutorFactory.class, JSBundleLoader.class);
            method.setAccessible(true);
            method.invoke(application.getReactNativeHost().getReactInstanceManager(),
                    new JSCJavaScriptExecutorFactory("sunsoft_supplier", ""),
                    JSBundleLoader.createFileLoader(file));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
