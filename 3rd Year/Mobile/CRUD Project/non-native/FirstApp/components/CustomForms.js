import React, {useState} from 'react'
import {View, TextInput, Text, Button, TouchableWithoutFeedback, 
    Keyboard, ScrollView, Pressable, Modal, TouchableOpacity} from "react-native"
import ModalGenres from './modals/ModalGenres';
import ModalStatus from './modals/ModalStatus';
import {styles} from "./styles/styles";
import {statusList} from "./data/Database";

const CustomForms = ({data, finish}) => {
    const [newItem, setNewItem] = useState(data);
    const [saves, setSaves] = useState(newItem.genres);
    const [status, setStatus] = useState(newItem.status);


    const [genresModal, setGenresModal] = useState(false);
    const [statusModal, setStatusModal] = useState(false);

    const savedGenres = newItem.genres.length !== 0 ? newItem.genres.reduce((acc, e) => `${acc}, ${e}`) : "";
    
    const saveGenres = () => {
        setNewItem({...newItem, genres: saves});
        setGenresModal(false);
    }
    const saveStatus = () => {
        setNewItem({...newItem, status});
        setStatusModal(false);
    }

    const subtract = (action, value) =>{
        if (value > 1){
            if (action === "episode") setNewItem(prev => ({...prev, episode: value - 1}));
            else setNewItem(prev => ({...prev, season: value - 1}));
        }
    }
    const add = (action, value) => {
        if (action === "episode") setNewItem(prev => ({...prev, episode: value + 1}));
            else setNewItem(prev => ({...prev, season: value + 1}));
    }

    return (<TouchableWithoutFeedback  onPress={Keyboard.dismiss}>
        
        <ScrollView style={{...styles.general, paddingVertical: 15}}>
        <Modal style={styles.modal} visible={genresModal}>
            <Text style={styles.headerModal}>Genres</Text>
            <ModalGenres style={styles.general} saves={saves} setSaves={setSaves}/>
            <View style={styles.modalButtonAlign}>
                <Pressable style={styles.modalButton} onPress={() => setGenresModal(false)}><Text style={styles.fontStyle}>Close</Text></Pressable>
                <Pressable style={styles.modalButton} onPress={saveGenres}><Text style={styles.fontStyle}>Save</Text></Pressable>
            </View>
        </Modal>
        <Modal style={styles.modal} visible={statusModal}>
            <Text style={styles.headerModal}>Status</Text>
            <ModalStatus savedStatus={status} setStatus={setStatus}/>
            <View style={styles.modalButtonAlign}>
                <Pressable style={styles.modalButton} onPress={() => setStatusModal(false)}><Text style={styles.fontStyle}>Close</Text></Pressable>
                <Pressable style={styles.modalButton} onPress={saveStatus}><Text style={styles.fontStyle}>Save</Text></Pressable>
            </View>
        </Modal>
        <Text style={styles.forms}>Title</Text>
        <TextInput style={styles.inputs} onChangeText={(e) => setNewItem(prev => ({...prev, title: e}))} value={newItem.title}/>

        <Text style={styles.forms}>Year</Text>
        <TextInput style={styles.inputs} keyboardType="numeric" onChangeText={(e) => 
            setNewItem(prev => ({...prev, year: e}))} value={newItem.year !== 0 ? newItem.year.toString() : ""}/>

        <Text style={styles.forms}>Genres</Text>
        <Pressable onPress={() => setGenresModal(true)}><Text style={styles.inputs}>{savedGenres}</Text></Pressable>

        <Text style={styles.forms}>Status</Text>
        <Pressable onPress={() => setStatusModal(true)}><Text style={styles.inputs}>{statusList[status]}</Text></Pressable>


        <Text style={styles.forms}>Progress</Text>
        <View style={{...styles.rowAlign, justifyContent: "space-between"}}>
        <View>
            <Text style={styles.forms}>Stopped at</Text>
            <TextInput style={{marginHorizontal: 15, color: "#B8B2C3", paddingVertical: 5}} onChangeText={(e) => setNewItem(prev => ({...prev, stoppedAt: e}))} value={newItem.stoppedAt}/>
        </View>
        <View>
            <Text style={styles.forms}>Episode</Text>
            <View style={styles.rowAlign}>
                <Button color="#06090F" title="-" onPress={() => subtract("episode", newItem.episode)}/>
                    <Text style={styles.forms}>{newItem.episode}</Text>
                <Button color="#06090F" title="+" onPress={() => add("episode", newItem.episode)}/>
            </View>
        </View>
        <View>
            <Text style={styles.forms}>Season</Text>
            <View style={styles.rowAlign}>
                <Button color="#06090F" title="-" onPress={() => subtract("season", newItem.season)}/>
                <Text style={styles.forms}>{newItem.season}</Text> 
                <Button color="#06090F" title="+" onPress={() => add("season", newItem.season)}/>
            </View>
        </View>
    </View>
    
    <TouchableOpacity style={{marginVertical: 20}}>
        <Button color="#06090F" title="Finish" onPress={() => finish(newItem)}/>
    </TouchableOpacity>
    </ScrollView>
    </TouchableWithoutFeedback>
    )
}

export default CustomForms

