import React, {useContext, useEffect} from 'react'
import {View, FlatList, ActivityIndicator, Text, Button, TouchableOpacity} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';

import ListItem from './ListItem';
import { DataContext } from "./Context/DataContext";
import {styles} from "./styles/styles";
import { getDataServer, getDataLocal } from "./data/Database";
import { NetworkContext } from './Context/NetworkProvider';

const Home = ({navigation}) => {

  const {items, connection} = useContext(DataContext);
  // const {connection} = useContext(NetworkContext);

  // useEffect(() =>{
  //   if (connection){
  //     getDataServer(dispatch);
  //   }
  //   else{
  //     getDataLocal(dispatch);
  //   }
  // }, [connection]);
  

  const addItem = () =>{
    navigation.navigate("Add");
  }

  return (
    
      <View style={styles.general}>
        {connection ? <></> : 
          <View style={{height: 28, backgroundColor: "#108c02", justifyContent: "center"}} >
            <Text style={{textAlign: "center", color: "white"}}>No internet connection.</Text>
          </View>}

        <TouchableOpacity onPress={addItem} style={styles.floatingAdd}>
          <Icon name="add" size={40} color="#665F6B"/>
        </TouchableOpacity>

        {items.length === 0 ? 
        <View style={{flex: 1, justifyContent: "center"}}>
          <ActivityIndicator color="#665F6B" size="large" />
        </View> 
        : 
        <FlatList
          data={items}
          renderItem={(item) => <ListItem navigation={navigation} item={item.item}/>}
          keyExtractor={item => item.id.toString()}
          ItemSeparatorComponent={() => {
            return <View style={styles.seperatorLine}></View>;
          }}
        />
        
        }
        
      </View>
  )
}


export default Home
