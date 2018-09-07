//
//  SSStyleTool.h
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/23.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

#define RelativeHeight(height) [SSStyleTool getRelativeHeight:height]
#define RelativeWidth(width) [SSStyleTool getRelativeWidth:width]
#define RelativeHeightOnlyi5(height) [SSStyleTool getRelativeHeightOnlyi5:height]

#define AppDesignHeight 667//app设计尺寸高
#define AppDesignWidth 375//app设计尺寸宽度
#define KScreenScale [[UIScreen mainScreen] bounds].size.width/375

/**
 APP样式
 */
@interface SSStyleTool : NSObject


/**
 导航title字体

 @return
 */
+ (UIFont*)navTitleFont;


/**
 导航title颜色

 @return 
 */
+ (UIColor*)navTitleColor;

/**
 蓝色风格颜色(登录按钮背景)
 
 @return
 */
+ (UIColor*)blueColor;

/**
 字体灰色（验证码）
 
 @return
 */
+ (UIColor*)lightGrayColor;


/**
 主内容颜色

 @return
 */
+ (UIColor*)mainContentColor;


/**
 主内容字体

 @return 
 */
+ (UIFont*)mainContentFont;


/**
 placeholder颜色

 @return
 */
+ (UIColor*)placeHoderColor;


/**
 placeholder字体

 @return 
 */
+ (UIFont*)placeHoderFont;
/**
 背景灰色
 
 @return
 */
+ (UIColor*)backgroundGrayColor;


+ (CGFloat)getRelativeHeight:(CGFloat)height;

+ (CGFloat)getRelativeHeightOnlyi5:(CGFloat)height;

+ (CGFloat)getRelativeWidth:(CGFloat)width;

/**
 更新字体颜色
 
 @return
 */
+ (UIColor*)updateColor;
/**
 线的颜色
 
 @return
 */
+ (UIColor*)lineColor;

@end
