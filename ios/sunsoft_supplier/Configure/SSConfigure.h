//
//  SSConfigure.h
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/24.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SSConfigureServerModel.h"

#define BaseURL @"https://ssl.ygzykj.com:2393/sunsoft-supplier-app/"//测试

#define RootURL [[SSAccountManager sharedManager] getServerUrl]
//版本号 

#define VERSIONCODE  @"1.1.4"
//bundle版本号
#define BUNDLECODE   @"1.0"
//#define BUNDLECODE   @"4.5"

//#endif

#define TOKEN   [[SSAccountManager sharedManager] getToken]       //token
#define USERID   [[SSAccountManager sharedManager] getUserId]     //用户id
#define USERNAME  [[SSAccountManager sharedManager] getUserName]  //用户名

//接口路径
//
//#pragma mark     =================登陆前的接口路径=====================
//
//#define LoginPath @"/login/login.json"
//#define VerifyCodePath @"/login/validatecode.json"
////忘记密码
//#define UserNameIsTruePath @"user/getUserDetailPassword.json"//获得用户信息（忘记密码 第一步调用）
//#define TestCodePath @"send/getSendMsg.json"//获取短信验证码
//#define JudgeMsgPath @"send/getJudgeSendMsg.json"//判断验证码正确性
//#define SetPwdPath @"user/resetPassword.json"//忘记密码-修改密码
//
//
//#define ServerUrlPath @"sunVersion/checkVersion.json"//获取服务器地址
//#define BundleUrlPath @"sunVersion/bundleVersionUrl.json"//获取bundle下载地址
//
//#pragma mark     =================个人信息的接口路径=====================
//
//#define UserMsgPath @"user/selectUsersBySupplierName.json"//获取用户信息及厂商名称
//#define LogOutPath @"login/exitlogin.json"//退出登录
//#define GetSupplierMsgPath @"appSupplier/selectSupplierDetails.json"//获取厂商信息
//#define EditHeadPortraidPath @"user/updateUserHeadPortrait.json"//修改用户头像
//#define EditRealNamePath @"user/updateUserName.json"//修改真实姓名
//#define EditPhonePath @"user/updateMobilePhone.json"//修改用户手机号
//#define CheckOldPwdPath @"user/checkOldPasswordCorrect.json"//修改密码-校验旧密码是否正确
//#define EditPwdPath @"user/updatePassword.json"//修改密码-修改密码
//#define CustomerServicePath @"/user/getServiceTel.json" //获取客服电话
//
//#pragma mark     =================消息的接口路径=====================
//#define RegionPath @"supplierRegion/selectSupplierRegion1_1_3.json"//获取省市区
//#define MessageTypePath @"supplierInformationType/getInformationTypeList.json" //获取消息筛选条件
//#define MessageListPath @"supplierInformation/getInformation.json"//获取消息列表
//#define DeleteInformationPath @"supplierInformation/deleteInformation.json"//删除学校消息
//#define DeleteInCountPath @"supplierInformation/deleteInCount.json"//查询删除数量
//#define MessageDetailPath @"supplierInformation/selectInformationList.json" ///<消息详情路径
//#define MessageDetailDeletePath @"supplierInformation/deleteByPrimaryKeyList.json" ///<消息详情删除路径

#define NetworkTimeOutIntervel 10

extern NSString* const SSServerDataError; ///< 数据解析错误

//for pop up content text
extern NSString* const SSServerNotReachableOfPortrait;
extern NSString* const SSServerNotReachable;
//extern NSString* const SSNetConnectFailCanNotGoNextPage;
extern NSString* const SSLoginBadNetwork; //登录弱网
extern NSString* const SSLoginNoNetwork; //登录无网络
extern NSString* const SSLoginAccountNilText;
extern NSString* const SSLoginPWDNilText;
extern NSString* const SSLoginVerifyCodeNilText;

// 忘记密码提示语
extern NSString* const SSUserNameNilText;
extern NSString* const SSPWDTestCodeNilText;
extern NSString *const SSNewPWDNilText;
extern NSString *const SSSurePWDNilText;
extern NSString *const SSNewPWDAndSurePWDNotEqualText;
extern NSString *const SSPWDErrorText;
extern NSString *const SSPWDNoPhoneText;

//更改姓名
extern NSString *const SSModifyUserNameErrorText; ///<更改姓名格式错误
extern NSString *const SSModifyUserNameSuccessText; ///<更改姓名成功

//验证码
extern NSString* const SSValCodeNilText; ///<验证码为空提示

//手机
extern NSString* const SSPhoneErrorText; ///<手机号错误
extern NSString* const SSPhoneRepeatText; ///<手机号重复
extern NSString* const SSPhoneBindSuccessText; ///<绑定手机号成功

//cer base64 content
extern NSString *const SSHttpsCerBase64Content;

@interface SSConfigure : NSObject

/**
 目的根地址

 @return
 */
+ (NSString*)realRootURL;


/**
 设置根地址
 */
+ (void)setRealRootURL:(NSString*)url;

/**
 app信息

 @param model 
 */
+ (void)setLatestApp:(SSConfigureServerModel*)model;


/**
 获取APP信息

 @return 
 */
+ (SSConfigureServerModel*)serverModel;

@end
