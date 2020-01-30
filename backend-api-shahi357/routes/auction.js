const express = require("express");
const router = express.Router();
const Auction = require("../models/auction");

//const mongoose = require("mongoose");
const auth = require('../middleware/auth');
const path = require("path");

const multer = require("multer");
const storage = multer.diskStorage({
    destination: "./public/images",
    filename: (req, file, callback) => {
        let ext = path.extname(file.originalname);
        callback(null, `${file.fieldname}-${Date.now()}${ext}`);
    }
});

const imageFileFilter = (req, file, cb) => {
    if (!file.originalname.toLowerCase().match(/\.(jpg|jpeg|png|gif)$/)) {
        return cb(new Error("You can upload only image files!"), false);
    }
    cb(null, true);
};

const upload = multer({
    storage: storage,
    fileFilter: imageFileFilter
})

router.post('/uploadimg', upload.single('imageFile'), (req, res) =>{
  res.json(req.file.fieldname);
  console.log(req.file.fieldname)  
});

router.post("/registerauction", (req, res) => {
    var title = req.body.title;
    var minimal_price = req.body.minimal_price;
    var auctionIssuestime = req.body.auctionIssuestime;
    var auctionEndtime = req.body.auctionIssuestime;
    var auction_photo = req.body.auction_photo;
    var description = req.body.description;

    const auction = new Auction({
        'title': title,
        'minimal_price':minimal_price,
        'auctionIssuestime':auctionIssuestime,
        'auctionEndtime': auctionEndtime,
        'auction_photo': auction_photo,
        'description': description
    })
   
    auction
    .save()
    .then(result => {
      res.status(201).json("Auction Registered Successfully!!");
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        message: err
      });
    });
})

// router.get('/all', function (req, res) {
//     Auction.find().then(function (auction) {
//         res.send(auction);
//     }).catch(function (e) {
//         res.send(e)
//     });
// });

// router.get('/getselectedauction/:id', function (req, res) {
//     uid = req.params.id.toString();
//     Auction.findById(uid).then(function (auction) {
//         res.send(auction);
//     }).catch(function (e) {
//         res.send(e)
//     });
// });

module.exports = router;