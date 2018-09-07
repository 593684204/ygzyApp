//
//  SSRNActionTool.h
//  sunsoft_supplier
//
//  Created by 田司 on 17/4/19.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
extern NSString  *RNActionNotification;
@class SSRNActionModule;
@protocol RNActionDelegate <NSObject>

- (void)onRNAction:(SSRNActionModule*)action;

@end

@interface SSRNActionTool : NSObject

+ (instancetype)sharedTool;

- (instancetype)init;

- (void)postAction:(NSString*)name data:(id)data;


@end
