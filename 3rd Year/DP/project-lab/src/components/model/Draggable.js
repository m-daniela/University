
// decorator class
// a wrapper to create a draggable object

export class Draggable{
    constructor(id, item){
        this.id = id;
        this.item = item;
        this.name = `Draggable ${item.name}`
    }

    getName = () =>{
        return this.name;
    }

    display = () =>{
        return <div className="draggable item" id={this.id} key={this.id} draggable={true} onMouseOver={() => this.drag(this.id)}>{this.item.display()}</div>;
    }

    drag = (id) => {
        const element = document.getElementById(id);

        let pos1 = 0;
        let pos2 = 0;
        let pos3 = 0;
        let pos4 = 0;
    
        const dragMouseDown = (e) =>{
            e.preventDefault();
            pos3 = e.clientX;
            pos4 = e.clientY;
            document.onmouseup = closeDragElement;
            document.onmousemove = elementDrag;
        }
    
        const elementDrag = (e) => {
            e.preventDefault();
            pos1 = pos3 - e.clientX;
            pos2 = pos4 - e.clientY;
            pos3 = e.clientX;
            pos4 = e.clientY;
            element.style.top = (element.offsetTop - pos2) + "px";
            element.style.left = (element.offsetLeft - pos1) + "px";
        }
    
        const closeDragElement = () => {
            document.onmouseup = null;
            document.onmousemove = null;
        }
    
        element.onmousedown = dragMouseDown;
    }
}