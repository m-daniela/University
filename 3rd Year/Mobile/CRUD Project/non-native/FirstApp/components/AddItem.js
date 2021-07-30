import React, {useContext, useEffect} from 'react'
import {Alert} from "react-native"
import { NetworkContext } from './Context/NetworkProvider'

import CustomForms from './CustomForms'
import { addItem } from './data/Database'
import { DataContext } from './Context/DataContext'

const AddItem = ({navigation}) => {

    const {dispatch, connection} = useContext(DataContext);
    // const {connection} = useContext(NetworkContext);

    const data = {
        id: Math.floor(Math.random() * 1000) + 1,
        title: "", 
        year: 0,
        genres: [],
        status: 0,
        stoppedAt: "00:00",
        episode: 1,
        season: 1,
    }

    const finish = (item) => {

        if (item.title === ""){
            Alert.alert(
                "Missing title",
                "You must provide a title",
                [
                  { text: "OK", onPress: () => console.log("OK Pressed") }
                ],
                { cancelable: false }
              );
        }
        else{
            addItem(item, connection, dispatch);
            
            // dispatch({type: "ADD_ITEM", item});
            navigation.goBack();
            
        }
        
    }

    return (<CustomForms data={data} finish={finish}/>);
}

export default AddItem
