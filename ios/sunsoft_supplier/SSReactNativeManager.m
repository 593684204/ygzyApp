//
//  SSReactNativeManager.m
//  sunsoft_supplier
//
//  Created by ShawnWang on 2018/5/10.
//  Copyright © 2018年 Facebook. All rights reserved.
//

#import "SSReactNativeManager.h"
#import <SSZipArchive.h>

//#define InAppArchiveTempBunldeDirName @"inapptemp"//临时解压路径
//#define TempBunldeDirName @"temp" //bundle下载临时文件夹
//#define TempFileName @"master.zip"

@interface SSReactNativeManager ()

@property (nonatomic, copy) NSString *jsRootDir;   // line xxx/sunsoft/sunsoft/
@property (nonatomic, copy) NSString *jsTempDirectory;//下载临时目录
@property (nonatomic, copy) NSString *jsInAppArchiveTempDir; ///<1.0包得解压临时目录
@property (nonatomic, copy) NSString *jsTempDownFile;//全量更新
@property (nonatomic, copy) NSString *jsBundleName;//BundleName
@property (nonatomic, copy) NSString *jsBundleCode;
@property (nonatomic, copy) NSString *diffTempDir;  ///<增量更新操作临时文件夹  like xxxx/sunsoft/.temp/
@property (nonatomic, copy) NSString *diffRootDir;  ///<增量更新解压后根目录
//@property (nonatomic, copy) NSString *newjsDirectory;


@property (nonatomic, strong) SSDownloadModel *downloadModel;


@end

@implementation SSReactNativeManager

+ (instancetype)sharedManager {
  static SSReactNativeManager *mgr;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    mgr = [SSReactNativeManager new];
  });
  return mgr;
}

- (BOOL)checkLocalFileExists {
  BOOL exists = [[NSFileManager defaultManager] fileExistsAtPath:self.jsDirectory];
  if (exists) {
    exists = [[NSFileManager defaultManager] fileExistsAtPath:self.jsLocation];
  }
  self.localFilePrepared = exists;
  return exists;
}

- (void)beginBundleOperation {
  if (![self checkLocalFileExists]) {
    //开始解压文件
      //解压sunsoft.zip文件
      BOOL success = [self archiveInAppBundle];
      if (success) {
        success = [self moveInAppBundle];
      }
      if (success) {
        dispatch_async(dispatch_get_main_queue(), ^{
          [self reportComplete];
        });
      }
    
  } else {
    [self reportComplete];
  }
  
}
//解压本地压缩包
- (BOOL)archiveInAppBundle {
  //获取路径
  NSString *filePath = [[NSBundle mainBundle] pathForResource:@"sunsoft" ofType:@"zip"];
  NSError *error;
  //创建目录
  BOOL success =[[NSFileManager defaultManager] createDirectoryAtPath:self.jsInAppArchiveTempDir withIntermediateDirectories:YES attributes:nil error:&error];
  if (!success) {
    [self reportError:error];
    return NO;
  }
  success = [SSZipArchive unzipFileAtPath:filePath toDestination:self.jsInAppArchiveTempDir overwrite:YES password:nil error:&error];
  if (!success) {
    [self reportError:error];
    return NO;
  }
  return YES;
}

- (BOOL)moveInAppBundle{
  NSError *error;
  BOOL success;
  if([[NSFileManager defaultManager] fileExistsAtPath:self.jsDirectory]){
    success = [[NSFileManager defaultManager] removeItemAtPath:self.jsDirectory error:&error];
    if (!success) {
      [self reportError:error];
      return NO;
    }
  }
  success = [[NSFileManager defaultManager] moveItemAtPath:self.jsInAppArchiveTempDir toPath:self.jsDirectory error:&error];
  if (!success) {
    [self reportError:error];
    return NO;
  }
  return YES;
}

