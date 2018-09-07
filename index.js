import React, {Component} from 'react';
import {
    AppRegistry,
    View
} from 'react-native';
import  Root from './src/Root';
export default class App extends Component {
    constructor(props) {
        super(props);
    }
    componentDidMount() {
    };
    render() {
        return (
            <View style={{flex:1}}>
                <Root/>
            </View>
        )
    }
}
AppRegistry.registerComponent('sunsoft_supplier', () => App);
