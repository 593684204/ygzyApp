//
//  SSRNActionModule.h
//  sunsoft_supplier
//
//  Created by 田司 on 17/4/19.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
typedef NS_ENUM(NSUInteger, SSRNActionType) {
  RNActionTypeTabBarHidden,         //tabBar隐藏 0
  RNActionTypeTabBarShow,           //tabBar显示 1
  RNActionTypeToast,                //toast 2
  RNActionTypeUpdate,             //强制更新 3
  RNActionTypeToLogout,              //退出登录 4
  RNActionTypeHideKeyboardTool,              //隐藏键盘tool完成 5
  RNActionTypeShowKeyboardTool, //显示键盘tool完成 6
};

@interface SSRNActionModule : NSObject

@property (nonatomic) SSRNActionType type;
@property (nonatomic, strong) id data;

+ (instancetype)moduleWith:(SSRNActionType)type andData:(id)data;




@end
