//
//  RNBridgeModule.m
//  sunsoft_supplier
//
//  Created by 田司 on 17/3/27.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "RNBridgeModule.h"
#import "React/RCTBridge.h"
#import "React/RCTEventDispatcher.h"
//#import "SSRNActionTool.h"
#import "SSRNActionModule.h"
//#import <UMMobClick/MobClick.h>
#import "SSReactNativeManager.h"
#import "SSDownloadModel.h"

@implementation RNBridgeModule

RCT_EXPORT_MODULE(RNBridgeModule)

//检测Bundle
RCT_EXPORT_METHOD(detectionBundle:(NSDictionary *)result)
{
  NSString *msgCode = [result objectForKey:@"msgCode"];
  
  [[NSNotificationCenter defaultCenter] postNotificationName:@"detectionBundle" object:msgCode];
}

//下载 bundle 更新
RCT_EXPORT_METHOD(downLoadBundle:(int)code withName:(NSDictionary *)name)
{
//  SSLog(@"RN调用OC-=-=-=%d\n==%@",code,name);
  SSDownloadModel *model = [SSDownloadModel yy_modelWithJSON:[name objectForKey:@"obj"][@"body"]];
  SSLog(@"%@",model.url);
  
  [[SSReactNativeManager sharedManager]downloadInAppBundle:model];
}

//关闭启动页
RCT_EXPORT_METHOD(downLaunch:(NSString *)name)
{
  [[NSNotificationCenter defaultCenter] postNotificationName:@"downLaunch" object:@"123"];
}
//打开启动页
RCT_EXPORT_METHOD(openLaunch:(NSString *)name)
{
  [[NSNotificationCenter defaultCenter] postNotificationName:@"openLaunch" object:@"123"];
}

//强制退出APP 时间
RCT_EXPORT_METHOD(exitApp:(NSString *)name)
{
  [SSReactNativeManager sharedManager].exitTime = 30;
}

////toast
//RCT_EXPORT_METHOD(toast:(NSString *)name)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:name];
//}
//
////hideTabHost
//RCT_EXPORT_METHOD(hideTabHost)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
////showTabHost
//RCT_EXPORT_METHOD(showTabHost)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
//
////logout
//RCT_EXPORT_METHOD(toLogin:(NSString*)name)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:name];
//}
//
////update
//RCT_EXPORT_METHOD(forceUpdateApk:(NSString*)url updateContentStr:(NSString*)content)
//{
//  NSMutableDictionary *dic = [NSMutableDictionary  dictionaryWithCapacity:0];
//  [dic setValue:url forKey:@"url"];
//  [dic setValue:content forKey:@"content"];
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:dic];
//}
//
////cancelMonitorEntityBack
//RCT_EXPORT_METHOD(cancelMonitorEntityBack)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
////monitorEntityBack
//RCT_EXPORT_METHOD(monitorEntityBack)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
//RCT_EXPORT_METHOD(reactViewInto:(NSString*)page)
//{
//  NSLog(@"友盟统计 RN页面:%@ 进入",page);
//  [MobClick beginLogPageView:page];
//}
//
//RCT_EXPORT_METHOD(reactViewExit:(NSString*)page)
//{
//  NSLog(@"友盟统计 RN页面:%@ 退出",page);
//  [MobClick endLogPageView:page];
//}
//
////monitorEntityBack
//RCT_EXPORT_METHOD(hideKeyboardTool)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
//RCT_EXPORT_METHOD(showKeyboardTool)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
////monitorEntityBack
//RCT_EXPORT_METHOD(RNActionTypeHideKeyboardTool)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}
//
//RCT_EXPORT_METHOD(RNActionTypeShowKeyboardTool)
//{
//  [[SSRNActionTool sharedTool] postAction:NSStringFromSelector(_cmd) data:nil];
//}

@end
