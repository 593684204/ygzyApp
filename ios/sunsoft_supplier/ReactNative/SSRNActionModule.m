
//
//  SSRNActionModule.m
//  sunsoft_supplier
//
//  Created by 田司 on 17/4/19.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSRNActionModule.h"

@implementation SSRNActionModule
+ (instancetype)moduleWith:(SSRNActionType)type andData:(id)data{
  SSRNActionModule *module = [SSRNActionModule new];
  module.type = type;
  module.data = data;
  return module;
}

@end
