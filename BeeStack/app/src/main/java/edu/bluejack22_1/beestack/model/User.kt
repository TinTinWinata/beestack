package edu.bluejack22_1.beestack.model

import java.io.Serializable

class User(uid:String, username: String, email: String, location:String): Serializable {

    var uid:String= uid;
    var email:String = email;
    var username:String = username;
    var location:String = location;


}