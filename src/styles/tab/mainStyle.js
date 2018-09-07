/**
 * MainPage样式表
 *Created by qiaozm on 2018/5/15
 */
import { StyleSheet} from 'react-native';
import Util from 'ygzycomponent/tools/Util';
const styles=StyleSheet.create({
    container: {
        flex: 1,
    },
    swiperContainer:{
        flex:345,
        borderBottomWidth:Util.getSize(5,1334,'h'),
        borderBottomColor:'#dedede',
        alignItems:'center'
    },
    itemRLContainer:{
        flex:220,
        borderBottomWidth:Util.getSize(4,1334,'h'),
        borderBottomColor:'#e5e5e5'
    },
    itemCenterContainer:{
        flex:208,
        borderRightWidth:Util.getSize(4,750,'w'),
        borderLeftWidth:Util.getSize(4,750,'w'),
        borderRightColor:'#e5e5e5',
        borderLeftColor:'#e5e5e5',
        borderBottomWidth:Util.getSize(4,1334,'h'),
        borderBottomColor:'#e5e5e5'
    },
    item_image_container:{
        flex:110,
        flexDirection:'column',
        alignItems:'center',
        justifyContent:'flex-end'
    },
    item_image:{
        height:Util.getSize(79,1334,'h')
    },
    item_text_container:{
        flex:90,
        alignItems:'center',
        justifyContent:'center'
    },
    item_text:{
        fontSize: 13,
        color:'#605c5b'
    }
});
export default styles;