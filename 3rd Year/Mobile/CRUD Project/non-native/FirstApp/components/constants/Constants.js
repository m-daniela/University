import {View, Text, TouchableOpacity, Animated, Alert} from 'react-native'

// server communication constants
const host = "localhost";
const port = "5000";
export const baseUrl = `http://${host}:${port}/`;
export const postUrl = `${baseUrl}add`;
export const deleteUrl = (id) => `${baseUrl}delete/${id}`;
export const putUrl = (id) => `${baseUrl}update/${id}`;
export const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Cache-Control': 'no-cache'
};

// alert
export const customAlert = (title, text) => {
    Alert.alert(
        title,
        text,
        [
          { text: "OK", onPress: () => console.log("OK Pressed") }
        ],
        { cancelable: false }
      );
}
