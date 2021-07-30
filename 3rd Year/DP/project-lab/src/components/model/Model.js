
// the components are defined here
// they are following an interface that defines the "display" method

// image - leaf node
export class Image{
    constructor(id, url){
        this.id = id;
        this.url = url;
        this.name = "Image";
    }

    getName = () =>{
        return this.name;
    }

    display = () => {
        return <img src={this.url} className="item" alt="img" key={this.id} id={this.id} />;
    }
}

// note - leaf node
export class Note{
    constructor(id){
        this.id = id;
        this.name = "Note";
    }

    getName = () =>{
        return this.name;
    }

    display = () => {
        return <textarea className="item" key={this.id} id={this.id} onDoubleClick={(e) => e.target.focus()} placeholder="Your notes..."/>;
    }
}

// group - composite node
export class Group{
    constructor(id, name){
        this.id = id;
        this.name = name === "" ? "Group" : name;
        this.elements = [];
    }

    getName = () =>{
        return this.name;
    }

    addElement = element =>{
        this.elements.push(element);
    }

    removeElement = elementId =>{
        this.elements = this.elements.filter(elem => +elem.id !== +elementId);
    }

    getElements = () => this.elements;

    display = () => {
        return (
            <div className="group item" key={this.id} id={this.id}>
                {this.name}
                {this.elements.map(elem => elem.display())}
            </div>
        );
    }
}