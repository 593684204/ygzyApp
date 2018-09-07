//
//  SSRNActionTool.m
//  sunsoft_supplier
//
//  Created by 田司 on 17/4/19.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSRNActionTool.h"
#import "SSRNActionModule.h"
@interface SSRNActionTool ()

@property (nonatomic, strong) NSMutableDictionary *actions;

@end

NSString *RNActionNotification = @"rnactionnotification";

@implementation SSRNActionTool

+ (instancetype)sharedTool{
  static dispatch_once_t token;
  static SSRNActionTool *tool;
  dispatch_once(&token, ^{
    tool = [SSRNActionTool new];
  });
  
  return tool;
}

- (instancetype)init{
  self = [super init];
  if (self) {
    [self registerActionType];
  }
  return self;
}

- (void)registerActionType{
  NSString *path = [[NSBundle mainBundle] pathForResource:@"SSRNActions" ofType:@"plist"];
  self.actions = [NSMutableDictionary dictionaryWithContentsOfFile:path];
}

- (void)postAction:(NSString *)name data:(id)data{
  SSRNActionModule *module = [SSRNActionModule new];
  NSNumber *type = self.actions[name];
  if (type) {
    module.type = (SSRNActionType)[type intValue];
    module.data = data;
    [[NSNotificationCenter defaultCenter] postNotificationName:RNActionNotification object:module];
  }
  else{
    NSLog(@"RNActionTool:Can not find target action type,action:%@",name);
  }
}
@end
