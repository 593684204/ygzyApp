import React, { Component } from 'react';
import {
    Text,
    View,
    Image,
    TouchableHighlight
} from 'react-native';
import Util from 'ygzycomponent/tools/Util';
import mainStyle from '../../styles/tab/mainStyle';
class MainPage extends Component {
    static navigationOptions=({
        headerTitle:(
            <View style={{flex:1,backgroundColor:'#fff',justifyContent:'center',alignItems:'center'}}>
                <Text style={{fontSize:18,color:'#333'}}>首页</Text>
            </View>
        ),
        /*headerLeft:(<TouchableHighlight onPress={()=>{alert('左边按钮')}}>
            <Text>按钮</Text>
        </TouchableHighlight>),
        headerRight:(<TouchableHighlight onPress={()=>{alert('右边按钮')}}>
            <Text>右按钮</Text>
        </TouchableHighlight>)*/
    });
    constructor(props) {
        super(props);
    }

    componentDidMount() {
    };

    componentWillUnmount() {

    };

    render() {
        return(
            <View style={mainStyle.container}>
                {/*<View style={mainStyle.swiperContainer}>
                    <Swiper data={this.props.data}/>
                </View>*/}
                <View style={{flex:754}}>
                    <View style={{flex:73}}/>
                    <View style={{flex:204,flexDirection:'row'}}>
                        <View style={{flex:41}}/>
                        <TouchableHighlight underlayColor={'rgba(0,0,0,0)'} onPress={()=>{}} style={{flex:220}}>
                            <View style={mainStyle.itemRLContainer}>
                                <View style={mainStyle.item_image_container}>
                                    <Image style={mainStyle.item_image} source={require('../../resources/images/main/serviceSchoolIcon.png')} resizeMode='contain' />
                                </View>
                                <View style={mainStyle.item_text_container}>
                                    <Text style={mainStyle.item_text}>服务中学校</Text>
                                </View>
                            </View>
                        </TouchableHighlight>
                        <View style={mainStyle.itemCenterContainer}>
                            <View style={mainStyle.item_image_container}>
                                <Image style={mainStyle.item_image} source={require('../../resources/images/main/historySchoolIcon.png')} resizeMode='contain' />
                            </View>
                            <View style={mainStyle.item_text_container}>
                                <Text style={mainStyle.item_text}>历史学校</Text>
                            </View>
                        </View>
                        <View style={mainStyle.itemRLContainer}>
                            <View style={mainStyle.item_image_container}>
                                <Image style={mainStyle.item_image} source={require('../../resources/images/main/noticeSchoolIcon.png')} resizeMode='contain' />
                            </View>
                            <View style={mainStyle.item_text_container}>
                                <Text style={mainStyle.item_text}>公告期内学校</Text>
                            </View>
                        </View>
                        <View style={{flex:41}}/>
                    </View>
                    <View style={{flex:204,flexDirection:'row'}}>
                        <View style={{flex:41}}/>
                        <View style={mainStyle.itemRLContainer}>
                            <View style={mainStyle.item_image_container}>
                                <Image style={mainStyle.item_image} source={require('../../resources/images/main/noticeOrderIcon.png')} resizeMode='contain' />
                            </View>
                            <View style={mainStyle.item_text_container}>
                                <Text style={mainStyle.item_text}>公告期内订购</Text>
                            </View>
                        </View>
                        <View style={mainStyle.itemCenterContainer}>
                            <View style={mainStyle.item_image_container}>
                                <Image style={mainStyle.item_image} source={require('../../resources/images/main/noticeEndIcon.png')} resizeMode='contain' />
                            </View>
                            <View style={mainStyle.item_text_container}>
                                <Text style={mainStyle.item_text}>公告结束订购</Text>
                            </View>
                        </View>
                        <View style={mainStyle.itemRLContainer}>
                            <View style={mainStyle.item_image_container}>
                                <Image style={mainStyle.item_image} source={require('../../resources/images/main/payMoneyIcon.png')} resizeMode='contain' />
                            </View>
                            <View style={mainStyle.item_text_container}>
                                <Text style={mainStyle.item_text}>货款</Text>
                            </View>
                        </View>
                        <View style={{flex:41}}/>
                    </View>
                    <View style={{flex:200,flexDirection:'row'}}>
                        <View style={{flex:41}}/>
                        <View style={{flex:220}}>
                            <View style={mainStyle.item_image_container}>
                                <Image style={mainStyle.item_image} source={require('../../resources/images/main/retailIcon.png')} resizeMode='contain' />
                            </View>
                            <View style={mainStyle.item_text_container}>
                                <Text style={mainStyle.item_text}>零售</Text>
                            </View>
                        </View>
                        <View style={{flex:208,borderRightWidth:Util.getSize(4,750,'w'),borderLeftWidth:Util.getSize(4,750,'w'),borderRightColor:'#e5e5e5',borderLeftColor:'#e5e5e5'}}/>
                        <View style={{flex:220}}/>
                        <View style={{flex:41}}/>
                    </View>
                    <View style={{flex:73}}/>
                </View>
            </View>
        )
    }
}
export default MainPage;
