//
//  SSAccountManager.h
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/24.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>


/**
 账号管理类
 */
@interface SSAccountManager : NSObject

+ (instancetype) sharedManager;

@property (nonatomic, copy) NSString *userName; ///< 用户名
@property (nonatomic, copy) NSString *userId;
@property (nonatomic, copy) NSString *password; ///< 密码
@property (nonatomic, copy) NSString *userRealName; ///< 名字
@property (nonatomic, copy) NSString *token; ///< token
@property (nonatomic, copy) NSString *undateNum; ///< 登录次数
@property (nonatomic, copy) NSString *headPortrait;
@property (nonatomic, copy) NSString *supplierName;
@property (nonatomic, copy) NSString *userType;
@property (nonatomic, copy) NSString *upPwsNum;
@property (nonatomic, copy) NSString *sex;
@property (nonatomic, copy) NSString *birthday;
@property (nonatomic, copy) NSString *mobilePhone;
@property (nonatomic, copy) NSString *serverUrl;
@property (nonatomic) BOOL shouldSkipLoginPage; ///< 是否应该跳过登陆页
@property (nonatomic) BOOL isLogin; ///< 登陆是否成功
- (void)setInfo:(NSDictionary*)info;


/**
 存储用户和偏好
 */
//存储用户信息
-(void)saveUserMessage;

//我的存储用户信息
-(void)saveMineMessage;
//存储用户信息
-(void)saveUserNameMessage;
-(NSString *)getPhone;

-(NSString *)getUpPwsNum;

-(NSString *)getUserId;

-(NSString *)getToken;

//删除用户UserMessage
-(void)deleteUserMessage;

-(NSString *)getUserName;

-(void)saveUserIsExit:(NSString *)isExit;

-(NSString *)isExit;

-(void)saveUrl;

-(NSString *)getServerUrl;

//获取用户supplierName
-(NSString *)getSupplierName;

//获取用户HeadPortrait
-(NSString *)getHeadPortrait;

//获取用户是否已经登陆
-(BOOL)isSkipLoginPage;

//存储用户bundle版本号
-(void)saveBundleCode:(NSString *)bundleCode;

//获取用户BundleCode
-(NSString *)getBundleCode;

//存储用户bundleName
-(void)saveBundleName:(NSString *)bundleName;

//获取用户bundleName
-(NSString *)getBundleName;

@end
