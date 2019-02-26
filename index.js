import React from "react";
import {ActivityIndicator, NativeModules, StyleSheet, Text, View, TouchableOpacity,} from 'react-native';

const { RNRaonSecure } = NativeModules;

export default class RaonSecure extends React.PureComponent {

    static async getItems() {
        return [];
    }

    state = {};
    constructor(props){
        super(props);
    }

    showItem() {

    }

    render () {
        const { labelStyle, labelText, textStyle, buttonStyle, buttonText, disableButtonStyle, ...props } = this.props;
        return (
            <View style={[ styles.container ]}>
                <Text style={ labelStyle }>{ labelText }</Text>
                <View>
                    <View style={[ StyleSheet.absoluteFill, ]}>
                        <ActivityIndicator />
                    </View>
                    <Text style={ textStyle }>0000-0000-0000</Text>
                    <TouchableOpacity style={ buttonStyle }>
                        <Text>{ buttonText }</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container : {
    }
});

