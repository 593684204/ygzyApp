/**
 * reducer统一处理入口
 * Created by qiaozm on 2018/5/8.
 */
'use strict';
import { combineReducers } from 'redux';
import loginIn from './loginReducer'; // 导入登录的redux处理过程
/******************公共start*************************/
/******************公共end*************************/
//import mainIn from './mainReducer';
const rootReducer = combineReducers({ // 将所有的redux处理逻辑包装在一起
    loginIn
});
export default rootReducer; // 导出,作为统一入口