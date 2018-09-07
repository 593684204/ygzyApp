package com.sunsoft_supplier.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by MJX on 2016/7/17.
 */
public class MyMainReactPackage implements ReactPackage {
    public RNBridgeModule mModule;
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
//        LogUtil.logMsg("--------------------1");
        mModule = new RNBridgeModule(reactContext);
        return Arrays.<NativeModule>asList(
                /*new AppStateModule(reactContext),
                new AsyncStorageModule(reactContext),
                new CameraRollManager(reactContext),
                new ClipboardModule(reactContext),
                new DatePickerDialogModule(reactContext),
                new DialogModule(reactContext),
                new FrescoModule(reactContext),
                new ImageEditingManager(reactContext),
                new ImageLoaderModule(reactContext),
                new ImageStoreManager(reactContext),
                new IntentModule(reactContext),
                new LocationModule(reactContext),
                new NetworkingModule(reactContext),
                new NetInfoModule(reactContext),
                new StatusBarModule(reactContext),
                new TimePickerDialogModule(reactContext),
                new ToastModule(reactContext),
                new VibrationModule(reactContext),
                new WebSocketModule(reactContext),*/
                mModule
        );
    }



    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
        /*return Arrays.<ViewManager>asList(
                ARTRenderableViewManager.createARTGroupViewManager(),
                ARTRenderableViewManager.createARTShapeViewManager(),
                ARTRenderableViewManager.createARTTextViewManager(),
                new ARTSurfaceViewManager(),
                new ReactDialogPickerManager(),
                new ReactDrawerLayoutManager(),
                new ReactDropdownPickerManager(),
                new ReactHorizontalScrollViewManager(),
                new ReactImageManager(),
                new ReactProgressBarViewManager(),
                new ReactRawTextManager(),
                new ReactScrollViewManager(),
                new ReactSliderManager(),
                new ReactSwitchManager(),
                new FrescoBasedReactTextInlineImageViewManager(),
                new ReactTextInputManager(),
                new ReactTextViewManager(),
                new ReactToolbarManager(),
                new ReactViewManager(),
                new ReactViewPagerManager(),
                new ReactVirtualTextViewManager(),
                new ReactWebViewManager(),
                new SwipeRefreshLayoutManager());*/
    };
}
