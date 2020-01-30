const mongoose = require("mongoose");
//const url = "mongodb://localhost:27017/auctionDB";

mongoose.connect("mongodb://localhost:27017/auctionDB", {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true
});

mongoose.Promise = global.Promise;
console.log("Successfully connected to MongoDB server");