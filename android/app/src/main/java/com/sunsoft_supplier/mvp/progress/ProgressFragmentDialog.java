package com.sunsoft_supplier.mvp.progress;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSCJavaScriptExecutorFactory;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.google.gson.Gson;
import com.sunsoft_supplier.MainApplication;
import com.sunsoft_supplier.R;
import com.sunsoft_supplier.bean.UpdateBean;
import com.sunsoft_supplier.constant.AppManager;
import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.data.BundleInfoSp;
import com.sunsoft_supplier.data.GetSpInsance;
import com.sunsoft_supplier.mvp.base.BaseFragmentDialog;
import com.sunsoft_supplier.mvp.updateapk.model.UpdateApkModel;
import com.sunsoft_supplier.net.network.IDownloadListen;
import com.sunsoft_supplier.net.network.ISecondaryCallBackData;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.UIUtil;
import com.sunsoft_supplier.utils.decompressionbundle.CreateFileUtil;
import com.sunsoft_supplier.utils.decompressionbundle.DecompressionUtil;
import com.sunsoft_supplier.utils.download.BundleUtil;
import com.sunsoft_supplier.utils.updatebundle.fullupdata.FullUpdataBundle;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 弹框
 * Created by MJX on 2017/4/19.
 */
public class ProgressFragmentDialog extends BaseFragmentDialog implements IDownloadListen {

    private TextView tv_toast, tv_progress;
    private ProgressBar progressBar;

    public TextView getTv_toast() {
        return tv_toast;
    }

    public TextView getTv_progress() {
        return tv_progress;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    private UpdateBean updateBean;
    private Gson gson = new Gson();
    private UpdateApkModel updateApkModel;
    private String toast;

    @Override
    public View subCreateView() {
        return UIUtil.inflate(R.layout.dialog_progress);
    }

    @Override
    public void subInitView() {
        setCancelable(false);
        tv_toast = (TextView) mView.findViewById(R.id.progress_tv_toast);
        tv_progress = (TextView) mView.findViewById(R.id.progress_tv);
        progressBar = (ProgressBar) mView.findViewById(R.id.progress_pb);
        tv_toast.setText(toast);
        tv_progress.setText(initProgress + "%");
        progressBar.setProgress(initProgress);
    }

    @Override
    public void subInitData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }

