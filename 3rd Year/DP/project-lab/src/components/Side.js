import React from 'react'
import { commands } from './helpers/Constants';
import { BoardContext } from './helpers/Context';
import { draggableComponents } from "./factory/DraggableComponents";
import { simpleComponents } from "./factory/SimpleComponents";
import { Iterator } from './iterator/Iterator';

const Side = () => {
    const {dispatch, selected, setSelected, getElement, elements} = React.useContext(BoardContext);
    const [url, setUrl] = React.useState("");
    const [group, setGroup] = React.useState("");
    const [selectedElement, setSelectedElement] = React.useState("");
    const disable = selected === 0;

    // subscribe to see the changes happening to "selected"
    // and update based on the new selected id
    React.useEffect(() => {
        if (selected !== 0 && selected !== undefined){
            const elem = getElement(selected);
            setSelectedElement(elem);
        }
    }, [selected, getElement]);
        

    const add = (item) =>{
        if (selected !== 0 && selectedElement?.elements !== undefined){
            selectedElement.addElement(item);
            setSelected(0);
        }
        else{
            dispatch({type: commands.add, item});
            setSelected(0);
        }
    }

    // simple
    
    const addImage = () =>{
        const item = simpleComponents.createImage(url);
        add(item);
        setUrl("");
    }

    const addNote = () =>{
        const item = simpleComponents.createNote();
        add(item);
    }

    const addGroup = () =>{
        const item = simpleComponents.createGroup(group);
        add(item);
        setGroup("");
    }

    // draggable

    const addDraggableImage = () =>{
        const item = draggableComponents.createImage(url);
        dispatch({type: commands.add, item});
        setUrl("");
    }

    const addDraggableNote = () =>{
        const item = draggableComponents.createNote();
        dispatch({type: commands.add, item});
    }

    const addDraggableGroup = () =>{
        const item = draggableComponents.createGroup(group);
        dispatch({type: commands.add, item});
    }

    const removeItem = () =>{
        try{
            const iterator = new Iterator(elements.slice(0, elements.length));
            const parent = iterator.parentOf(selected);
            console.log(parent)
            if (parent !== undefined){
                parent.removeElement(selected);
            }
            else{
                console.log("elems", elements)
                dispatch({type: commands.delete, item: selected});
            }
            setSelected(0);
        }
        catch(err){
            console.log(err)
        }
    }

    return (
        <div className="side">
            <form className="remove" onSubmit={e => e.preventDefault()}>
                <span>{selectedElement?.name} {selected}</span>
                <button onClick={removeItem} disabled={disable}>Delete</button>
            </form>
            <form className="add_image" onSubmit={e => e.preventDefault()}>
                <button onClick={addImage}>Image</button>
                <button onClick={addDraggableImage}>Draggable image</button>
                <label>
                    URL
                    <input type="text" onChange={(e) => setUrl(e.target.value)} value={url} />
                </label>
            </form>

            <form className="add_note" onSubmit={e => e.preventDefault()}>
                <button onClick={addNote}>Note</button>
                <button onClick={addDraggableNote}>Draggable note</button>
                
            </form>
            <form className="add_group" onSubmit={e => e.preventDefault()}>
                <button onClick={addGroup}>Group</button>
                <button onClick={addDraggableGroup} disabled>Draggable group</button>
                <label>
                    Group name
                    <input type="text" onChange={(e) => setGroup(e.target.value)} value={group}/>
                </label>
            </form>
            
			
        </div>
    )
}

export default Side
