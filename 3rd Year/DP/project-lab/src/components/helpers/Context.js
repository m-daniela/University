import React, {useReducer, createContext} from 'react';
import { Iterator } from '../iterator/Iterator';
import { commands } from './Constants';


export const BoardContext = createContext();

const reducer = (state, action) => {
    switch(action.type){
        case (commands.add): 
            return [...state, action.item];
        case (commands.delete): 
            return state.filter(element => +element.id !== +action.item);
        default: 
            return state;
    }
}


const BoardProvider = (props) => {
    const state = [];
    const [elements, dispatch] = useReducer(reducer, state);
    const [selected, setSelected] = React.useState(0);

    const getElement = id =>{
        const iterator = new Iterator(elements.slice(0, elements.length));
        let current = iterator.first();
        if (+current?.id !== +id){
            while (iterator.hasNext()){
                const next = iterator.next();
                if(+next.id === +id){
                    return next;
                }
            }
        }
        else{
            return current;
        }
        
        // let current = null;
        // elements.forEach(elem => {
        //     console.log(elem)
        //     if (+elem.id) === +id)){
        //         current = elem;
        //     }
        // })
        // return current;
    }

    return (
        <BoardContext.Provider value={{elements, dispatch, selected, setSelected, getElement}}>
            {props.children}
        </BoardContext.Provider>
    )
}

export default BoardProvider;