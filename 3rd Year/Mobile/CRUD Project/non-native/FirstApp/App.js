/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import Home from "./components/Home";
import Details from "./components/Details";
import AddItem from "./components/AddItem";
import { getGenres } from "./components/data/Database";
import NetworkProvider from './components/Context/NetworkProvider';
import DataProvider from './components/Context/DataContext';


const Stack = createStackNavigator();

const App = () => {

  getGenres();

  return (
    <NetworkProvider>
      <DataProvider>
        <NavigationContainer >
          <Stack.Navigator screenOptions={{headerStyle: {backgroundColor: "#0B0B0B"}, headerTintColor: "#EFEFEF"}} initialRouteName="kinolist">
            <Stack.Screen name="kinolist" component={Home} />
            <Stack.Screen name="Details" component={Details} />
            <Stack.Screen name="Add" component={AddItem} />
          </Stack.Navigator>
        </NavigationContainer>
      </DataProvider>
    </NetworkProvider>
  );
};



export default App;
