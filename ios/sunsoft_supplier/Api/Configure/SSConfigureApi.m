//
//  SSConfigureApi.m
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/24.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSConfigureApi.h"
#import "SSConfigureServerModel.h"

@implementation SSConfigureApi


+ (void)downloadBundleWithOption:(SSDownloadModel *)downloadModel progress:(DownloadProgress)progress completion:(CompletionHandler)completion{
//  NSString *url = [NSString stringWithFormat:@"https://dldir1.qq.com/qqfile/qq/QQ9.0.3/23729/QQ9.0.3.exe"];
  
  [SSNetworkManager DownloadFile:downloadModel.url progress:progress destination:^NSURL *(NSURL *targetPath, NSURLResponse *response) {
    return [NSURL fileURLWithPath:downloadModel.targetPath];
  } completionHandler:completion name:NSStringFromSelector(_cmd)];
}


@end
