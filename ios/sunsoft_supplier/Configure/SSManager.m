//
//  SSManager.m
//  sunsoft_supplier
//
//  Created by 田司 on 17/3/30.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSManager.h"
//#import "SSAccValTimeModel.h"
#import <ifaddrs.h>
#import <arpa/inet.h>
#import <CommonCrypto/CommonDigest.h>

#define TargetCoutDownTime 60

@interface SSManager ()

@property (nonatomic, strong) NSTimer *validationTimer;
@property (nonatomic, strong) NSMutableDictionary *phoneForValTime; ///<绑定手机验证码数据dic
@property (nonatomic, strong) NSTimer *bindValTime; ///<绑定手机定时器
@end

@implementation SSManager

{
  NSMutableDictionary *_accountForValidationTime;
}
+ (instancetype)sharedManager{
  static SSManager *mgr;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    mgr = [SSManager new];
  });
  return mgr;
}

/*
- (void)goToMsgDetail{
  if (!self.shouldGoToMsgDetail) {
    return;
  }
  UIViewController *ctr = [UIApplication sharedApplication].keyWindow.rootViewController;
//  if ([ctr isKindOfClass:[MainViewController class]]) {
//    MainViewController *mCtr = (MainViewController*)ctr;
//    [mCtr.mainTabBar emuTouch:2];
//    UINavigationController *nCtr = mCtr.viewControllers[2];
//    [nCtr popToRootViewControllerAnimated:NO];
//    SSMessageDetailController *msgCtr = [SSMessageDetailController new];
//    msgCtr.hidesBottomBarWhenPushed = YES;
//    SSMessageModell *messageModel = [SSMessageModell new];
//    //  //  messageModel.schoolId = userInfo[@"extras"][@"schoolId"];
//    //  //  messageModel.typeNo = userInfo[@"extras"][@"typeNo"];
//    NSUserDefaults *userDefaultes = [NSUserDefaults standardUserDefaults];
//    messageModel.schoolId = [userDefaultes objectForKey:@"schoolId"];
//    messageModel.typeNo = [userDefaultes objectForKey:@"typeNo"];
//    msgCtr.msg = messageModel;
//    [nCtr pushViewController:msgCtr animated:NO];
    self.shouldGoToMsgDetail = NO;
  }
}

- (BOOL)checkAccoutValide:(NSString *)account{
  SSAccValTimeModel *model = self.accountForValidationTime[account];
  if (model) {
    if (model.failureTime > 120) {
      return YES;
    }
  }
  [self.accountForValidationTime removeObjectForKey:account];
  return NO;
}

- (SSAccValTimeModel*)addAccount:(NSString *)account{
  SSAccValTimeModel *model = [SSAccValTimeModel new];
  model.account = account;
  model.failureTime = 180;
  model.countdownTime = 60;
  model.flag = @"true";
  self.accountForValidationTime[account] = model;
  if(!self.validationTimer.isValid){
    [self.validationTimer fire];
  }
  return model;
}

- (void)resetAccountCountDownTime:(NSString *)account{
  SSAccValTimeModel *model = self.accountForValidationTime[account];
  if (model) {
    model.countdownTime = 60;
  }
}

- (void)resetFailTime:(NSString *)account{
  SSAccValTimeModel *model = self.accountForValidationTime[account];
  if (model) {
    model.countdownTime = 180;
  }
}

- (void)dealWithAccoutValidationTime{
//  NSMutableArray *keys = [NSMutableArray array];
  for (NSString *key in self.accountForValidationTime) {
    SSAccValTimeModel *model = self.accountForValidationTime[key];
    model.failureTime -= .1;
    model.countdownTime -= .1;
//    if (model.failureTime < 0) {
//      [keys addObject:key];
//      continue;
//    }
   
    NSString *timeStr = [NSString stringWithFormat:@"%.1f",model.countdownTime];
    float timeFloat = [NSNumber numberWithString:timeStr].floatValue;
    model.countdownTime = timeFloat;
    if (timeFloat == (NSInteger)timeFloat && timeFloat >= 0) {
      if (self.delegate && [self.delegate respondsToSelector:@selector(onTimerCountDown:)]) {
        [self.delegate onTimerCountDown:model];
      }
    }
  }
//  [self.accountForValidationTime removeObjectsForKeys:keys];
}

- (void)resetValidationAll{
  [self.accountForValidationTime removeAllObjects];
}

#pragma mark - 绑定手机验证码

- (BOOL)checkPhoneValide:(NSString *)phone{
  SSAccValTimeModel *model = self.phoneForValTime[phone];
  if (model) {
    if (model.countdownTime > 0) {
      return YES;
    }
    else{
      return NO;
    }
  }
  [self addPhone:phone];
  return NO;
}

- (SSAccValTimeModel*)addPhone:(NSString *)phone{
  SSAccValTimeModel *model = [SSAccValTimeModel new];
  model.account = phone;
  model.failureTime = 180;
  model.countdownTime = 0;
  model.flag = @"true";
  self.phoneForValTime[phone] = model;
  if(!self.bindValTime.isValid){
    [self.bindValTime fire];
  }
  return model;
}

- (void)resetPhoneCountDownTime:(NSString *)phone{
  SSAccValTimeModel *model = self.phoneForValTime[phone];
  if (model) {
    model.countdownTime = TargetCoutDownTime;
  }
}

- (NSString*)getFlagForPhone:(NSString *)phone{
  SSAccValTimeModel *model = self.phoneForValTime[phone];
  if (model) {
    return model.flag;
  }
  else{
    return @"true";
  }
}

- (SSAccValTimeModel*)getTimeModelForPhone:(NSString *)phone{
  return self.phoneForValTime[phone];
}

- (void)setFlag:(NSString *)flag forPhone:(NSString *)phone{
  SSAccValTimeModel *model = self.phoneForValTime[phone];
  if (model) {
    model.flag = flag;
  }
}

- (void)dealWithPhoneValidationTime{
  //  NSMutableArray *keys = [NSMutableArray array];
  for (NSString *key in self.phoneForValTime) {
    SSAccValTimeModel *model = self.phoneForValTime[key];
    model.countdownTime -= .1;
    
    NSString *timeStr = [NSString stringWithFormat:@"%.1f",model.countdownTime];
    float timeFloat = [NSNumber numberWithString:timeStr].floatValue;
    model.countdownTime = timeFloat;
    if (timeFloat == (NSInteger)timeFloat && timeFloat >= 0) {
      if (self.delegate && [self.delegate respondsToSelector:@selector(onTimerCountDown:)]) {
        [self.delegate onTimerCountDown:model];
      }
    }
  }
}

- (void)resetPhoneValidationAll{
  [self.phoneForValTime removeAllObjects];
  [self.validationTimer invalidate];
  self.validationTimer = nil;
}

#pragma mark - lazy load

- (NSMutableDictionary*)accountForValidationTime{
  if (!_accountForValidationTime) {
    _accountForValidationTime = [NSMutableDictionary dictionary];
  }
  return _accountForValidationTime;
}

- (NSMutableDictionary*)phoneForValTime{
  if(!_phoneForValTime){
    _phoneForValTime = [NSMutableDictionary dictionary];
  }
  return _phoneForValTime;
}

- (NSTimer*)validationTimer{
  if (!_validationTimer) {
    _validationTimer = [NSTimer scheduledTimerWithTimeInterval:.1 target:self selector:@selector(dealWithAccoutValidationTime) userInfo:nil repeats:YES];
    [[NSRunLoop currentRunLoop] addTimer:_validationTimer forMode:NSRunLoopCommonModes];
    
  }
  return _validationTimer;
}

- (NSTimer*)bindValTime{
  if (!_bindValTime) {
    _bindValTime = [NSTimer scheduledTimerWithTimeInterval:.1 target:self selector:@selector(dealWithPhoneValidationTime) userInfo:nil repeats:YES];
    [[NSRunLoop currentRunLoop] addTimer:_bindValTime forMode:NSRunLoopCommonModes];
  }
  return _validationTimer;
}

#pragma mark -------============== 字符串----------
//判断字符串是否为空
+ (BOOL)isBlankString:(NSString *)str{
  
  if ([str isKindOfClass:[NSNull class]]) {
    
    return YES;
    
  }
  
  if (str == nil || str == NULL) {
    
    return YES;
  }
  
  if ([[str stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]] length]==0) {
    
    return YES;
    
  }
  
  return NO;
  
}

- (void)dealWithAppStoreUpdate{
  NSString *const key = @"versionKey";
  NSString *version = [[NSUserDefaults standardUserDefaults] stringForKey:key];
//  NSString *curVersion = [NSBundle mainBundle].infoDictionary[@"CFBundleShortVersionString"];
  NSString *curVersion = VERSIONCODE;
  if (![version isEqualToString:curVersion]) {
    [[SSAccountManager sharedManager] saveBundleCode:BUNDLECODE];
    NSString *document = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    NSString *path = [document stringByAppendingPathComponent:@"sunsoft"];
    if([[NSFileManager defaultManager] fileExistsAtPath:path isDirectory:NULL]){
      [[NSFileManager defaultManager] removeItemAtPath:path error:NULL];
    }
//    if ([[SSAccountManager sharedManager] getBundleName].length!=0) {
//      NSString *document = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
//      NSString *path = [document stringByAppendingPathComponent:@"sunsoft"];
//      NSString *dir = [path stringByAppendingPathComponent:[[SSAccountManager sharedManager] getBundleName]];
//      [[NSFileManager defaultManager] removeItemAtPath:dir error:NULL];
//    }
    [[SSAccountManager sharedManager] saveBundleName:@""];
  }
  [[NSUserDefaults standardUserDefaults] setObject:curVersion forKey:key];
  [[NSUserDefaults standardUserDefaults] synchronize];
}

//判断是否是null 如果是空返回@""
+(NSString *)stringNullDealWith:(NSString *)str
{
  if ([str isKindOfClass:[NSNull class]]) {
    return @"";
  }
  if (str == nil || str == NULL) {
    return @"";
  }
  return [NSString stringWithFormat:@"%@",str];
}

//自适应高度
+(float)heightUpdateWithStr:(NSString *)str andFont:(float)fontSize andWidth:(float)Width{
  //设置字体类型
  UIFont *font = [UIFont systemFontOfSize:fontSize];
  //注意label的font还是要设置的，下面字典里用到font时用其来制定size，而不是对label设置font
  NSDictionary *dic = [NSDictionary dictionaryWithObjectsAndKeys:font,NSFontAttributeName,nil];
  //根据内容自适应size
  //传入的参数分别为：label矩形的的尺寸界线、文本绘制时的附加选项、文字属性、 文本绘制时的附加选项，包括一些信息，例如如何调整字间距以及缩放，该对象包含的信息将用于文本绘制，该参数可为nil
  CGSize stringSize = [str boundingRectWithSize:CGSizeMake(Width,2000)
                                        options:NSStringDrawingUsesLineFragmentOrigin
                                     attributes:dic
                                        context:nil].size;
  UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, Width, 20)];
  label.numberOfLines = 0;
  label.text = str;
  label.lineBreakMode = NSLineBreakByWordWrapping;
  //    CGSize size = [label sizeThatFits:CGSizeMake(label.frame.size.width, MAXFLOAT)];
  label.frame = CGRectMake(label.frame.origin.x, label.frame.origin.y, label.frame.size.width, stringSize.height);
  return label.frame.size.height;
}
+ (NSString *)HTMLBR:(NSString *)html{
  
  //    ;
  html = [html stringByReplacingOccurrencesOfString:@"\n" withString:@"<br>"];
  NSScanner *theScanner;
  NSString *text = nil;
  
  theScanner = [NSScanner scannerWithString:html];
  
  while ([theScanner isAtEnd] == NO) {
    // find start of tag
    [theScanner scanUpToString:@"<" intoString:NULL] ;
    //        [theScanner scanUpToString:@"</div>" intoString:&text];
    // find end of tag
    [theScanner scanUpToString:@">" intoString:&text] ;
    // replace the found tag with a space
    //(you can filter multi-spaces out later if you wish)
    html = [html stringByReplacingOccurrencesOfString:
            [NSString stringWithFormat:@"%@>", text]
                                           withString:@""];
  } // while //
  return html;
}
 
**/


