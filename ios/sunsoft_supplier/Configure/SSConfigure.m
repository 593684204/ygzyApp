//
//  SSConfigure.m
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/24.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSConfigure.h"

NSString *const SSServerDataError = @"数据错误";

/**
 登录提示语
 
 @return
 */
NSString *const SSServerNotReachableOfPortrait = @"头像上传失败";
//NSString *const SSNetConnectFailCanNotGoNextPage = @"网络连接失败，不能跳转下个页面";
NSString *const SSServerNotReachable = @"网络连接失败";
NSString *const SSLoginBadNetwork = @"网络较差，登陆失败";
NSString *const SSLoginNoNetwork = @"网络不可用，登录失败";
NSString *const SSLoginAccountNilText = @"请输入用户名";
NSString *const SSLoginPWDNilText = @"请输入密码";
NSString *const SSLoginVerifyCodeNilText = @"请输入验证码";


/**
 忘记密码提示语
 
 @return
 */
NSString *const SSUserNameNilText = @"请输入用户名";
NSString *const SSPWDTestCodeNilText = @"请输入验证码";
NSString *const SSNewPWDNilText = @"新建密码为空，请输入新建密码";
NSString *const SSSurePWDNilText = @"确认密码为空，请输入确认密码";
NSString *const SSNewPWDAndSurePWDNotEqualText = @"确认密码与新建密码不一致，请重新输入确认密码";
NSString *const SSPWDErrorText = @"密码设置不符合规则，请重建密码";
NSString *const SSPWDNoPhoneText = @"未注册手机号，请联系厂商管理员";

//更改姓名
NSString *const SSModifyUserNameErrorText = @"姓名输入错误，请重新输入";
NSString *const SSModifyUserNameSuccessText = @"更改姓名成功";

//验证码
NSString *const SSValCodeNilText = @"请输入验证码";

//手机
NSString *const SSPhoneErrorText = @"手机号码输入错误，请重新输入";
NSString *const SSPhoneRepeatText = @"手机号码与绑定手机号码重复，请重新输入";
NSString *const SSPhoneBindSuccessText = @"绑定新手机号成功";

NSString *const SSHttpsCerBase64Content = @"MIIGBTCCBO2gAwIBAgIQAWLExabGyxfOsfwC88g8DTANBgkqhkiG9w0BAQsFADBEMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNR2VvVHJ1c3QgSW5jLjEdMBsGA1UEAxMUR2VvVHJ1c3QgU1NMIENBIC0gRzMwHhcNMTcxMDIzMDAwMDAwWhcNMTgxMjIyMjM1OTU5WjCBgzELMAkGA1UEBhMCQ04xDzANBgNVBAgMBuWMl+S6rDEPMA0GA1UEBwwG5YyX5LqsMScwJQYDVQQKDB7pmLPlhYnmmbrlm63np5HmioDmnInpmZDlhazlj7gxEjAQBgNVBAsMCeaKgOacr+mDqDEVMBMGA1UEAwwMKi55Z3p5a2ouY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6ZR6I3n5emTxkJVJQ9iHkqaVD5gn9aPSz/C2c4LUn0iHs4m7zjM1BaO/tnLx6HXb+phMPuFbVqy5b1F0lU1BgiE1htONIgJPWubcN+hzrnAtn+8jJCJkWUhuh+u8WFgJ2/rspJNf1mJoR8wbkhL6t44Cr+fya6Bkk/lCyBcFCWdRO4JZkY1j/S8KmCi1oJfoiQ4APVyKH/Epxv777BLlY6Hben7OF/3HaS3mQ4N/AVXm93T445u9XfVVrjPNFBKVlOUiEjFWXVcoQZjMZyZguAGt2yz59+/wDViFDDFdxAO6mYYusoaVJMMAg6cIBtyWbCEWBpo1S18T4v887YeYuwIDAQABo4ICsTCCAq0wIwYDVR0RBBwwGoIMKi55Z3p5a2ouY29tggp5Z3p5a2ouY29tMAkGA1UdEwQCMAAwDgYDVR0PAQH/BAQDAgWgMCsGA1UdHwQkMCIwIKAeoByGGmh0dHA6Ly9nbi5zeW1jYi5jb20vZ24uY3JsMIGdBgNVHSAEgZUwgZIwgY8GBmeBDAECAjCBhDA/BggrBgEFBQcCARYzaHR0cHM6Ly93d3cuZ2VvdHJ1c3QuY29tL3Jlc291cmNlcy9yZXBvc2l0b3J5L2xlZ2FsMEEGCCsGAQUFBwICMDUMM2h0dHBzOi8vd3d3Lmdlb3RydXN0LmNvbS9yZXNvdXJjZXMvcmVwb3NpdG9yeS9sZWdhbDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHwYDVR0jBBgwFoAU0m/3lvSFP3I8MH0j2oV4m6N8WnwwVwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNodHRwOi8vZ24uc3ltY2QuY29tMCYGCCsGAQUFBzAChhpodHRwOi8vZ24uc3ltY2IuY29tL2duLmNydDCCAQMGCisGAQQB1nkCBAIEgfQEgfEA7wB2AN3rHSt6DU+mIIuBrYFocH4ujp0B1VyIjT0RxM227L7MAAABX0fH6AoAAAQDAEcwRQIgNK4DGEeebU+PylXAE0GM/pPbLbG7CU+xSDaxtlLDJUICIQCAlgdg3tSkq5sDJ0Mc3TMnt6xL7404+As7cDo+koj5twB1AKS5CZC0GFgUh7sTosxncAo8NZgE+RvfuON3zQ7IDdwQAAABX0fH6D0AAAQDAEYwRAIgBtqPA+sn1ZoFtPPemV/iv7h1Jqi+DR1BoqsmlyomWwgCIB7eLecgUEPmy056jJekyramaMHHuF+OmUMEUCMFcaAOMA0GCSqGSIb3DQEBCwUAA4IBAQDKFlV0juSmam93XtnyNtMS3Ml5stb8NluTumo+iGdw5TqPOE1kyyKmnLcylu5aefqdKuhcVCSlUBrHSO7ncJoz0pFnNc+Ej1aK1p5+Irce0+DKdXxTuEF0qt4Io4zfp7pl9VBixYSIqjorRpGpOfR71GraysN6UuJIkT6TQiQIfKOc+C9juVey9MFGfrNklzB1mwjS5z4JfWJraOeEX7NU4m+Acx8xf21cN0VN64+xNTGrl07Bp/6LxzMhVYWCS3F3T83OZws74Nvq7PKa5jcZnoflRZ9pYwqiAlqBCzOqpPnwDwgq4ioSpQDJSijzrT3xexpRvWEOXgTkJvmwkOuM";

static NSString *_realRootUrl;
static SSConfigureServerModel *_serverModel;

@implementation SSConfigure

+ (NSString*)realRootURL{
  return _realRootUrl;
}

+ (void)setRealRootURL:(NSString *)url{
  _realRootUrl = url;
}

+ (void)setLatestApp:(SSConfigureServerModel *)model{
  [self setRealRootURL:model.serverUrl];
  _serverModel = model;
}

+ (SSConfigureServerModel*)serverModel{
  return _serverModel;
}

@end
