//
//  ProgressViewController.m
//  sunsoft_supplier
//
//  Created by ShawnWang on 2018/5/11.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "ProgressViewController.h"
#import "SSCustomProgressView.h"

@interface ProgressViewController ()

@property (nonatomic, strong) UIImageView *progressBackground;
@property (nonatomic, strong) SSCustomProgressView *progressView;
@property (nonatomic, strong) UILabel *percentLabel;
@property (nonatomic, strong) NSTimer *time;
@property (nonatomic, assign) double ssTime;


@end

@implementation ProgressViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  self.view.backgroundColor = [UIColor whiteColor];
  
  [self setProgress];
  
  
  
  
}

- (void)setProgress {
  
  UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"launch_image"]];
  imageView.frame = self.view.bounds;
  [self.view addSubview:imageView];
  
  self.progressBackground = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"progress_background"]];
  self.progressBackground.hidden = NO;
  [self.view addSubview:self.progressBackground];
  
  
  self.progressView = [[SSCustomProgressView alloc] initWithProgressViewStyle:UIProgressViewStyleDefault];
  self.progressView.ggProgressImage = [UIImage imageNamed:@"progress_track"];
  self.progressView.ggTrackImage = [UIImage imageNamed:@"progress_background"];
  self.progressView.progress = 0;
  
  self.time = [NSTimer timerWithTimeInterval:0.1 target:self selector:@selector(timeAction) userInfo:nil repeats:YES];
  [[NSRunLoop mainRunLoop] addTimer:self.time forMode:NSDefaultRunLoopMode];
//  self.time = [NSTimer scheduledTimerWithTimeInterval:self.ssTime target:self selector:@selector(timeAction:) userInfo:nil repeats:NO];
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    [self.time invalidate];
  });
  
  self.progressView.trackTintColor = [UIColor clearColor];
  [self.progressBackground addSubview:self.progressView];
  
  self.percentLabel = [UILabel new];
  self.percentLabel.font = [UIFont systemFontOfSize:10];
  self.percentLabel.textColor = UIColorHex(9b9b9b);
  self.percentLabel.text = @"0%";
  self.percentLabel.hidden = YES;
  [self.view addSubview:self.percentLabel];
  
  [self.progressBackground mas_makeConstraints:^(MASConstraintMaker *make) {
    make.size.mas_equalTo(CGSizeMake(170, 10.5));
    make.centerX.mas_equalTo(self.view.mas_centerX);
    make.top.mas_equalTo(958/2);
  }];
  
  [self.progressView mas_makeConstraints:^(MASConstraintMaker *make) {
    make.size.mas_equalTo(CGSizeMake(167, 7));
    make.centerX.mas_equalTo(self.progressBackground.mas_centerX);
    make.centerY.mas_equalTo(self.progressBackground.mas_centerY);
  }];
  
  [self.percentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
    make.bottom.mas_equalTo(self.progressBackground.mas_top).offset(-10);
    make.centerX.mas_equalTo(self.view.mas_centerX);
    make.height.mas_equalTo(10);
    make.width.mas_greaterThanOrEqualTo(10);
  }];
  
}

- (void)timeAction {
  if (self.ssTime < 3) {
    self.ssTime = self.ssTime + 0.05;
    self.progressView.progress = self.ssTime;
  }
  
}




- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
