import React from 'react'
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native'
import {statusList} from "../data/Database"
import {styles} from "../styles/styles"



const ModalStatus = ({savedStatus, setStatus}) => {
    
    const updateStatus = (chosenStatus) =>{
        setStatus(chosenStatus);
    }

    function RadioButton(props) {
        
        return (
            <TouchableOpacity style={styles.rowAlign} onPress={() => updateStatus(props.index)}>
                <View style={styles.radioButton}>
                    {props.selected ? <View style={styles.radioChoice}/> : null}
                </View>
                
                {props.children}
            </TouchableOpacity>
        );
    }

    return (
        <View style={{...style, ...styles.general}}>
            {statusList.map((s, index) => index === savedStatus? 
                <RadioButton key={index} index={index} selected={true}><Text style={{...styles.fontStyle, marginVertical: 10}}>{s}</Text></RadioButton> : 
                <RadioButton key={index} index={index} selected={false}><Text style={{...styles.fontStyle, marginVertical: 10}}>{s}</Text></RadioButton>)
            }
        </View>
    )
}

const style = StyleSheet.create({
    modalStatus: {
        // flexDirection: "column", 
        justifyContent: "center", 
        alignItems: "center", 
        flex: 1,
        paddingVertical: 50,
        width: 85,
        height: 50,
    }
});

export default ModalStatus
