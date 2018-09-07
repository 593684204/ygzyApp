/**
 * Created by qiaozm on 2018/5/8.
 * 声明所有界面，配置相关属性
 */
import React from 'react';
import {
    Image,
    StyleSheet
} from 'react-native';
import {
    StackNavigator,
    TabNavigator,
    TabBarBottom,
    NavigationActions,
    createStackNavigator
} from 'react-navigation';
import CardStackStyleInterpolator from 'react-navigation/src/views/CardStack/CardStackStyleInterpolator';

import Util from 'ygzycomponent/tools/Util';

/************************业务页面****************************************/

import MainPage from '../pages/tab/MainPage';
import Two from '../pages/tab/Two';
import Three from '../pages/tab/Three';
import Four from '../pages/tab/Four';
import LoginPage from '../pages/login/LoginPage';
//import ServiceSchoolList from '../pages/serviceSchool/ServiceSchoolList';//服务中学校列表
const indexNormalIcon = require('../resources/images/common/index_normal.png');
const indexPressedIcon=require('../resources/images/common/index_pressed.png');
const statisNormalIcon=require('../resources/images/common/statis_normal.png');
const statisPressedIcon=require('../resources/images/common/statis_pressed.png');
const MessageNormalIcon = require('../resources/images/common/message_normal.png');
const MessagePressedIcon=require('../resources/images/common/message_pressed.png');
const AboutNormalIcon=require('../resources/images/common/about_normal.png');
const AboutPressedIcon=require('../resources/images/common/about_pressed.png');

const styles = StyleSheet.create({
    tabIcon: {
         height:Util.getSize(80,1334,'h'),
        resizeMode: 'cover'
    }
});

const TabRouteConfigs = {
    MainPage: {
        screen: MainPage,
        navigationOptions: ({navigation})=>({
            title: '',
            tabBarIcon: ({focused, tintColor})=>(
                <Image source={focused ? indexPressedIcon : indexNormalIcon} style={styles.tabIcon} resizeMode='contain'/>
            ),
        }),
    },
    Two:{
        screen:Two,
        navigationOptions:({navigation})=>({
           title:'',
           tabBarIcon: ({focused, tintColor})=>(
               <Image source={focused ? statisPressedIcon : statisNormalIcon} style={styles.tabIcon} resizeMode='contain'/>
           ),
        }),
    },
    Three:{
        screen:Three,
        navigationOptions:({navigation})=>({
            title:'',
            tabBarIcon: ({focused, tintColor})=>(
                <Image source={focused ? MessagePressedIcon : MessageNormalIcon} style={styles.tabIcon} resizeMode='contain'/>
            ),
        }),
    },
    Four:{
        screen:Four,
        navigationOptions:({navigation})=>({
            title:'',
            tabBarIcon: ({focused, tintColor})=>(
                <Image source={focused ? AboutPressedIcon : AboutNormalIcon} style={styles.tabIcon} resizeMode='contain'/>
            ),
        }),
    },
};

const TabNavigatorConfigs ={
    initialRouteName: 'MainPage',
    tabBarComponent: TabBarBottom,
    tabBarPosition: 'bottom',
    lazy: true,
    swipeEnabled:false,
    tabBarOptions:{
        activeTintColor: '#2562b4', // 文字和图片选中颜色
        showIcon: true, // android 默认不显示 icon, 需要设置为 true 才会显示
        indicatorStyle: {
            height: 0
        }, // android 中TabBar下面会显示一条线，高度设为 0 后就不显示线了， 不知道还有没有其它方法隐藏？？？
        style: {
            backgroundColor: '#FFFFFF', // TabBar 背景色
            height:Util.getSize(110,1334,'h'),
            width:Util.size.width
        },
        labelStyle: {
            fontSize: 11, // 文字大小
        },
    }
};

const TabBarNavigator = TabNavigator(TabRouteConfigs, TabNavigatorConfigs);

const StackRouteConfigs ={
    /*Splash: {
        screen: Splash,
        navigationOptions: {
            header: null
        }
    },----闪屏页*/
    LoginPage: {
        screen: LoginPage,
        navigationOptions:({navigation})=>({
            title:''
        })
    },
    /*ServiceSchoolList: {
        screen: ServiceSchoolList,
        navigationOptions:({navigation})=>({
            title:''
        })
    },*/
    Index: {
        screen: TabBarNavigator
    }
};

const StackNavigatorConfigs = {
    initialRouteName: 'LoginPage', // 初始化哪个界面为根界面
    mode:'card', // 跳转方式：默认的card，在iOS上是从右到左跳转，在Android上是从下到上，都是使用原生系统的默认跳转方式。
    headerMode:'screen', // 导航条动画效果：float表示会渐变，类似于iOS的原生效果，screen表示没有渐变。none表示隐藏导航条
    transitionConfig:()=>({
        screenInterpolator:CardStackStyleInterpolator.forHorizontal,// forHorizontal 从右向左, forVertical 从下向上, forFadeFromBottomAndroid 安卓那种的从下向上  forInitial 无动画
    }),
};
const AppNavigator = StackNavigator(StackRouteConfigs, StackNavigatorConfigs);
export {
    AppNavigator
};
const defaultGetStateForAction = AppNavigator.router.getStateForAction;
AppNavigator.router.getStateForAction = (action, state) => {
    //可以在此判断处理快速点击时跳转多次的问题
   /* if (state &&action.type === NavigationActions.BACK) {
        // Returning null from getStateForAction means that the action
        // has been handled/blocked, but there is not a new state
        return null;
    }*/
    return defaultGetStateForAction(action, state);
};