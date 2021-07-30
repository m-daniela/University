
export class Iterator{
    constructor(collection){
        this.visited = [];
        this.stack = collection.reverse().slice(0, collection.length);
    }

    first = () =>{
        return this.stack.length > 0 ? this.stack[this.stack.length - 1] : undefined;
    }

    hasNext = () =>{
        return this.stack.length > 0 || this.stack[this.stack.length - 1]?.getElements !== undefined;
    }

    next = () => {
        if (this.hasNext()){
            const current = this.stack.pop();
            this.visited.push(current);
            if(current?.getElements !== undefined){
                current.getElements().forEach((elem) => this.stack.push(elem));
            }
            return current;
        }
    }

    parentOf = id =>{
        id = +id;
        
        while(this.hasNext()){
            const nextElement = this.next();
            if (nextElement.getElements !== undefined){
                const result = nextElement.getElements().filter(elem => +elem.id === id)
                if (result.length > 0){
                    return nextElement;
                }
            }
        }    
    }

}