import React from 'react'
import { BoardContext } from './helpers/Context'

const Board = () => {
    const {elements, setSelected} = React.useContext(BoardContext);

    const clickHandler = e =>{
        const target = e.target;
        setSelected(target.id);
    }

    return (
        <div className="board" onClick={clickHandler}>
            {elements.map(elem => elem.display())}
        </div>
    )
}

export default Board;