- (void)reportError:(NSError*)error{
  //失败后，移除所有bundle相关文件
  [[NSFileManager defaultManager] removeItemAtPath:self.jsTempDirectory error:nil];
  [[NSFileManager defaultManager] removeItemAtPath:self.diffTempDir error:nil];
  
  dispatch_async(dispatch_get_main_queue(), ^{
    
    if (self.delegate && [self.delegate respondsToSelector:@selector(onRNBundleOperationFailure:)]) {
      [self.delegate onRNBundleOperationFailure:error];
    }
  });
}

//本地bundle操作完成
- (void)reportComplete{
  if (self.delegate && [self.delegate respondsToSelector:@selector(onRNBundleUnpackOperationComplete)]) {
    [self.delegate onRNBundleUnpackOperationComplete];
  }
}

//下载压缩包
- (void)downloadInAppBundle:(SSDownloadModel *)model {
  
  model.targetPath = self.jsTempDownFile;

  if(![[NSFileManager defaultManager] fileExistsAtPath:self.jsTempDirectory]){
    NSError *error;
    BOOL success =[[NSFileManager defaultManager] createDirectoryAtPath:self.jsTempDirectory withIntermediateDirectories:YES attributes:nil error:&error];
    if (!success) {
      [self reportError:error];
      return;
    }
  }
  
  [SSConfigureApi downloadBundleWithOption:model progress:^(NSProgress *downloadProgress) {
    //下载成功
    dispatch_async(dispatch_get_main_queue(), ^{
      if (self.delegate && [self.delegate respondsToSelector:@selector(onRNBundleDownloadProgress:)]) {
        long long progressNow = downloadProgress.completedUnitCount;
        long long progressTotal = downloadProgress.totalUnitCount;
        float scale = (float)progressNow / progressTotal*1.0;
        [self.delegate onRNBundleDownloadProgress:scale];
      }
    });
    
  } completion:^(NSURLResponse *response, NSURL *filePath, NSError *error) {
    if (error == NULL) {
      dispatch_async(dispatch_get_global_queue(0, 0), ^{
        
        if (!model.isIncrement) {
          [self allUpdate:model];
        } else{
          [self diffUpdate];
        }
        
      });
      
    } else{
      
      if (self.delegate && [self.delegate respondsToSelector:@selector(onRNBundleOperationFailure:)]) {
        [self.delegate onRNBundleOperationFailure:error];
      }
      
    }
    
  }];
  
}

//-(void)saveBundleMessage{
//  [[SSAccountManager sharedManager] saveBundleCode:self.jsBundleCode];
//  [[SSAccountManager sharedManager] saveBundleName:self.jsBundleName];
//}

//判断本地MD5跟服务器是否一致
-(BOOL)hashCodeIsTrueWith:(SSDownloadModel*)model{
  self.jsBundleName = model.bundleName;
  NSString *pathStr = [self.jsTempDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"%@/main.jsbundle",self.jsBundleName]].copy;
  NSString *localHashCode = [SSManager fileMD5:pathStr];
  if ([[model.hashCode lowercaseString] isEqualToString:localHashCode]) {
    return YES;
  }else{
    return NO;
  }
}

#pragma mark - 全量更新

- (void)allUpdate:(SSDownloadModel*)model{
  //2.解压bundle包到临时文件夹
  BOOL success = [self archiveBundle];
  
  if (success) {
    if ([self hashCodeIsTrueWith:model]) {
      //3.移动解压的文件夹到目标目录
      success = [self moveBundle];
//      [self saveBundleMessage];
    }else{
#pragma mark ---###############重新加载
      // 加载本地初始bundle
      [self reportComplete];
    }
  }
  if (success) {
    dispatch_async(dispatch_get_main_queue(), ^{
      //全量更新解压完成
      //全量更新成功
      if (self.delegate && [self.delegate respondsToSelector:@selector(onRNBundleAllUpDateOperationComplete)]) {
        [self.delegate onRNBundleAllUpDateOperationComplete];
      }
      
    });
  }
}

#pragma mark - 增量更新

