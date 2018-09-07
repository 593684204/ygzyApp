//
//  SSAccountManager.m
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/24.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSAccountManager.h"
#import <UICKeyChainStore.h>
#import <objc/objc.h>
#import <objc/runtime.h>
#define kSkipLoginPagePrefrence @"skiploginpage"
#define kAccountPrefrence @"username"
#define kPasswordPrefrence @"password"
#define kMobile @"mobile"
#define kUserId @"userId"
#define kToken @"token"
#define kServerUrl @"serverUrl"
#define kHeadPortrait @"headPortrait"
#define kSupplierName @"supplierName"
#define kBundleCode @"bundleCode"
#define kBundleName @"bundleName"

@implementation SSAccountManager

+ (instancetype)sharedManager{
  static SSAccountManager *mgr;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    mgr = [SSAccountManager new];
  });
  return mgr;
}

- (instancetype)init{
  if (self = [super init]) {
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    self.shouldSkipLoginPage = [defaults boolForKey:kSkipLoginPagePrefrence];
//    UICKeyChainStore *keychain = [UICKeyChainStore keyChainStoreWithService:[NSBundle mainBundle].infoDictionary[(NSString*)(kCFBundleIdentifierKey)]];
//    self.userName = keychain[kAccountPrefrence];
//    self.password = keychain[kPasswordPrefrence];
//    self.mobilePhone = keychain[kMobile] ;
//    self.userId = keychain[kUserId];
//    self.token = keychain[kToken];
  }
  return self;
}

- (void)setInfo:(NSDictionary *)info{
  unsigned int propertyCount = 0;
  objc_property_t *propertyList = class_copyPropertyList([self class], &propertyCount);
  for (unsigned int i = 0; i < propertyCount; i++ ) {
    objc_property_t *thisProperty = &propertyList[i];
    const char* propertyName = property_getName(*thisProperty);
    NSString *key = [NSString stringWithCString:propertyName encoding:NSUTF8StringEncoding];
    id value = info[key];
    if (value && ![value isKindOfClass:[NSNull class]]) {
      [self setValue:value forKey:key];
    }
  }
}

//存储服务器地址
-(void)saveUrl
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:self.serverUrl forKey:kServerUrl];
  [ud synchronize];
}
//获取服务器地址
-(NSString *)getServerUrl{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kServerUrl];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}
//存储用户信息
-(void)saveUserNameMessage
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:self.userId forKey:kUserId];
  [ud setObject:self.mobilePhone forKey:kMobile];
  [ud synchronize];
}
//登录存储用户信息
-(void)saveUserMessage
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:self.userName forKey:kAccountPrefrence];
  [ud setObject:self.userId forKey:kUserId];
  [ud setObject:self.mobilePhone forKey:kMobile];
  [ud setObject:self.token forKey:kToken];
  [ud setObject:self.upPwsNum forKey:@"upPwsNum"];
  [ud synchronize];
}

//我的存储用户信息
-(void)saveMineMessage
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:self.userName forKey:kAccountPrefrence];
  [ud setObject:self.userId forKey:kUserId];
  [ud setObject:self.mobilePhone forKey:kMobile];
  [ud setObject:self.headPortrait forKey:kHeadPortrait];
  [ud setObject:self.supplierName forKey:kSupplierName];
  [ud synchronize];
}
//获取用户upPwsNum
-(NSString *)getUpPwsNum
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:@"upPwsNum"];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}
//获取用户userName
-(NSString *)getUserName
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kAccountPrefrence];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}

//获取用户UserId
-(NSString *)getUserId
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kUserId];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}
//获取用户Phone
-(NSString *)getPhone{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kMobile];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}
//获取用户token
-(NSString *)getToken{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kToken];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}

//删除用户UserMessage
-(void)deleteUserMessage{
  [[NSUserDefaults standardUserDefaults] removeObjectForKey:kToken];
  [[NSUserDefaults standardUserDefaults] removeObjectForKey:kUserId];
  [[NSUserDefaults standardUserDefaults] removeObjectForKey:kMobile];
  [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"upPwsNum"];
  [[NSUserDefaults standardUserDefaults] synchronize];

}

//存储用户是否杀了进程  杀了进程就要重新发送验证码
-(void)saveUserIsExit:(NSString *)isExit
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:isExit forKey:@"exit"];
  [ud synchronize];
}

//用户是否杀了进程
-(NSString *)isExit{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:@"exit"];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}

//获取用户supplierName
-(NSString *)getSupplierName{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kSupplierName];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}

//获取用户HeadPortrait
-(NSString *)getHeadPortrait{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kHeadPortrait];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}
//获取用户是否已经登陆
-(BOOL)isSkipLoginPage{
  if ([[SSAccountManager sharedManager] getToken].length > 0) {
    return YES;
  }else{
    return NO;
  }
}

//存储用户bundle版本号
-(void)saveBundleCode:(NSString *)bundleCode
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:bundleCode forKey:kBundleCode];
  [ud synchronize];
}

//获取用户BundleCode
-(NSString *)getBundleCode{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kBundleCode];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}
//存储用户bundleName
-(void)saveBundleName:(NSString *)bundleName
{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  [ud setObject:bundleName forKey:kBundleName];
  [ud synchronize];
}

//获取用户bundleName
-(NSString *)getBundleName{
  NSUserDefaults *ud = [NSUserDefaults standardUserDefaults];
  NSString *value;
  value = [ud objectForKey:kBundleName];
  if (value == nil) {
    return @"";
  }else{
    return value;
  }
}

@end
