const mongoose = require("mongoose");

const auctionSchema = mongoose.Schema({
    title: {
        type: String
    },
    minimal_price: {
        type: String
    },
    auctionIssuestime: {
      type: Date
    },
    auctionEndtime: {
        type: Date
    },
    auction_photo: {
        type: String 
    },
    description: {
        type: String
    }
});

const Auction = mongoose.model("Auction", auctionSchema);
module.exports = Auction;