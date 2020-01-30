const express = require("express");
const app = express();
const morgan = require("morgan");
const bodyParser = require("body-parser");
const mongoose = require("./Database/mongoose");
const uploadRouter = require('./routes/upload');


const userRoute = require('./routes/users');
const auctionRoute = require('./routes/auction');


app.use(morgan('dev'));
app.use(express.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());
app.use(express.static(__dirname + "/public"));

const cors = require("cors");
app.use(cors());

app.use('/users', userRoute);
app.use('/fileUpload', uploadRouter);
app.use('/auctions', auctionRoute);

//error handling
app.use((req, res, next) => {
    const error = new Error("Not found");
    error.status = 404;
    next(error);
  });
  
  app.use((error, req, res, next) => {
    res.status(error.status || 500);
    res.json({
      error: {
        message: error.message
      }
    });
  });

module.exports = app;