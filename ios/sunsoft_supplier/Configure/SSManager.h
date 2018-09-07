//
//  SSManager.h
//  sunsoft_supplier
//
//  Created by 田司 on 17/3/30.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class SSAccValTimeModel;
@protocol SSMangerDelegate <NSObject>

- (void)onTimerCountDown:(SSAccValTimeModel*)model;

@end

@interface SSManager : NSObject

@property (nonatomic, strong) NSMutableDictionary *secondDic;
@property (nonatomic) __block NSInteger second;
@property (nonatomic, strong, readonly) NSMutableDictionary *accountForValidationTime; //用户验证码时间
@property (nonatomic, weak) id<SSMangerDelegate> delegate;
@property (nonatomic) BOOL shouldGoToMsgDetail; ///<是否应该跳转到消息详情

+ (instancetype) sharedManager;

#pragma mark - 极光推送

- (void)goToMsgDetail;

#pragma mark - 忘记密码验证码处理

- (BOOL)checkAccoutValide:(NSString*)account;

- (SSAccValTimeModel*)addAccount:(NSString*)account;

- (void)resetAccountCountDownTime:(NSString*)account;

- (void)resetFailTime:(NSString*)account;

- (NSTimer*)validationTimer;

- (void)resetValidationAll; ///<清楚所有相关验证缓存

#pragma mark - 绑定手机验证码处理

- (BOOL)checkPhoneValide:(NSString*)phone;

- (NSString*)getFlagForPhone:(NSString*)phone;

- (void)resetPhoneCountDownTime:(NSString*)phone;

- (SSAccValTimeModel*)getTimeModelForPhone:(NSString*)phone;

- (void)setFlag:(NSString*)flag forPhone:(NSString*)phone;

- (void)resetPhoneValidationAll;

#pragma mark - appstore更新后数据更新

- (void)dealWithAppStoreUpdate;


#pragma mark -------============== 字符串处理----------

//判断字符串是否为空
+ (BOOL)isBlankString:(NSString *)str;

///判断是否是null 如果是空返回@""
+(NSString *)stringNullDealWith:(NSString *)str;

//自适应高度
+(float)heightUpdateWithStr:(NSString *)str andFont:(float)fontSize andWidth:(float)Width;

//br标签的处理
+ (NSString *)HTMLBR:(NSString *)html;

//对bundle文件进行md5加密
+(NSString*)fileMD5:(NSString*)path;

//图片自适应高度
+(float)imageHeightWithImge:(UIImage *)image;

@end
