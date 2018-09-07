import React, { Component } from 'react';
import JPushModule from 'jpush-react-native';
import {
    StyleSheet,
    Text,
    View,
    Image,
} from 'react-native';

export default class Main extends Component {
    constructor(props) {
        super(props);
        this.state={
            pushMsg:'初始默认值'
        }
    }

    componentDidMount() {
        JPushModule.addTags(['xxxxxxxx'], success => {
            alert('1');
        });
        // 新版本必需写回调函数
        // JPushModule.notifyJSDidLoad();
        JPushModule.notifyJSDidLoad((resultCode) => {
            if (resultCode === 0) {}
        });
        // 接收自定义消息
        JPushModule.addReceiveCustomMsgListener((message) => {
            this.setState({pushMsg: message});
        });
        // 接收推送通知
        JPushModule.addReceiveNotificationListener((message) => {
            console.log("receive notification: " + message);
        });
        // 打开通知
        JPushModule.addReceiveOpenNotificationListener((map) => {
            console.log("Opening notification!");
            console.log("map.extra: " + map.extras);
            // 可执行跳转操作，也可跳转原生页面
            this.props.navigation.navigate("ServiceSchoolList",{token:'我就是测试来的'});
        });
    };

    componentWillUnmount() {
        JPushModule.removeReceiveCustomMsgListener();
        JPushModule.removeReceiveNotificationListener();
    };

    render() {
        return (
            <View style={styles.container}>
                <Text onPress={()=>{this.props.navigation.navigate('ServiceSchoolList',{token:'我就是测试来的'});}}>第三个Tab页</Text>
                <Text>pushMsg:{this.state.pushMsg}</Text>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
})