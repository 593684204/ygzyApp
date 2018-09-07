/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "AppDelegate.h"
#import <RCTJPushModule.h>
#ifdef NSFoundationVersionNumber_iOS_9_x_Max
#import <UserNotifications/UserNotifications.h>
#endif

//#import "SSRNManager.h"
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import "SSReactNativeManager.h"
//#import "SplashScreen.h"  // here
#import "SSLaunchController.h"

@implementation AppDelegate

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
  [JPUSHService registerDeviceToken:deviceToken];
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
  [[NSNotificationCenter defaultCenter] postNotificationName:kJPFDidReceiveRemoteNotification object:userInfo];
}

- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification
{
  [[NSNotificationCenter defaultCenter] postNotificationName:kJPFDidReceiveRemoteNotification object: notification.userInfo];
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)   (UIBackgroundFetchResult))completionHandler
{
  [[NSNotificationCenter defaultCenter] postNotificationName:kJPFDidReceiveRemoteNotification object:userInfo];
}

- (void)jpushNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(NSInteger))completionHandler
{
  NSDictionary * userInfo = notification.request.content.userInfo;
  if ([notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
    [JPUSHService handleRemoteNotification:userInfo];
    [[NSNotificationCenter defaultCenter] postNotificationName:kJPFDidReceiveRemoteNotification object:userInfo];
  }

  completionHandler(UNNotificationPresentationOptionAlert);
}

- (void)jpushNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)())completionHandler
{
  NSDictionary * userInfo = response.notification.request.content.userInfo;
  if ([response.notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
    [JPUSHService handleRemoteNotification:userInfo];
    [[NSNotificationCenter defaultCenter] postNotificationName:kJPFOpenNotification object:userInfo];
  }

  completionHandler();
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  [JPUSHService setupWithOption:launchOptions appKey:@"da15aa3148c6cae5e6f33c76"
                        channel:nil apsForProduction:nil];

//  NSURL *jsCodeLocation;
//
//  [self beginLoaLoadingReactNative];
//  jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
//  jsCodeLocation = [NSURL URLWithString:[SSReactNativeManager sharedManager].jsLocation];
//  SSLog(@"===========%@",[SSReactNativeManager sharedManager].jsLocation);
//  jsCodeLocation = [NSURL URLWithString:@"http://192.168.11.173:8081/index.bundle?platform=ios&dev=false"];

//  RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
//                                                      moduleName:@"sunsoft_supplier"
//                                               initialProperties:nil
//                                                   launchOptions:launchOptions];


  
  

    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];

//    UIViewController *rootViewController = [UIViewController new];
//    rootViewController.view = rootView;
//    self.window.rootViewController = rootViewController;

//  [SSReactNativeManager sharedManager].launchOptions = launchOptions;
//
  SSLaunchController *LaunchController = [[SSLaunchController alloc] init];
  self.window.rootViewController = LaunchController;

  [self.window makeKeyAndVisible];
  
//  [SplashScreen show];
  return YES;
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
  //杀掉进程  审核可能被拒
  
  SSLog(@"程序关闭");
  UIBackgroundTaskIdentifier bgTask;
  bgTask = [application beginBackgroundTaskWithExpirationHandler:^{
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)([SSReactNativeManager sharedManager].exitTime * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
      exit(0);
    });
    
  }];
}



@end
