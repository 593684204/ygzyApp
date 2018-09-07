//
//  SSNetworkTool.m
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/23.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSNetworkManager.h"

@implementation SSNetworkManager

+ (instancetype)sharedManager{
  static SSNetworkManager *mgr;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    mgr = [SSNetworkManager new];
  });
  return mgr;
}

+ (void)GETWithURL:(NSString *)url params:(NSDictionary *)params success:(Success)success failure:(Failure)failure{
  
  // 1.初始化单例类
  AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
  //  manager.securityPolicy.SSLPinningMode = AFSSLPinningModeCertificate;
  // 2.设置证书模式
//  NSString * cerPath = [[NSBundle mainBundle] pathForResource:@"ygzy" ofType:@"cer"];
  NSData * cerData = [[NSData alloc] initWithBase64EncodedString:SSHttpsCerBase64Content options:0];
  manager.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeCertificate withPinnedCertificates:[[NSSet alloc] initWithObjects:cerData, nil]];
  // 客户端是否信任非法证书
  manager.securityPolicy.allowInvalidCertificates = YES;
  // 是否在证书域字段中验证域名
  [manager.securityPolicy setValidatesDomainName:NO];
  
  manager.requestSerializer.timeoutInterval = NetworkTimeOutIntervel;
  [manager GET:url parameters:params progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    success(responseObject,task);
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}

+ (void)POSTWithURL:(NSString *)url params:(id)params success:(Success)success failure:(Failure)failure name:(NSString *)name{
  // 1.初始化单例类
  AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
  //  manager.securityPolicy.SSLPinningMode = AFSSLPinningModeCertificate;
  // 2.设置证书模式
//  NSString * cerPath = [[NSBundle mainBundle] pathForResource:@"ygzy" ofType:@"cer"];
  NSData * cerData = [[NSData alloc] initWithBase64EncodedString:SSHttpsCerBase64Content options:0];
  manager.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeCertificate withPinnedCertificates:[[NSSet alloc] initWithObjects:cerData, nil]];
  // 客户端是否信任非法证书
  manager.securityPolicy.allowInvalidCertificates = YES;
  // 是否在证书域字段中验证域名
  [manager.securityPolicy setValidatesDomainName:NO];
  
  manager.requestSerializer.timeoutInterval = NetworkTimeOutIntervel;
  
  NSURLSessionDataTask *task = [manager POST:url parameters:params progress:nil success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    success(responseObject,task);
    [[SSNetworkManager sharedManager].keyForTask removeObjectForKey:name];
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    [[SSNetworkManager sharedManager].keyForTask removeObjectForKey:name];
    failure(error);
    
    
  }];
  [SSNetworkManager sharedManager].keyForTask[name] = task;
}

+ (void)ImagePOSTWithURL:(NSString *)url andData:(NSData*)data params:(id)params success:(Success)success failure:(Failure)failure{
  // 1.初始化单例类
  AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
  NSData * cerData = [[NSData alloc] initWithBase64EncodedString:SSHttpsCerBase64Content options:0];
  manager.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeCertificate withPinnedCertificates:[[NSSet alloc] initWithObjects:cerData, nil]];
  // 客户端是否信任非法证书
  manager.securityPolicy.allowInvalidCertificates = YES;
  // 是否在证书域字段中验证域名
  [manager.securityPolicy setValidatesDomainName:NO];
  manager.requestSerializer = [AFHTTPRequestSerializer serializer];
  manager.requestSerializer.timeoutInterval = NetworkTimeOutIntervel;
  manager.responseSerializer = [AFImageResponseSerializer serializer];
  
  [manager POST:url parameters:nil constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
    
    NSData *tokenData = [params[@"token"] dataUsingEncoding:NSUTF8StringEncoding];
    NSData *typeData = [params[@"versionType"] dataUsingEncoding:NSUTF8StringEncoding];
    NSData *versionData = [params[@"versionCode"] dataUsingEncoding:NSUTF8StringEncoding];
    NSData *bundleData = [params[@"bundleCode"] dataUsingEncoding:NSUTF8StringEncoding];
    //这个就是参数
    [formData appendPartWithFileData:data name:@"fileDate" fileName:@"fileDate.jpeg" mimeType:@"image/jpeg"];
    [formData appendPartWithFormData:tokenData name:@"token" ];
    [formData appendPartWithFormData:typeData name:@"versionType" ];
    [formData appendPartWithFormData:versionData name:@"versionCode"];
    [formData appendPartWithFormData:bundleData name:@"bundleCode"];

  } progress:^(NSProgress * _Nonnull uploadProgress) {
    
    //打印下上传进度
  } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    
    //请求成功
      success(responseObject,task);
    
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    
    //请求失败
    failure(error);
   
  }];
}

+ (void)ImagePOSTWithURL:(NSString *)url params:(id)params success:(Success)success failure:(Failure)failure{
  // 1.初始化单例类
  AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
  NSData * cerData = [[NSData alloc] initWithBase64EncodedString:SSHttpsCerBase64Content options:0];
  manager.securityPolicy = [AFSecurityPolicy policyWithPinningMode:AFSSLPinningModeCertificate withPinnedCertificates:[[NSSet alloc] initWithObjects:cerData, nil]];
  // 客户端是否信任非法证书
  manager.securityPolicy.allowInvalidCertificates = YES;
  // 是否在证书域字段中验证域名
  [manager.securityPolicy setValidatesDomainName:NO];
  manager.requestSerializer = [AFHTTPRequestSerializer serializer];
  manager.requestSerializer.timeoutInterval = NetworkTimeOutIntervel;
  manager.responseSerializer = [AFImageResponseSerializer serializer];
  
  [manager POST:url parameters:params  success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
    success(responseObject,task);
  } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
    failure(error);
  }];
}


+ (void)DownloadFile:(NSString *)url progress:(DownloadProgress)progress destination:(Destination)destination completionHandler:(CompletionHandler)completion name:(NSString*)name{
  
  
  AFHTTPSessionManager *manager = [AFHTTPSessionManager manager];
  NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url]];
  NSURLSessionDownloadTask *downloadTask =  [manager downloadTaskWithRequest:request progress:progress destination:destination completionHandler:^(NSURLResponse * _Nonnull response, NSURL * _Nullable filePath, NSError * _Nullable error) {
    [[SSNetworkManager sharedManager].keyForTask removeObjectForKey:name];
    completion(response,filePath,error);
  }];
  [downloadTask resume];
  [SSNetworkManager sharedManager].keyForTask[name] = downloadTask;
}

- (void)startMonitoring{
  [[AFNetworkReachabilityManager sharedManager] startMonitoring];
}

- (void)stopMonitoring{
  [[AFNetworkReachabilityManager sharedManager] stopMonitoring];
}

- (void)removeTaskForName:(NSString *)name{
  NSURLSessionDataTask *task = self.keyForTask[name];
  if (task) {
    [task cancel];
  }
  [self.keyForTask removeObjectForKey:name];
}

- (NSMutableDictionary*)keyForTask{
  if (!_keyForTask) {
    _keyForTask = [NSMutableDictionary dictionary];
  }
  return _keyForTask;
}

@end