//对bundle文件进行md5加密
+(NSString*)fileMD5:(NSString*)path
{
  NSFileHandle *handle = [NSFileHandle fileHandleForReadingAtPath:path];
  if( handle== nil ) return @"ERROR GETTING FILE MD5"; // file didnt exist
  
  CC_MD5_CTX md5;
  
  CC_MD5_Init(&md5);
  
  BOOL done = NO;
  while(!done)
  {
    // NSData* fileData = [handle readDataOfLength: CHUNK_SIZE ];
    NSData* fileData = [handle readDataOfLength: 32 ];
    CC_MD5_Update(&md5, [fileData bytes], [fileData length]);
    if( [fileData length] == 0 ) done = YES;
  }
  unsigned char digest[CC_MD5_DIGEST_LENGTH];
  CC_MD5_Final(digest, &md5);
  NSString* s = [NSString stringWithFormat: @"%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x",
                 digest[0], digest[1],
                 digest[2], digest[3],
                 digest[4], digest[5],
                 digest[6], digest[7],
                 digest[8], digest[9],
                 digest[10], digest[11],
                 digest[12], digest[13],
                 digest[14], digest[15]];
  return s;
}
+(float)imageHeightWithImge:(UIImage *)image{
  if (image.size.width != 0) {
    return (kScreenWidth)*image.size.height/image.size.width ;
  }else{
    return 0;
  }
}
@end
