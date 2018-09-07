import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
    Image,
    ScrollView,
    TextInput,
    TouchableHighlight,
    NativeModules,
} from 'react-native';
dgeModule=NativeModules.RNBridgeModule;
import {connect} from 'react-redux'; // 引入connect函数
import {NavigationActions} from 'react-navigation';
import *as loginAction from '../../actions/loginAction';// 导入action方法
import Util from 'ygzycomponent/tools/Util';
import service from '../../common/service';
import {
    msgCode_Zero,
    msgCode_One,
    msgCode_Three,
    msgCode_Four,
    msgCode_Seven,
    msgCode_Eight
} from '../../common/constant';
/*import toast from '../../components/common/Toast';
import alert from '../../components/common/Alert';
import LoadIng from '../../components/common/Loading';
import Button from '../../components/common/Button';*/

const RNBridgeModule = NativeModules.RNBridgeModule;
const resetAction = NavigationActions.reset({
    index: 0,
    actions: [
        NavigationActions.navigate({routeName: 'Index'})
    ]
})

class LoginPage extends Component {
    static navigationOptions=({
        headerTitle:(
            <View style={{flex:1,backgroundColor:'#fff',justifyContent:'center',alignItems:'center'}}>
                <Text style={{fontSize:18,color:'#333'}}>登 录</Text>
            </View>
        )
    });

    shouldComponentUpdate(nextProps, nextState) {
        // 登录完成,切成功登录
        /*let toastInfo={
            message:'toast提示颜色',
            duration:2000
        }
        let aa=toast.show(toastInfo);
        setTimeout(function () {
            toast.hide(aa);
        }, 500);*/
        if (nextProps.status === '登陆成功' && nextProps.isSuccess) {
            let msgCode = parseInt(nextProps.user.msgCode);
            if (msgCode_Zero === msgCode) { //保存用户信息，并进入首页
                this.props.navigation.dispatch(resetAction);
                return false;
            } else if (msgCode_One === msgCode) {  //请求失败
                //ToastAndroid.show(nextProps.user.obj.title, ToastAndroid.SHORT);
                /*let toastInfo={
                    message:nextProps.user.obj.title,
                    duration:2000
                }
                toast.show(toastInfo);*/
                return false;
            } else if (msgCode_Three === msgCode) { //显示验证码
                this.isShowCode = true;
                return true;
            } else if (msgCode_Seven === msgCode) {

            } else if (msgCode_Four === msgCode) { //
                Util.FuncUpdate(0, nextProps.user);
                return false;
            } else if (msgCode_Eight === msgCode) { //bundle有更新
                //Util.FuncUpdate(2, nextProps.user);
            }

            return false;
        }
        /*let options={
            title:'测试标题',
            message:'测试内容测试内容',
            buttons:[{text: '确定', onPress: () => console.log('OK Pressed')},{text: '取消', onPress: () => console.log('OK Pressed')}]
        };
        alert.show(options);*/
        return true;
    }

    constructor(props) {
        super(props);
        this.userName = '';
        this.password = '';
        this.validateCode = '';
        this.uuid = '';
        this.isShowCode = false;
    }

    componentDidMount() {
        RNBridgeModule.getUUID((uuid) => {
            console.log('uuid:' + uuid);
            this.uuid = uuid;
        });
    };

    componentWillUnmount() {

    };

