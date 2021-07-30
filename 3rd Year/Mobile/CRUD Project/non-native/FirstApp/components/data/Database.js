
import {realmObj} from "../data/Schemas";
import {baseUrl, putUrl, deleteUrl, postUrl, headers, customAlert} from "../constants/Constants";


export const statusList = ["To Watch", "Watching", "Completed"];
export const genres = [];

const convertToDBObject = (item) => {
  let newItem = {
    id: item.id,
    title: item.title,
    year: parseInt(item.year),
    status: item.status,
    genres: [],
    stoppedAt: item.stoppedAt,
    episode: item.episode,
    season: item.season,
  };
  return newItem;
}

// add to server
// an array of items can be sent aswell
const serverAdd = (item) => {
  
  return fetch(postUrl, {
    method: "POST", 
    headers,
    body: JSON.stringify(item)
  })
  .then(res => res.json())
  .then(res => console.log(res.success))
  .catch(err => {console.log(err); console.log("No server connection. Item added locally.")});
}

// add item to local database
// if there is a problem with the connection,
// the item will be added only to the local db
// a warning will appear, but the data will
// be sent to the server when the connection is back
export const addItem = (item, connection, dispatch) => {
  
  realmObj.then(realm => {
    realm.write(() => {
      const localGenres = []
      item.genres.map(genre => {
        localGenres.push(realm.objects("Genres").filtered(`name == "${genre}"`)[0])
      })
      const newItem = realm.create("Items", convertToDBObject(item));
      newItem.genres = localGenres;
    })
  }).catch((error) => console.log(error));

  if (connection){
    serverAdd(item).then(_ => getDataServer(dispatch))
    // getDataServer(dispatch);

  }
  else{
    getDataLocal(dispatch);
    customAlert("No internet connection", "Item added to local database");
    console.log("Item added to local database");
  }
}


// remove the given item
// don't delete if there is no internet 
// connection or there are problems with
// the server 
// a warning will be shown in this case
export const removeItem = (id, dispatch) => {

    fetch(deleteUrl(id), {
      method: "DELETE", 
      headers,
    })
    .then(res => res.json())
    .then(res => console.log(res.success))
    .then(_ => {
      realmObj.then(realm => {
        realm.write(() => {
          const toDelete = realm.objects('Items').filtered(`id == ${id}`);
          realm.delete(toDelete);
        });
        getDataServer(dispatch);

      }).catch((error) => console.log(error));
    })
    .catch(err => {
      console.log(err); 
      console.log("Remove: No server connection");
      customAlert("No server connection", "Item couldn't be removed, please try again later.");
    });
}

// update the given item
// don't update if there is no internet 
// connection or there are problems with
// the server 
// a warning will be shown in this case
export const updateItem = (item, dispatch) => {

    fetch(putUrl(item.id), {
      method: "PUT", 
      headers,
      body: JSON.stringify(item)
    })
    .then(res => res.json())
    .then(res => console.log(res.success))
    .then(res => {
      realmObj.then(realm => {
        const localGenres = [];
    
        item.genres.map(genre => {
          localGenres.push(realm.objects("Genres").filtered(`name == "${genre}"`)[0])
        });
    
        realm.write(() => {
          let toUpdate = realm.objects('Items').filtered(`id == ${item.id}`)[0];
          toUpdate.title = item.title;
          toUpdate.year = parseInt(item.year);
          toUpdate.status = item.status;
          toUpdate.genres = localGenres;
          toUpdate.stoppedAt = item.stoppedAt;
          toUpdate.episode = item.episode;
          toUpdate.season = item.season;
        });
        getDataServer(dispatch);
      }).catch((error) => console.log(error));
    })
    .catch(err => {
      console.log(err); 
      customAlert("No server connection", "Item couldn't be updated, please try again later.");
      console.log("Update: No server connection");
    });
}


export const getDataLocal = (dispatch) => {
  realmObj.then(realm => {
    let niceAndParsed = [];
    
    const dbItems = realm.objects("Items");
    dbItems.map((item) => {
      let niceAndParsedGenres = [];

      item.genres.map((genre) => {
        niceAndParsedGenres.push(genre.name)
      });

      const newItem = convertToDBObject(item);
      newItem.genres = niceAndParsedGenres;

      niceAndParsed.push(newItem);
      
    });
    dispatch({type: "LOAD_DATA", newItems: niceAndParsed});
  })
  .catch((error) => console.log(error));


  
}


// get the data from the server
// if there is no connection with the server, 
// just take the data from the local db
export const getDataServer = (dispatch) => {

  fetch(baseUrl, {
    method: "GET", 
    headers,
  })
  .then(res => res.json())
  .then(res =>  dispatch({type: "LOAD_DATA", newItems: res}))
  .catch(err => {
    console.log(err); 
    getDataLocal(dispatch);
  });

}


// read local db and send data to server
// this is read at app start and the 
// array of genres is saved aswell
export const readLocal = () =>{

  return realmObj.then(realm => {
    let niceAndParsed = [];
    
    const dbItems = realm.objects("Items");
    dbItems.map((item) => {
      let niceAndParsedGenres = [];

      item.genres.map((genre) => {
        niceAndParsedGenres.push(genre.name)
      });

      const newItem = convertToDBObject(item);
      newItem.genres = niceAndParsedGenres;

      niceAndParsed.push(newItem);
      
    });
    // setData(niceAndParsed);
    serverAdd(niceAndParsed);
  })
  .catch((error) => console.log(error));
}

export const getGenres = () =>{
  if (genres.length === 0){
    realmObj.then(realm => {
      const dbGenres = realm.objects("Genres");
      dbGenres.map((item) => {
        genres.push(item.name);
      });
    })
    .catch((error) => console.log(error));
  }
}