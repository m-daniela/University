import React, {useContext} from 'react'
import {Alert} from "react-native"
import { NetworkContext } from './Context/NetworkProvider'

import CustomForms from './CustomForms'
import { getDataServer, updateItem } from './data/Database'
import { DataContext } from './Context/DataContext'
import { customAlert } from './constants/Constants'

const Details = ({route, navigation}) => {
    const item = route.params.item;

    const {dispatch, connection} = useContext(DataContext);
    // const {connection} = useContext(NetworkContext);

    const finish = (item) => {
        if (item.title === ""){
            customAlert("Missing title", "You must provide a title");

            // Alert.alert(
            //     "Missing title",
            //     "You must provide a title",
            //     [
            //       { text: "OK", onPress: () => console.log("OK Pressed") }
            //     ],
            //     { cancelable: false }
            //   );
        }
        else{
            if (connection){
                updateItem(item, dispatch);
                // getDataServer(dispatch);
                // dispatch({type: "UPDATE_ITEM", item});

            }
            else{
                console.warn("Update couldn't be performed");
                customAlert("No internet connection", "Item couldn't be updated, please try again later.");

            }
            navigation.goBack();
        }
    }

    return (
        <CustomForms data={item} finish={finish}/>
    )
}

export default Details
