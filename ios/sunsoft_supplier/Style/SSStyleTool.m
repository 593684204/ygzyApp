//
//  SSStyleTool.m
//  sunsoft_supplier
//
//  Created by yzl on 2017/3/23.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSStyleTool.h"

@implementation SSStyleTool

+ (UIFont*)navTitleFont{
  static UIFont *font;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    font = [UIFont systemFontOfSize:18];
  });
  return font;
}

+ (UIColor*)navTitleColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#333333);
  });
  return color;
}
+ (UIColor*)blueColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#45b6b0);
  });
  return color;
}


+ (UIColor*)lightGrayColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#5e5a57);
  });
  return color;
}

+ (UIColor*)mainContentColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#333333);
  });
  return color;
}
+ (UIFont*)mainContentFont{
  static UIFont *font;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    font = [UIFont systemFontOfSize:16];
  });
  return font;
}

+ (UIColor*)placeHoderColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#afafaf);
  });
  return color;
}

+ (UIFont*)placeHoderFont{
  static UIFont *font;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    font = [UIFont systemFontOfSize:12];
  });
  return font;
}

+ (UIColor*)backgroundGrayColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#f0eff5);
  });
  return color;
}

+ (CGFloat)getRelativeHeight:(CGFloat)height{
  return kScreenHeight/AppDesignHeight*height;
}
+ (CGFloat)getRelativeHeightOnlyi5:(CGFloat)height{
  if (kScreenWidth <= 320) {
    return kScreenHeight/AppDesignHeight*height;
  }
  return height;
}
+ (CGFloat)getRelativeWidth:(CGFloat)width{
  return kScreenWidth/AppDesignWidth*width;
}

+ (UIColor*)updateColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#636363);
  });
  return color;
}
+ (UIColor*)lineColor{
  static UIColor *color;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    color = UIColorHex(#d4d4d4);
  });
  return color;
}
@end