- (void)diffUpdate{
  NSString *path = self.jsRootDir;
  NSError *error;
  BOOL success = NO;
  if (!self.localFilePrepared) {
    if (![self archiveInAppBundle]) {
      return;
    };
    if (! [self moveInAppBundle]) {
      return;
    }
  }
  //1.解压增量更新包
  success = [self archiveBundle];
  if (!success) {
    [self reportError:error];
    return;
  }
  //2.复制原bundle相关文件到.temp 后续操作.temp目录
  if ([[NSFileManager defaultManager] fileExistsAtPath:self.diffTempDir]) {
    [[NSFileManager defaultManager] removeItemAtPath:self.diffTempDir error:NULL];
  }
  [[NSFileManager defaultManager] copyItemAtPath:path toPath:self.diffTempDir error:&error];
  if (error) {
    [self reportError:error];
    return;
  }
  //3.获取增量更新包根目录
  self.diffRootDir = [self getDifferRootDir];
  if (!self.diffRootDir) {
    [self reportError:NULL];
  }
  //4.main.jsbundle的增量更新
  if (![self bundlePathesOper]) {
    [self reportError:error];
    return;
  }
  //5.根据update.json删除文件
  if (![self deleteFileWithUpdateJson]) {
    [self reportError:NULL];
    return;
  }
  //6.循环遍历asserts目录替换文件
  if (![self mergeAsserts]) {
    [self reportError:NULL];
    return;
  }
  //7、更改文件夹名称及删除
  if (![self renameFolder]) {
    [self reportError:NULL];
  }
#pragma Mark----增量更新完成
  //完成
  dispatch_async(dispatch_get_main_queue(), ^{
    
    //增量更新完成
    if (self.delegate && [self.delegate respondsToSelector:@selector(onRNBundlediffUpDateOperationComplete)]) {
      [self.delegate onRNBundlediffUpDateOperationComplete];
    }
  });
}

- (BOOL)bundlePathesOper{
  DiffMatchPatch *dmp = [DiffMatchPatch new];
  NSString *bundlePath = [self.diffTempDir stringByAppendingPathComponent:@"main.jsbundle"];
  NSError *error;
  NSString *bundleStr = [NSString stringWithContentsOfFile:bundlePath encoding:NSUTF8StringEncoding error:&error];
  if (error) {
    [self reportError:error];
    return false;
  }
  NSString *updateBundlePath = [self.diffRootDir stringByAppendingPathComponent:@"main.jsbundle"];
  //  [[NSFileManager defaultManager] setAttributes:@{NSFilePosixPermissions:@(777)} ofItemAtPath:updateBundlePath error:&error];
  //  if (error) {
  //    return false;
  //  }
  NSString *updateBundleStr = [NSString stringWithContentsOfFile:updateBundlePath encoding:NSUTF8StringEncoding error:&error];
  if (error) {
    //    [self reportError:error];
    return false;
  }
  NSMutableArray *originPathes = [dmp patch_fromText:updateBundleStr error:&error];
  if (error) {
    //    [self reportError:error];
    return false;
  }
  
  
  NSArray *resultPathes = [dmp patch_apply:originPathes toString:bundleStr];
  if (error) {
    //    [self reportError:error];
    return false;
  }
  if (resultPathes.count>0) {
    //写文件
    NSMutableData *writer = [[NSMutableData alloc] init];
    [writer appendData:[resultPathes[0] dataUsingEncoding:NSUTF8StringEncoding]];
    [writer writeToFile:bundlePath atomically:YES];
  }
  //比较md5值
  NSString *localHashCode = [SSManager fileMD5:bundlePath];
  if (![[self.downloadModel.hashCode lowercaseString] isEqualToString:localHashCode]) {
    return NO;
  }
  return true;
}

