import { Note, Image, Group } from "../model/Model";
import { getId } from "../helpers/Constants";

// factory for simple components
const createImage = (url) =>{
    const id = getId();
    return new Image(id, url);
}


const createNote = () => {
    const id = getId();
    return new Note(id);
}

const createGroup = (group) => {
    const id = getId();
    return new Group(id, group);
}

export const simpleComponents = {
    createImage,
    createNote,
    createGroup,
}