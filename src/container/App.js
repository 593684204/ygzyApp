import React, {Component} from 'react';
import SplashScreen from 'react-native-splash-screen';
import {AppNavigator} from './routers'
import {
    NativeModules,
    View
} from 'react-native';

const RNBridgeModule = NativeModules.RNBridgeModule;

class App extends Component {
    componentDidMount() {
       this.hideSplash();
    }
    render() {
        return (
            <View style={{flex:1}}>
                <AppNavigator ref={nav => this.navigation = nav} />
            </View>
        );
    }
    hideSplash = () => {
        SplashScreen.hide();//启动页
        RNBridgeModule.dismissProgress(); //隐藏原生弹窗
    };
}

export default App;
