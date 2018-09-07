//
//  SSDownloadModel.h
//  sunsoft_supplier
//
//  Created by yzl on 2017/4/10.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SSDownloadModel : NSObject

/**
 下载地址
 */
@property (nonatomic, copy) NSString *url;

/**
 保存本地地址
 */
@property (nonatomic, copy) NSString *targetPath;
/**
 Bundle名称
 */
@property (nonatomic, copy) NSString *bundleName;
/**
 Bundle版本
 */
@property (nonatomic, copy) NSString *bundleCode;
/**
 校验文件是否相同参数
 */
@property (nonatomic, copy) NSString *hashCode;
/**
 0 全量  1 增量
 */
@property (nonatomic) BOOL isIncrement;


@end
