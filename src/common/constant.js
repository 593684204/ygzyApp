/**
 * 存储常量
 *Created by qiaozm on 2018/5/14
 */
import {Platform,NativeModules} from 'react-native';
export const msgCode_Zero=0;//接口正常返回数据
export const msgCode_One=1;//接口返回数据为空或者服务端发生异常但正常返回数据
export const msgCode_Two=2;
export const msgCode_Three=3;
export const msgCode_Four=4;//接口返回需要版本更新
export const msgCode_Five=5;//普通更新
export const msgCode_Six=6;//bundle更新
export const msgCode_Seven=7;////登录时  用户未划分区域
export const msgCode_Eight = 8;////登录时  用户未划分区域
export const msgCode_NinetyNine=99;//接口返回需要强制退出
export const timeout=10*1000;//10s超时
export const OS=(Platform.OS==='ios'?'ios':'android');//判断平台类型
export const RNBridgeModule=NativeModules.RNBridgeModule;//原生方法调用
export const type=(Platform.OS==='ios'?'01':'10');//平台类型type=01 ios type=10 android
