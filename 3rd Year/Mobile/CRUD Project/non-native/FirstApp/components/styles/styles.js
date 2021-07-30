import {StyleSheet} from "react-native"


export const styles = StyleSheet.create({
    general:{
        backgroundColor: "#06090F",
        flex: 1,
    },
    fontStyle:{
        color: "#B8B2C3",
        fontSize: 14,
    },
    forms:{
        color: "#B8B2C3",
        fontSize: 16,
        marginHorizontal: 15,
        marginTop: 10,
        marginBottom: 5
    },
    inputs: {
        color: "#B8B2C3",
        fontSize: 16,
        marginHorizontal: 15,
        borderBottomColor: "#665F6B",
        paddingBottom: 5,
        borderBottomWidth: 1,
    },
    header: {
        fontSize: 18,
    },
    headerModal: {
        fontSize: 18,
        color: "#B8B2C3",
        backgroundColor: "#06090F",
        paddingVertical: 10,
        paddingLeft: 20
    },
    modal:{
        backgroundColor: "#06090F",
        // flex: 1,
        paddingTop: 100,
        marginVertical: 50,
        height: 100/1,
    },
    rowAlign: {
        backgroundColor: "#06090F",
        display: "flex",
        flexDirection: "row",
        color: "#B8B2C3",
    },
    modalButtonAlign: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "flex-end",
        backgroundColor: "#06090F",
    },
    modalButton: {
        padding: 20,
        marginHorizontal: 10,
        marginVertical: 5,

    },
    seperatorLine: {
        backgroundColor: "#665F6B",
        height: 1,
        flex: 1,
        marginHorizontal: 50
    },
    floatingAdd: {
        alignSelf: "flex-end",
        alignItems: 'center',
        justifyContent: 'center',
        width: 60,
        height: 60,
        backgroundColor: '#E6DEF4',
        position: 'absolute',
        zIndex: 1000,
        borderRadius: 100,
        right: 30,
        bottom: 40,
        marginTop: "auto",
    },
    item: {
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        marginHorizontal: 5,
        padding: 15, 
    },
    itemProgress:{
        paddingVertical: 15,
        paddingLeft: 5,
    },
    radioButton:{
        height: 24,
        width: 24,
        borderRadius: 12,
        borderWidth: 2,
        borderColor: "#B8B2C3",
        alignItems: 'center',
        justifyContent: 'center',
        marginHorizontal: 20,
        marginVertical: 10
    },
    radioChoice: {
        height: 12,
        width: 12,
        borderRadius: 6,
        backgroundColor: "#B8B2C3",
    },
});