import Realm from "realm";

const GenreSchema = {
    name: "Genres",
    primaryKey: "name",
    properties: {
        name: "string"
    }
}

const ItemSchema = {
    name: "Items", 
    primaryKey: "id",
    properties: {
        id: "int", 
        title: "string",
        year: "int", 
        genres: "Genres[]", 
        status: "int", 
        stoppedAt: "string", 
        episode: "int", 
        season: "int"
    }
}

export const realmObj = Realm.open({schema: [GenreSchema, ItemSchema],
    schemaVersion: 1, 
    migration: (oldRealm, newRealm) => {},
})
