//
//  SSConfigureApi.h
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/24.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SSDownloadModel.h"
/**
 配置Api
 */

@interface SSConfigureApi : NSObject

/**
 下载bundle包

 @param downloadModel
 @param progress
 @param completion 
 */
+ (void)downloadBundleWithOption:(SSDownloadModel*)downloadModel
                     progress:(DownloadProgress)progress
                   completion:(CompletionHandler)completion;

@end
