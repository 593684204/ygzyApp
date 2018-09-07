//
//  SSCustomProgressView.m
//  sunsoft_supplier
//
//  Created by 田司 on 2017/4/26.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "SSCustomProgressView.h"

@implementation SSCustomProgressView
-(void)setGgTrackImage:(UIImage *)ggTrackImage
{
  _ggTrackImage=ggTrackImage;
  
  UIImageView *trackImageView=self.subviews.firstObject;
  
  CGRect trackProgressFrame=trackImageView.frame;
  
  trackProgressFrame.size.height=self.frame.size.height;
  
  trackImageView.frame=trackProgressFrame;
  
  CGFloat width = _ggTrackImage.size.width;
  
  CGFloat height = _ggTrackImage.size.height;
  
  UIImage *imgTrack = [_ggTrackImage resizableImageWithCapInsets:UIEdgeInsetsMake(height, width, height, 0) resizingMode:UIImageResizingModeStretch];
  
  trackImageView.image=imgTrack;
}

-(void)setGgProgressImage:(UIImage *)ggProgressImage

{
  _ggProgressImage = ggProgressImage;
  
  CGFloat width = _ggTrackImage.size.width;
  
  CGFloat height = _ggProgressImage.size.height;
  
  UIImageView *progressImageView = self.subviews.lastObject;
  
  CGRect ProgressFrame = progressImageView.frame;
  
  ProgressFrame.size.height = self.frame.size.height;
  
  progressImageView.frame = ProgressFrame;
  
  UIImage *imgProgress = [_ggProgressImage resizableImageWithCapInsets:UIEdgeInsetsMake(height, width, height, 0) resizingMode:UIImageResizingModeStretch];
  
  progressImageView.image = imgProgress;
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