//下载文件解压到临时文件夹
- (BOOL)archiveBundle{
  
  NSError *error;
  BOOL success = [SSZipArchive unzipFileAtPath:self.jsTempDownFile toDestination:self.jsTempDirectory overwrite:YES password:nil error:&error];
  if (!success) {
    [self reportError:error];
    return NO;
  }
  [[NSFileManager defaultManager] removeItemAtPath:self.jsTempDownFile error:nil];
  return YES;
}

- (BOOL)moveBundle{
  NSError *error;
  BOOL success;
  if([[NSFileManager defaultManager] fileExistsAtPath:self.jsDirectory]){
    success = [[NSFileManager defaultManager] removeItemAtPath:self.jsDirectory error:&error];
    if (!success) {
      [self reportError:error];
      return NO;
    }
  }
  
  success = [[NSFileManager defaultManager] moveItemAtPath:self.jsTempDirectory toPath:self.jsDirectory error:&error];
  if (!success) {
    [self reportError:error];
    return NO;
  }
  return YES;
}



- (NSString*)getDifferRootDir{
  NSDirectoryEnumerator *dirEnum = [[NSFileManager defaultManager] enumeratorAtPath:self.jsTempDirectory];
  NSString *fileName;
  while (fileName = [dirEnum nextObject]) {
    BOOL dir;
    if([[NSFileManager defaultManager] fileExistsAtPath:[self.jsTempDirectory stringByAppendingPathComponent:fileName] isDirectory:&dir]){
      if (dir) {
        return [self.jsTempDirectory stringByAppendingPathComponent:fileName];
      }
    }
  }
  return NULL;
}

- (BOOL)deleteFileWithUpdateJson{
  NSString *file = [self.diffRootDir stringByAppendingPathComponent:@"update.json"];
  NSArray *deleteArray =[NSJSONSerialization JSONObjectWithData:[NSData dataWithContentsOfFile:file] options:NSJSONReadingAllowFragments error:NULL];
  if (![deleteArray isKindOfClass:[NSArray class]]) {
    return true;
  }
  if (!file) {
    return false;
  }
  NSString *diffOperRootDir = self.diffTempDir;
  for (NSDictionary *dic in deleteArray) {
    
    NSString *path = [diffOperRootDir  stringByAppendingPathComponent:dic[@"url"]];
    [[NSFileManager defaultManager] removeItemAtPath:path error:NULL];
  }
  return true;
}

- (BOOL)mergeAsserts{
  NSString *diffOperAssetsDir = [self.diffTempDir stringByAppendingPathComponent:@"assets"];
  if ([[NSFileManager defaultManager] fileExistsAtPath:diffOperAssetsDir]) {
    return [self _mergeDir:[self.diffRootDir stringByAppendingPathComponent:@"assets"] withDir:diffOperAssetsDir];
  }
  return true;
}
// dir1 -> dir2 相同文件dir1 覆盖 dir2
- (BOOL)_mergeDir:(NSString*)dir1 withDir:(NSString*)dir2{
  NSDirectoryEnumerator *dirEnum = [[NSFileManager defaultManager] enumeratorAtPath:dir1];
  NSString *fileName;
  //循环遍历文件夹 复制文件
  while (fileName = [dirEnum nextObject]) {
    BOOL dir;
    NSString *path1 = [dir1 stringByAppendingPathComponent:fileName];
    [[NSFileManager defaultManager] fileExistsAtPath:path1 isDirectory:&dir];
    
    if (dir) {
      //是文件夹
      //      NSString *lastPath = [fileName lastPathComponent];
      NSString *dir2Path = [dir2 stringByAppendingPathComponent:fileName];
      if ([[NSFileManager defaultManager] fileExistsAtPath:dir2Path]) {
        //di2对应文件夹存在 继续往里进入
        if (![self _mergeDir:path1 withDir:dir2Path]) {
          return false;
        };
      }
      else{
        //dir2对应文件夹不存在 直接移动整个文件夹
        if(![[NSFileManager defaultManager] moveItemAtPath:path1 toPath:dir2Path error:NULL]){
          return false;
        }
      }
    }
    else{
      //不是文件夹 移动文件
      NSError *error;
      NSString *path2 = [dir2 stringByAppendingPathComponent:fileName];
      //      if(!([[NSFileManager defaultManager] fileExistsAtPath:path2] && [[NSFileManager defaultManager] removeItemAtPath:path2 error:&error]) || ![[NSFileManager defaultManager] moveItemAtPath:path1 toPath:path2 error:&error]){
      //        return false;
      //      }
      if ([[NSFileManager defaultManager] fileExistsAtPath:path2]) {
        if(![[NSFileManager defaultManager] removeItemAtPath:path2 error:&error]){
          return false;
        }
      }
      if (![[NSFileManager defaultManager] moveItemAtPath:path1 toPath:path2 error:&error]) {
        return false;
      }
    }
  }
  
  return true;
}

