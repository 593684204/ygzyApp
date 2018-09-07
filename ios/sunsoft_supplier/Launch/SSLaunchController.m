//
//  SSLaunchController.m
//  sunsoft_supplier
//
//  Created by ShawnWang on 2018/5/11.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "SSLaunchController.h"
#import "SSCustomProgressView.h"
#import "SSDownloadModel.h"
#import "SSReactNativeManager.h"
#import <React/RCTRootView.h>
#import <React/RCTBundleURLProvider.h>

@interface SSLaunchController ()<SSRNOperationDelegate>

@property (nonatomic, strong) UIImageView *progressBackground;
@property (nonatomic, strong) SSCustomProgressView *progressView;
@property (nonatomic, strong) UILabel *percentLabel;
@property (nonatomic) BOOL isLoading;
@property (nonatomic, strong) SSDownloadModel *downModel;
@property (nonatomic) BOOL firstEnter;

@property (nonatomic, strong) RCTRootView *rootView;
@property (nonatomic, strong) UIView *lunchView;
@end

@implementation SSLaunchController

- (void)viewDidLoad {
  [super viewDidLoad];

  //检测Bundle
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(detectionBundle:) name:@"detectionBundle" object:nil];
  //关闭启动页
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(downLaunchAction:) name:@"downLaunch" object:nil];
  //开启启动页
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(openLaunchAction:) name:@"openLaunch" object:nil];

  
  [SSReactNativeManager sharedManager].delegate = self;
  
  [self initWithLunchView];
  self.view = self.lunchView;
  [self beginDetection];

  [self beginLoaLoadingReactNative];
  
//  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(5.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
//  });
  
}
#pragma mark --接收通知
- (void)detectionBundle:(NSNotification *)notification {

  NSString *msgCode = notification.object;
  int code = [msgCode intValue];
  SSLog(@"megCode == %d",code);
  if (code == 0 || code == 2) {
    //重新加载ReactNative
    [self initWithReactNative];
    self.view = self.rootView;
  }
  
}
//关闭启动页
- (void)downLaunchAction:(NSNotification *)notification {
  //重新加载ReactNative
  [self initWithReactNative];
  self.view = self.rootView;
}
//开启启动页
- (void)openLaunchAction:(NSNotification *)notification {
  self.view = self.lunchView;
}

- (void)dealloc {
  [[NSNotificationCenter defaultCenter] removeObserver:self];
}

#pragma mark --LunchView

- (void)initWithLunchView {
  [self.lunchView addSubview:self.progressBackground];
  [self.progressBackground mas_makeConstraints:^(MASConstraintMaker *make) {
    make.size.mas_equalTo(CGSizeMake(170, 10.5));
    make.centerX.mas_equalTo(self.lunchView.mas_centerX);
    make.top.mas_equalTo(RelativeHeight(958/2));
  }];
  
  [self.progressBackground addSubview:self.progressView];
  [self.progressView mas_makeConstraints:^(MASConstraintMaker *make) {
    make.size.mas_equalTo(CGSizeMake(167, 7));
    make.centerX.mas_equalTo(self.progressBackground.mas_centerX);
    make.centerY.mas_equalTo(self.progressBackground.mas_centerY);
  }];
  
  [self.lunchView addSubview:self.percentLabel];
  [self.percentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
    make.bottom.mas_equalTo(self.progressBackground.mas_top).offset(-10);
    make.centerX.mas_equalTo(self.lunchView.mas_centerX);
    make.height.mas_equalTo(10);
    make.width.mas_greaterThanOrEqualTo(10);
  }];
}

#pragma mark --初始ReactNative
- (void)beginLoaLoadingReactNative {
  //检测是否有本地文件路径
  SSLog(@"=======%@",[SSReactNativeManager sharedManager].jsLocation);
  
  //开始加载包
  [[SSReactNativeManager sharedManager] beginBundleOperation];
  
  //重新加载ReactNative
  [self initWithReactNative];

}

- (void)initWithReactNative {
  NSURL *jsCodeLocation;
//  jsCodeLocation = [NSURL URLWithString:[SSReactNativeManager sharedManager].jsLocation];
  jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];

  self.rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                              moduleName:@"sunsoft_supplier"
                                       initialProperties:nil
                                           launchOptions:[SSReactNativeManager sharedManager].launchOptions];
  self.view = self.rootView;
}

#pragma mark --SSReactNativeManager Delegate
/**
 本地bundle操作完成
 */
- (void)onRNBundleUnpackOperationComplete {
  //重新加载ReactNative
  [self initWithReactNative];

}

/**
 全量更新bundle操作完成
 */
- (void)onRNBundleAllUpDateOperationComplete {
  //重新加载ReactNative
  [self initWithReactNative];
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    [self beginMerge];
  });
}

/**
 增量更新bundle操作完成
 */
- (void)onRNBundlediffUpDateOperationComplete {
  //重新加载ReactNative
  [self initWithReactNative];

}

/**
 bundle操作失败
 
 @param error
 */
- (void)onRNBundleOperationFailure:(NSError*)error {
  //重新加载ReactNative
  [self initWithReactNative];
}

//开始检测
- (void)beginDetection {
  self.percentLabel.text = [NSString stringWithFormat:@"正在检测 ···"];
//  [self.progressView setProgress:1];
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(5.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    
  });
  self.percentLabel.hidden = NO;
  self.progressBackground.hidden = YES;
}

//开始更新
- (void)beginMerge {
  self.percentLabel.text = [NSString stringWithFormat:@"正在更新资源 ···"];
//  [self.progressView setProgress:1];
  self.percentLabel.hidden = NO;
  self.progressBackground.hidden = YES;
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    self.view = self.rootView;
  });
}

/**
 下载进度
 @param progress
 */
- (void)onRNBundleDownloadProgress:(float)progress {

  self.view = self.lunchView;
  self.percentLabel.text = [NSString stringWithFormat:@"下载进度：%.0f%%",progress*100];
  [self.progressView setProgress:progress];
  self.percentLabel.hidden = NO;
  self.progressBackground.hidden = NO;
}




-(UIView *)lunchView {
  if (!_lunchView) {
    _lunchView = [[UIView alloc] initWithFrame:[UIScreen mainScreen].bounds];
    UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"launch_image"]];
    imageView.frame = self.view.bounds;
    [_lunchView addSubview:imageView];
  }
  return _lunchView;
}

-(UIImageView *)progressBackground {
  if (!_progressBackground) {
    _progressBackground = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"progress_background"]];
    _progressBackground.hidden = NO;
  }
  return _progressBackground;
}

- (SSCustomProgressView *)progressView {
  if (!_progressView) {
    _progressView = [[SSCustomProgressView alloc] initWithProgressViewStyle:UIProgressViewStyleDefault];
    _progressView.ggProgressImage = [UIImage imageNamed:@"progress_track"];
    _progressView.ggTrackImage = [UIImage imageNamed:@"progress_background"];
    _progressView.progress = 0;
    _progressView.trackTintColor = [UIColor clearColor];
  }
  return _progressView;
}

- (UILabel *)percentLabel {
  if (!_percentLabel) {
    _percentLabel = [UILabel new];
    _percentLabel.font = [UIFont systemFontOfSize:10];
    _percentLabel.textColor = UIColorHex(9b9b9b);
    _percentLabel.text = @"0%";
    _percentLabel.hidden = YES;
  }
  return _percentLabel;
}
























@end
