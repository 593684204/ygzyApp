/**
 * 实现Provider对视图部分的包裹
 * Created by qiaozm on 2018/5/8.
 */
import React, {Component} from 'react';
import {Provider} from 'react-redux';
import configureStore from './store/ConfigureStore';
import App from './container/App';// app的入口
const store = configureStore();
export default class Root extends Component {
    constructor(props) {
        super(props);
    }
    componentDidMount() {
    };
    render() {
        return (
            <Provider store={store}>
                <App />
            </Provider>
        )
    }
}
