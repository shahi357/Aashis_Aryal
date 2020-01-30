const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const User = require("../models/user");

//route for registering users
router.post("/register", (req, res) => {
    User.find({ email: req.body.email })
    .exec()
    .then(user => {
      if (user.length >= 1) {
        res.status(201).json({
          message_error: "Mail already exists"
        });
      } else {
        const user = new User({
          fullname: req.body.fullname,
          address: req.body.address,
          mobile_number: req.body.mobile_number,
          email: req.body.email,
          password: req.body.password,
          user_profile: req.body.user_profile,
          user_type: "user"
        });
        user
          .save()
          .then(result => {
            res.status(201).json({
              message_success: "Register Successful"
            });
          })
          .catch(err => {
            console.log(err);
            res.status(500).json({
              message: err
            });
          });
      }
    })
    .catch(err => {
      res.status(500).json({
        message: err
      });
    });
});

//route for getting all users
router.get("/getAllUser", (req, res) => {
    User.find({ user_type: "user" })
    .then(function(user) {
        res.send(user)
    })
    .catch(function(e) {
        res.send(e);
    });
});

//route for user login
router.post("/userLogin", async function (req, res) {
  var askeduserEmail = req.body.email;
  var askeduserPassword = req.body.password;
  console.log(askeduserEmail + " - email data")
  const users = await User.checkCredentialsDb(askeduserEmail, askeduserPassword);

  if (users != null) {
      const token = await users.generateAuthToken();
      console.log(token);
      res.status(201).json({
          token: token,
          users: users,
          message: "Sucess"
      });
  } else {
      res.json({
          message: "Invalid! Login Denied!!"
      })
  }
})

// router.get('/this', Auth, function (req, res) {
//   console.log("responding")
//   res.send(req.users);
// })

  
module.exports = router;