- (BOOL)renameFolder{
  NSString *path = self.jsRootDir;
  NSString *deprecatedPath = [self.jsDirectory stringByAppendingPathComponent:@"deprecated"];
  
  NSString *targetPath = [self.jsDirectory stringByAppendingPathComponent:self.jsBundleName];
  [[NSFileManager defaultManager] moveItemAtPath:path toPath:deprecatedPath error:NULL];
  NSError *error = NULL;
  [[NSFileManager defaultManager] moveItemAtPath:self.diffTempDir toPath:targetPath error:&error];
  if (error) {
    [[NSFileManager defaultManager] moveItemAtPath:deprecatedPath toPath:path error:NULL];
    return false;
  }
//  [self saveBundleMessage];
  [[NSFileManager defaultManager] removeItemAtPath:deprecatedPath error:NULL];
  [[NSFileManager defaultManager] removeItemAtPath:self.jsTempDirectory error:NULL];
  return YES;
}


- (NSString*)jsDirectory{
  if (!_jsDirectory) {
    NSString *document = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    _jsDirectory = [document stringByAppendingPathComponent:@"sunsoft"].copy;
  }
  return _jsDirectory;
}

//解压文件路径
- (NSString*)jsLocation{
  if ([[SSAccountManager sharedManager] getBundleName].length == 0) {
    _jsLocation = [self.jsDirectory stringByAppendingPathComponent:@"sunsoft/main.jsbundle"].copy;
  }else{
    _jsLocation = [self.jsDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"%@/main.jsbundle",[[SSAccountManager sharedManager] getBundleName]]].copy;
  }
  return _jsLocation;
}

//临时解压路径
- (NSString*)jsInAppArchiveTempDir{
  if(!_jsInAppArchiveTempDir){
    NSString *document = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    _jsInAppArchiveTempDir = [document stringByAppendingPathComponent:@"inapptemp"];
  }
  return _jsInAppArchiveTempDir;
}
//临时文件夹
- (NSString*)jsTempDirectory{
  if (!_jsTempDirectory) {
    NSString *document = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    _jsTempDirectory = [document stringByAppendingPathComponent:@"temp"].copy;
  }
  return _jsTempDirectory;
}
//下载保存地址
- (NSString*)jsTempDownFile{
  if (!_jsTempDownFile) {
    
    _jsTempDownFile = [self.jsTempDirectory stringByAppendingPathComponent:@"master.zip"];
  }
  return _jsTempDownFile;
}
//增量更新操作临时文件夹
- (NSString*)diffTempDir{
  if (!_diffTempDir) {
    _diffTempDir = [self.jsDirectory stringByAppendingPathComponent:@".temp"];
  }
  return _diffTempDir;
}

- (NSString*)jsRootDir{
  if ([[SSAccountManager sharedManager] getBundleName].length == 0) {
    _jsRootDir = [self.jsDirectory stringByAppendingPathComponent:@"sunsoft"].copy;
  }else{
    _jsRootDir = [self.jsDirectory stringByAppendingPathComponent:[NSString stringWithFormat:@"%@",[[SSAccountManager sharedManager] getBundleName]]].copy;
  }
  return _jsRootDir;
}
@end
