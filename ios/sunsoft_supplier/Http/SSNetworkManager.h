//
//  SSNetworkTool.h
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/23.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void(^Success)(id response, NSURLSessionDataTask* task);
typedef void(^Failure)(NSError *error);
typedef void (^DownloadProgress)(NSProgress *downloadProgress);
typedef NSURL * (^Destination)(NSURL *targetPath, NSURLResponse *response);
typedef void(^CompletionHandler)(NSURLResponse *response, NSURL *filePath, NSError *error);

@interface SSNetworkManager : NSObject

@property (nonatomic, strong) NSMutableDictionary *keyForTask;

+ (instancetype)sharedManager;


/**
 Get请求

 @param url
 @param params 参数
 @param success 成功
 @param failure 失败
 */
+ (void)GETWithURL:(NSString*)url params:(NSDictionary*)params success:(Success)success failure:(Failure)failure;

+ (void)POSTWithURL:(NSString*)url params:(NSDictionary*)params success:(Success)success failure:(Failure)failure name:(NSString*)name;

+ (void)ImagePOSTWithURL:(NSString *)url andData:(NSData*)data params:(id)params success:(Success)success failure:(Failure)failure;

+ (void)ImagePOSTWithURL:(NSString *)url params:(id)params success:(Success)success failure:(Failure)failure;

+ (void)DownloadFile:(NSString*)url progress:(DownloadProgress)progress destination:(Destination)destination completionHandler:(CompletionHandler)completion name:(NSString*)name;

/**
 开始监测网络
 */
- (void)startMonitoring;


/**
 停止监测网络
 */
- (void)stopMonitoring;


- (void)removeTaskForName:(NSString*)name;

@end
