
const getUrl = "/";
const postUrl = "/add";
const putUrl = "/update/:id";
const deleteUrl = "/delete/:id";

const express = require("express");
const bodyParser = require("body-parser");

const port = process.env.port || 5000;

const app = express();

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());


let movieList = [];


app.get(getUrl, (req, res) => {
    console.log("GET");
    res.json(movieList);
    // console.log(movieList);
});

// add entry
app.post(postUrl, (req, res) => {
    console.log("POST");
    const movie = req.body;

    if (movie.length){
        movieList = movie;
    }
    else{
        movieList.push(movie);
    }
    
    console.log(movie);
    res.json({success: "Entry added"});

});

// update entry
app.put(putUrl, (req, res) => {
    console.log("PUT");
    const id = +req.params.id;
    const movie = req.body;
    movieList = movieList.map(element => element.id === id ? movie : element);
    console.log(movie);
    res.json({success: "Entry updated"});

});

// delete entry
app.delete(deleteUrl, (req, res) => {
    console.log("DELETE");
    const id = +req.params.id;
    const deletedElem = movieList.filter(element => element.id === id)[0];
    movieList.splice(movieList.indexOf(deletedElem), 1);
    console.log(deletedElem);
    res.json({success: "Entry deleted"});
});


app.listen(port, () => console.log(`Server started on port ${port}`));