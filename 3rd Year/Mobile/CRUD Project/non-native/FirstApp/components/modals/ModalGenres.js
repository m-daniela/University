import React, {useState, useContext} from 'react'
import { ScrollView, Pressable, Text } from 'react-native'
import { CheckBox } from 'react-native-elements'
import {genres} from "../data/Database"
import {DataContext} from "../Context/DataContext"
import { styles } from '../styles/styles'


const ModalGenres = ({saves, setSaves}) => {
    // const {dispatch} = useContext(DataContext);
    
    const updateGenres = (checked, item) =>{
        // if (checked){
        //     setSaves([...saves.filter(e => e === item)]);
        // }
        // else{
        //     setSaves([...saves, item]);
        // }
        if (checked){
            setSaves([...saves.filter(e => e !== item)]);
        }
        else{
            setSaves([...saves, item]);
        }

    }

    const genreBox = (genre, index, checked) =>{
        const [isChecked, setIsChecked] = useState(checked);
        return (
            <CheckBox style={styles.general} onPress={() => {updateGenres(isChecked, genre); setIsChecked(!isChecked)}}
                key={index}
                title={genre}
                checked={isChecked}
            />
        );
    }

    return (
        <ScrollView style={{display: "flex", flexDirection: "column", ...styles.general}}>
            {genres.map((item, index) => saves.indexOf(item) !== -1 ? genreBox(item, index, true) : genreBox(item, index, false))}
        </ScrollView>
    )
}

export default ModalGenres
