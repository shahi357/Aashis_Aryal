const mongoose = require("mongoose");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const Schema = mongoose.Schema;

const userSchema = new Schema(
    {
        fullname: {
            type: String,
            required: true
        },
        address: {
            type: String,
            required: true
        },
        mobile_number: {
            type: String,
            required: true
        },
        email: {
            type: String,
            required: true
        },
        password: {
            type: String,
            required: true
        },
        user_profile: {
            type: String
        },
        user_type: {
            type: String
        },

        tokens: [{
            token: {
                type: String,
                required: true
            }
        }]
});

userSchema.pre("save", async function(next){
    if(!this.isModified("password"))return next();

    this.password = await bcrypt.hash(this.password, 12);
})

userSchema.statics.checkCredentialsDb = async (email, password) => {

    const user1 = await User.findOne({ email: email, password: password })
    return user1;

}

userSchema.methods.generateAuthToken = async function () {

    console.log("token");

    const user = this
    const token = jwt.sign({ _id: user._id.toString() }, 'tokens')

    console.log(token);
    user.tokens = user.tokens.concat({ token: token })
    await user.save()
    return token
}


const User = mongoose.model("User", userSchema);
module.exports = User;
