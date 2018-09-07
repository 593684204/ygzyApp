//
//  SSConfigureServerModel.h
//  sunsoft_supplier
//
//  Created by 田司 on 17/4/12.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SSConfigureServerModel : NSObject
@property (nonatomic, copy) NSString *versionCode;  //版本号
@property (nonatomic, copy) NSString *url;          //更新地址
@property (nonatomic, copy) NSString *isUpdate;     //是否需要强制更新
@property (nonatomic, copy) NSString *serverUrl;    //服务器地址
@property (nonatomic, copy) NSString *content;      //更新内容
@end