    render() {
        return (
            <View style={styles.container}>
                <View style={{flex: 1209}}>
                    <ScrollView keyboardShouldPersistTaps="handled" style={{flex: 1}}>
                        <View style={styles.header}>
                            <View style={styles.main1}>
                                <View style={{
                                    alignItems: 'flex-end',
                                    flexDirection: 'row',
                                    justifyContent: 'center',
                                    flex: 1
                                }}>
                                    <Image style={{width: Util.getSize(530, 750, 'w')}}
                                           source={require('../../resources/images/login/main1.png')}
                                           resizeMode='contain'/>
                                </View>
                            </View>
                            <View style={styles.main2}>
                                <View style={{flex: 112.5}}/>
                                <View style={{flex: 525}}>
                                    <View style={{
                                        flex: 195,
                                        flexDirection: 'column',
                                        justifyContent: 'flex-start',
                                        borderBottomWidth: Util.getSize(1, 1334, 'h'),
                                        borderBottomColor: '#b0b0b0'
                                    }}>
                                        <View style={{flex: 115}}/>
                                        <View style={{flex: 55, flexDirection: 'row', justifyContent: 'center'}}>
                                            <View style={{flex: 20}}/>
                                            <View style={{flex: 32, flexDirection: 'row', justifyContent: 'center'}}>
                                                <Image style={{height: Util.getSize(51, 1334, 'h')}}
                                                       source={require('../../resources/images/login/name.png')}
                                                       resizeMode='contain'/>
                                            </View>
                                            <View style={{flex: 470}}>
                                                <TextInput
                                                    style={{padding: 0, height: 30, flex: 470, textAlign: 'center'}}
                                                    underlineColorAndroid="transparent" placeholder="请输入用户名"
                                                    placeholderTextColor="#adadad" onChangeText={(text) => {
                                                    this.userName = text
                                                }}/>
                                            </View>
                                        </View>
                                        <View style={{flex: 30}}/>
                                    </View>
                                    <View style={{
                                        flex: 146,
                                        flexDirection: 'column',
                                        justifyContent: 'flex-start',
                                        borderBottomWidth: Util.getSize(1, 1334, 'h'),
                                        borderBottomColor: '#b0b0b0'
                                    }}>
                                        <View style={{flex: 67}}/>
                                        <View style={{flex: 50, flexDirection: 'row', justifyContent: 'center'}}>
                                            <View style={{flex: 20}}/>
                                            <View style={{flex: 32, flexDirection: 'row', justifyContent: 'center'}}>
                                                <Image style={{height: Util.getSize(50, 1334, 'h')}}
                                                       source={require('../../resources/images/login/pwd.png')}
                                                       resizeMode='contain'/>
                                            </View>
                                            <View style={{flex: 470}}>
                                                <TextInput
                                                    style={{padding: 0, height: 30, flex: 470, textAlign: 'center'}}
                                                    secureTextEntry={true} underlineColorAndroid="transparent"
                                                    placeholder="请输入密码" placeholderTextColor="#adadad"
                                                    onChangeText={(text) => {
                                                        this.password = text
                                                    }}/>
                                            </View>
                                        </View>
                                        <View style={{flex: 30}}/>
                                    </View>
                                    {
                                this.isShowCode ?
                                <View style={{
                                    flex: 146,
                                    flexDirection: 'column',
                                    justifyContent: 'flex-start',
                                    borderBottomWidth: Util.getSize(1, 1334, 'h'),
                                    borderBottomColor: '#b0b0b0'
                                }}>
                                    <View style={{flex: 67}}/>
                                    <View style={{flex: 50, flexDirection: 'row', justifyContent: 'flex-start'}}>
                                        <View style={{flex: 25, flexDirection: 'row', justifyContent: 'center'}}>
                                            <Text style={{textAlignVertical: 'center'}}>验证码：</Text>
                                        </View>
                                        <View style={{flex: 25}}>
                                            <TextInput
                                                style={{padding: 0, height: 30, flex: 25, textAlign: 'center'}}
                                                underlineColorAndroid="transparent" onChangeText={(text) => {
                                                this.validateCode = text
                                            }}  placeholder="" placeholderTextColor="#adadad"/>
                                        </View>
                                        <View style={{flex: 25, justifyContent: 'center'}}>
                                                <Image style={{height:30,width:60}}  resizeMode='contain'
                                                                             source={{uri:service.baseUrl + service.VERIFICATION_CODE + "?uniqueNo=" + this.uuid + "&sourceType=0&" + (new Date()).getTime() }}
                                                onLoadEnd={()=>{
                                                    this.props.nextCode=false;
                                                }
                                                }/>

                                          </View>
                                        <View style={{flex: 25, flexDirection: 'row', justifyContent: 'center'}} >
                                           <Text style={{textAlignVertical: 'center',fontSize:14,color:'#47B7B0'}} onPress={()=>{
                                               this.props.FunNextCode();
                                           }}>换一张</Text>
                                        </View>
                                    </View>
                                    <View style={{flex: 30}}/>
                                </View>
                                : null
                            }

                                </View>
                                <View style={{flex: 112.5}}/>
                            </View>


                            <View style={styles.main3}>
                                <View style={{flex: 100}}/>
                                <TouchableHighlight underlayColor={'rgba(0,0,0,0)'} style={{flex: 80}}
                                                    onPress={() => this.props.FuncLogin(this.userName, this.password, this.validateCode, this.uuid)}>
                                    <View>
                                        <View style={{flex: 1, alignItems: 'center', justifyContent: 'flex-start'}}>
                                            <Image style={{height: Util.getSize(80, 1334, 'h')}}
                                                   source={require('../../resources/images/login/btn.png')}
                                                   resizeMode='contain'/>
                                        </View>
                                        <View style={{
                                            alignItems: 'center',
                                            justifyContent: 'center',
                                            height: Util.getSize(80, 1334, 'h')
                                        }}>
                                            <Text style={{
                                                textAlignVertical: 'center',
                                                color: '#ffffff',
                                                fontSize: 16
                                            }}>登录</Text>
                                        </View>
                                    </View>
                                </TouchableHighlight>
                            </View>
                            <View style={styles.main4}>
                                <View style={{flex: 46}}/>
                                <View style={{flex: 27, flexDirection: 'row'}}>
                                    <View style={{flex: 665, alignItems: 'flex-end'}}>
                                        <Text style={{fontSize: 12, color: '#918f8b'}}>忘记密码？</Text>
                                    </View>
                                    <View style={{flex: 85}}/>
                                </View>
                            </View>
                        </View>
                        <View style={styles.main}/>
                    </ScrollView>
                </View>
                {
                    /*this.props.status==='正在登陆' && <LoadIng data={
                        {
                            isImage:true,
                            source:require('../../resources/images/common/loading.gif')
                        }
                    }/>*/
                }
                {/*<Button data={{
                    isImageBackground:false,
                    text:'我不是背景图按钮',
                    style:{alignItems: 'center',justifyContent: 'center',height: Util.getSize(80, 1334, 'h'),backgroundColor:'red'},
                    fontStyle:{color:'#5e5a57',fontSize:9},
                    source:require('../../resources/images/common/btn.png')
                }}/>*/}
            </View>
        );
    }


}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    header: {
        height: Util.getSize(823, 1334, 'h')
    },
    main: {
        flex: 386
    },
    main1: {
        flex: 203,
    },
    imagePic1: {},
    main2: {
        flex: 341,
        flexDirection: 'row'
    },
    main3: {
        flex: 180
    },
    main4: {
        flex: 73,
    }
});
export default connect(
    (state) => ({
        status: state.loginIn.status,
        isSuccess: state.loginIn.isSuccess,
        user: state.loginIn.user,
        nextCode:state.loginIn.nextCode
    }),
    (dispatch) => ({
        FuncLogin: (userName, password, code, uuid) => dispatch(loginAction.login(userName, password, code, uuid)),
        FunNextCode:()=>dispatch(loginAction.FunNextCode()),
    })
)(LoginPage)