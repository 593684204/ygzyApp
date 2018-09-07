/**
 * 预处理消息区分各个事件的类别
 * Created by qiaozm on 2018/5/8.
 */
'use strict';
import * as types from '../constants/loginType';// 导入事件类型,用来做分配给各个事件
import service from '../common/service';
import request from 'ygzycomponent/tools/request';
// 模拟用户信息
let user = {
    name: 'zhangsan',
    age: 24,
}

// 访问登录接口 根据返回结果来划分action属于哪个type,然后返回对象,给reducer处理
export function login(userName, password, validateCode, uuid) {
    let url = service.baseUrl + service.login;

    let _q = 'userName=' + userName + '&password=' + password + '&uniqueNo=' + uuid + '&validateCode=' + validateCode;
    console.log(url + '?' + _q);
    let options = {
        method: 'POST',
        body: _q
    };
    return dispatch => {
        dispatch(isLogining()); // 正在执行登录请求
        request(url, options)
            .then(data => dispatch(loginSuccess(data)))
            .catch(e => dispatch(loginError(e)));
    }
}

function isLogining() {
    return {
        type: types.LOGIN_IN_DOING
    }
}

function loginSuccess(user) {
    console.log('loginSuccess:')
    console.log(user);
    return {
        type: types.LOGIN_IN_DONE,
        user: user,
    }
}

function loginError(e) {
    console.log('loginError:' + e);
    return {
        type: types.LOGIN_IN_ERROR,
    }
}

export function FunNextCode() {
    return dispatch=>{
        dispatch(nextCode());
    }
}
function nextCode() {
    return {
        type:types.LOGIN_CODE
    }
}