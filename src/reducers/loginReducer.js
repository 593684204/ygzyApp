/**
 * 处理登录过程中的state变化
 * Created by qiaozm on 2018/5/8.
 */
'use strict';
import * as types from '../constants/loginType' // 导入事件类别,用来做事件类别的判断
// 初始状态
const initialState = {
    status: '点击登录',
    isSuccess: false,
    user: null,
    nextCode:false,
}
// 不同类别的事件使用switch对应处理过程
export default function loginIn(state = initialState, action) {
    switch (action.type) {
        case types.LOGIN_IN_DOING:
            return {
                ...state,
                status: '正在登陆',
                isSuccess: false,
                user: null,
            };
        case types.LOGIN_IN_DONE:
            // console.log('action.user:' + action.user);
            return {
                ...state,
                status: '登陆成功',
                isSuccess: true,
                user: action.user,
            };
        case types.LOGIN_IN_ERROR:
            return {
                ...state,
                status: '登录出错',
                isSuccess: true,
                user: null,
            };
        case types.LOGIN_CODE:
            return {
                ...state,
                status: '',
                isSuccess: false,
                user: null,
                nextCode:true
            };
        default:
            return state;
    }
}