    public void setProgress(final String toast, final int progress) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tv_progress.getVisibility() == View.GONE || progressBar.getVisibility() == View.GONE) {
                    tv_progress.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                tv_toast.setText(toast);
                tv_progress.setText(progress + "%");
                progressBar.setProgress(progress);
            }
        });

    }

    public void hideProgress(final String toast) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_toast.setText(toast);
                tv_progress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void initUpdateBean(final String json) {
        updateBean = gson.fromJson(json, UpdateBean.class);
        String isincrement = updateBean.getObj().getBody().getIsIncrement();
        if ("0".equals(isincrement)) { //bundle全量更新
            updateBundle("bundle");
        } else if ("1".equals(isincrement)) {  /*增量更新*/
            updateBundle("bundlezip");
        }

    }

    private void updateBundle(String tag) {
        updateApkModel = new UpdateApkModel(new ISecondaryCallBackData() {
            @Override
            public void OnSuccess(String tag, Object result) {

            }

            @Override
            public void OnError(String tag, String error) {

            }
        });

        String bundleId = (String) GetSpInsance.getSpValue("download", tag + "bundleId", "");
        if (!bundleId.equals(updateBean.getObj().getBody().getBundleId())) {//本地存储的versionId与得到的不一样
            BundleUtil.deleteBundZipFile(updateBean.getObj().getBody().getBundleName());
            GetSpInsance.saveSp("download", tag + "bundleId", updateBean.getObj().getBody().getVersionId());
        }
        long breakPoints = (long) GetSpInsance.getSpValue("download", tag + "current", 0L);
        long length = (long) GetSpInsance.getSpValue("download", tag + "length", 0L);
        if (breakPoints != 0 && length != 0 && breakPoints == length) { //已经下载完成了
            onDownloadSuccess(tag, BundleUtil.getLoadBundZipFile(updateBean.getObj().getBody().getBundleName()).getAbsolutePath());
            return;
        }
        updateApkModel.downloadUpdateFile(tag, updateBean.getObj().getBody().getUrl(), BundleUtil.getLoadBundZipFile(updateBean.getObj().getBody().getBundleName()), this);

    }

    @Override
    public void onDownloadSuccess(String tag, final String path) {
        if ("bundle".equals(tag)) {
            hideProgress("下载完成，解压中");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateAllBundle(path);
                }
            }, 3000);

        } else if ("bundlezip".equals(tag)) {
            updateIncreat(path);
        }
    }

    @Override
    public void onDownloading(int progress, String tag) {
        LogUtil.logMsg("bundle下载中progress:" + progress);
        //        sendMessageDelayed("bundle下载中", progress * 2/10, 0);
        setProgress("下载中", progress);
    }

    @Override
    public void onDownloadFailed(String s, String tag) {
        LogUtil.logMsg("下载失败：s:" + s + "  tag:" + tag);
        MainApplication.getInstance().getMyMainReactPackage().mModule.downloadFail(tag);
    }

    private void updateAllBundle(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 下载成功后需要做的工作
                File file = null;
                //bundle的zip解压后的路径
                String bundleStr = CreateFileUtil.getFilePath("bundle", "");
                file = new File(path);
                DecompressionUtil.unZipFile(file, bundleStr);

                String hashCode = updateBean.getObj().getBody().getHashCode();
                String bundleName = updateBean.getObj().getBody().getBundleName();
                String bundleCode = updateBean.getObj().getBody().getBundleCode();
                String fileStr = BundleInfoSp.getBundleValue("fileStr");
                String initBundleFile = BundleInfoSp.getBundleValue("initBundleFile");
                String oldPath = fileStr + "/" + initBundleFile + "/index.android.bundle";
                File filesDir = UIUtil.getContext().getFilesDir();
                String newPath = filesDir + "/" + "bundle" + "/" + bundleName + "/index.android.bundle";
                FullUpdataBundle.startFullUpdataBundle(oldPath, newPath, hashCode, bundleName, bundleCode);
                //                mvpView.intoApp();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundleUpdateDone();
                    }
                }, 3000);
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
                //                handler.sendEmptyMessage(0);
                String hashCode = updateBean.getObj().getBody().getHashCode();
                String bundleName = updateBean.getObj().getBody().getBundleName();
                String bundleCode = updateBean.getObj().getBody().getBundleCode();
                String fileStr = BundleInfoSp.getBundleValue("fileStr");
                String initBundleFile = BundleInfoSp.getBundleValue("initBundleFile");
                String oldPath = fileStr + "/" + initBundleFile + "/index.android.bundle";
                File filesDir = UIUtil.getContext().getFilesDir();
                String newPath = filesDir + "/" + "bundle" + "/" + bundleName + "/index.android.bundle";
                FullUpdataBundle.BundleIncrementalUpdate(oldPath, newPath, bundleName, bundleCode, hashCode, handler);
                /*if (progress != 100) {
                    setProgress("", 100);
                }*/
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
                //                CreateReactViewManager.init(AppManager.getAppManager().currentActivity());
                //                ((MainActivity)AppManager.getAppManager().currentActivity()).initFragment();
                //                sendMessageDelayed("bundle重新加载", 20, 1);

            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressUtil.dismiss();
                onJSBundleLoadedFromServer();
            }
        });

    }

    public Handler getHandler() {
        return handler;
    }

    private int initProgress = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String toast = (String) msg.obj;
                    int p = msg.arg1;
                    initProgress += p;
                    LogUtil.logMsg("initProgress:" + initProgress);
                    //                    setProgress(toast, initProgress);
                    if (initProgress == 100) {
                        //                        ProgressUtil.dismiss();
                        //                        onJSBundleLoadedFromServer();
                    }
                    break;

            }
        }
    };

    public void setToast(final String toast) {
        if (getActivity() == null) {
            this.toast = toast;
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_toast.setText(toast);
            }
        });
    }


    private void onJSBundleLoadedFromServer() {
        LogUtil.logMsg("download success, reload js bundle");
        try {

            String fileStr = BundleInfoSp.getBundleValue("fileStr");
            String initBundleFile = BundleInfoSp.getBundleValue("initBundleFile");
            String oldPath = fileStr + "/" + initBundleFile + "/index.android.bundle";
            LogUtil.logMsg(oldPath);
            ReactApplication application = (ReactApplication) AppManager.getAppManager().currentActivity().getApplication();
            Class<?> RIManagerClazz = application.getReactNativeHost().getReactInstanceManager().getClass();
            Field f = RIManagerClazz.getDeclaredField("mJavaScriptExecutorFactory");
            f.setAccessible(true);
            JavaScriptExecutorFactory javaScriptExecutorFactory = (JavaScriptExecutorFactory) f.get(application.getReactNativeHost().getReactInstanceManager());
            Method method = RIManagerClazz.getDeclaredMethod("recreateReactContextInBackground",
                    JavaScriptExecutorFactory.class, JSBundleLoader.class);

            method.setAccessible(true);
            method.invoke(application.getReactNativeHost().getReactInstanceManager(),
                    new JSCJavaScriptExecutorFactory("sunsoft_supplier", ""),
                    //                                        javaScriptExecutorFactory,
                   /* new JavaScriptExecutorFactory() {
                        @Override
                        public JavaScriptExecutor create() throws Exception {
                            return new JavaScriptExecutor(new HybridData()) {
                            };
                        }
                    },*/
                    JSBundleLoader.createFileLoader(oldPath));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
