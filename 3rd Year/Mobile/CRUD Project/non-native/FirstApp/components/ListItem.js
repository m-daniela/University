import React, {useContext, useState} from 'react'
import {View, Text, TouchableOpacity, Animated, Alert} from 'react-native'
import Swipeable from 'react-native-gesture-handler/Swipeable';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { NetworkContext } from './Context/NetworkProvider';
import { getDataServer, removeItem } from './data/Database';
import { DataContext } from "./Context/DataContext";
import {styles} from "./styles/styles";
import { customAlert } from './constants/Constants';

const ListItem = ({item, navigation}) => {
  const savedGenres = item.genres.length !== 0? item.genres.reduce((acc, e) => `${acc}, ${e}`) : "";

  const {dispatch, connection} = useContext(DataContext);
  // const {connection} = useContext(NetworkContext);


  const showDetails = () =>{
    navigation.navigate('Details', {item});
  }

  // fix this
  const colors = {
    0: "#026e71",
    1: "#b90b2a",
    2: "#f9a425"
  };

  const alertMessage = () =>{
    const deleteItem = () => {
      if (connection){
        // dispatch({type: "REMOVE_ITEM", id: item.id});
        removeItem(item.id, dispatch);
        // getDataServer(dispatch);
      }
      else{
        console.log("Delete couldn't be performed");
        customAlert("No internet connection", "Delete couldn't be performed");
      }
    }
    // customAlert("Delete", `Are you sure you want to delete ${item.title}?`);
    
    Alert.alert(
      "Delete",
      `Are you sure you want to delete ${item.title}?`,
      [
        { text: "Close", onPress: () => console.log("Close Pressed") },
        { text: "Delete", onPress: () => deleteItem(item.id) }
      ],
      { cancelable: false }
    );
  }

  

  const rightSwipe = (progress, dragX) => {

      return (<TouchableOpacity onPress={()=> alertMessage()} activeOpacity={0.6}>
          <View>
            <Animated.View style={{alignItems: 'center', justifyContent: 'center', paddingVertical: 20, paddingHorizontal: 10}}>
              <Icon name="delete-outline" size={30} color="#B8B2C3"/>

            </Animated.View>
          </View>
        </TouchableOpacity>
      );
    };


    return (
        <Swipeable renderRightActions={rightSwipe}>
            <TouchableOpacity style={styles.item} onPress={showDetails}>
                <View>
                    <Text style={{...styles.fontStyle, ...styles.header, color: colors[item.status]}}>{item.title} - {item.year}</Text>
                    <Text style={{...styles.fontStyle, color: colors[item.status]}}>{savedGenres}</Text>
                </View>
                <Text style={{...styles.itemProgress, ...styles.fontStyle, color: colors[item.status]}}>{item.stoppedAt}/{item.episode}/{item.season}</Text>
            </TouchableOpacity>
        </Swipeable>
    )
}


export default ListItem
