import React, {useReducer, useState, useEffect} from 'react';

import {useNetInfo} from "@react-native-community/netinfo";
import { getDataLocal, getDataServer, readLocal } from '../data/Database';

export const DataContext = React.createContext();

const dataReducer = (state, action) =>{
    switch(action.type){
        case "LOAD_DATA": 
            return action.newItems;
        case "ADD_ITEM":
            return [...state, action.item];
        case "REMOVE_ITEM":
            return state.filter(item => item.id !== action.id);
        case "UPDATE_ITEM":
            return state.map(item => (item.id === action.item.id ? item = action.item : item));
        default:
            return state;
    }
}


const DataProvider = (props) =>{
    const netInfo = useNetInfo();
    const [items, dispatch] = useReducer(dataReducer, []);
    const [connection, setConnection] = useState(true);
    
    useEffect(() => {
      setConnection(netInfo.isConnected);
      if (netInfo.isConnected){
        readLocal().then(_ => getDataServer(dispatch));
      }
      else{
        getDataLocal(dispatch);
      }
    }, [netInfo.isConnected]);

    return(
      <DataContext.Provider value={{items, dispatch, connection}}>
          {props.children}
      </DataContext.Provider>
    )
}

export default DataProvider;