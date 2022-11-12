package edu.bluejack22_1.beestack.model

import java.io.Serializable

class User(var uid:String, var username: String, var email: String, var location:String): Serializable {

    companion object{
        fun fromHashMap(hashMap: HashMap<String, String>): User {

            return User(hashMap["uid"]!!, hashMap["username"]!!, hashMap["email"]!!, hashMap["location"]!!);
        }
    }

}