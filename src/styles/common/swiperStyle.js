/**
 * swiper样式表
 *Created by qiaozm on 2018/5/15
 */
import {StyleSheet} from 'react-native'
import Util from 'ygzycomponent/tools/Util';
const styles = StyleSheet.create({
    slide1: {
        justifyContent:'center',
        alignItems:'center',
        width:Util.size.width,
        height:34*Util.size.width/75,
    },
    slide2: {
        flex:1,
        justifyContent:'center',
        alignItems:'center'
    },
    slide3: {
        flex:1,
        justifyContent:'center',
        alignItems:'center'
    },
    pagination_x:{
        position: 'absolute',
        height:34*Util.size.width/75,
        flexDirection:'row',
        justifyContent:'center',
        alignItems:'flex-end',
        width:Util.size.width
    },
    pagination: {
        position: 'absolute',
        height:34*Util.size.width/75,
        flexDirection:'row',
        justifyContent:'center',
        alignItems:'flex-end',
        width:Util.size.width
    },
    swiperImg:{
        height:34*Util.size.width/75,
        width:Util.size.width
    }
});
export default styles;