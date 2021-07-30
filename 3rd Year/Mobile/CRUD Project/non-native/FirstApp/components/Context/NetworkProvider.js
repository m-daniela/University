import React, {useState, useEffect} from 'react';
import {useNetInfo} from "@react-native-community/netinfo";
import { getDataLocal, readLocal } from '../data/Database';

export const NetworkContext = React.createContext();


const NetworkProvider = ({children}) => {
    const netInfo = useNetInfo();

    const [connection, setConnection] = useState(true);
    
    useEffect(() => {
      setConnection(netInfo.isConnected);
      if(netInfo.isConnected) readLocal();
    }, [netInfo.isConnected]);

    
    return (
        <NetworkContext.Provider value={{connection}}>
            {children}
        </NetworkContext.Provider>
    )
}

export default NetworkProvider
