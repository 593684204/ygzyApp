//
//  SSReactNativeManager.h
//  sunsoft_supplier
//
//  Created by ShawnWang on 2018/5/10.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridge.h>
#import "SSConfigureApi.h"
#import "DiffMatchPatch.h"

/**
 rn 处理delegate
 */
@protocol SSRNOperationDelegate <NSObject>

@optional

///**
// 开始解压文件
// */
//- (void)onRNBundleBeginUnpack;
//
///**
// 开始合并
// */
//- (void)onRNBundleBeginMerge;

/**
 下载进度
 @param progress
 */
- (void)onRNBundleDownloadProgress:(float)progress;

/**
 本地bundle操作完成
 */
- (void)onRNBundleUnpackOperationComplete;

/**
 全量更新bundle操作完成
 */
- (void)onRNBundleAllUpDateOperationComplete;

/**
 增量更新bundle操作完成
 */
- (void)onRNBundlediffUpDateOperationComplete;

/**
 bundle操作失败
 
 @param error
 */
- (void)onRNBundleOperationFailure:(NSError*)error;

@end


@interface SSReactNativeManager : NSObject

@property (nonatomic, weak) id<SSRNOperationDelegate> delegate;

/**
 根目录
 */
@property (nonatomic, copy) NSString *jsDirectory;  //  like xxx/sunsoft/

/**
 本地是否有解压文件
 */
@property (nonatomic) BOOL localFilePrepared; ///<本地bundle已经解压准备好了

/**
 解压文件路径
 */
@property (nonatomic, copy) NSString *jsLocation;

@property (nonatomic, strong) NSDictionary *launchOptions;

@property (nonatomic, strong) RCTBridge *bridge;

/**
 APP 重新启动时间
 */
@property (nonatomic, assign) NSInteger exitTime;

+ (instancetype) sharedManager;


/**
 开始加载包
 */
- (void)beginBundleOperation;

/**
 检查本地文件是否存在
 */
- (BOOL)checkLocalFileExists;

/**
 解压本地文件
 */
- (BOOL)archiveInAppBundle;

/**
 移动本地解压文件
 */
- (BOOL)moveInAppBundle;

/**
 下载压缩包
 */
- (void)downloadInAppBundle:(SSDownloadModel *)model;











@end
