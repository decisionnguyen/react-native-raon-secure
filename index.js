import React from "react";
import {ActivityIndicator, NativeModules, StyleSheet, Text, View,} from 'react-native';

const { RNRaonSecure } = NativeModules;

export default class RaonSecure extends React.Component {

    state = {};

    constructor(props){
        super(props);
    }

    render () {

        const { labelStyle, textStyle, buttonStyle, disableButtonStyle, ...props } = this.props;

        return (
            <View style={ styles.container }>
                <Text> RaonSecure </Text>
                <ActivityIndicator />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container : {
        alignItems: 'center',
    }
});

