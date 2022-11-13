package edu.bluejack22_1.beestack.model

import edu.bluejack22_1.beestack.`interface`.GetHashMap
import java.io.Serializable;

class Comment (name: String, value: String) :  Serializable{
    val name : String= name
    val value:String = value;
//    override fun getHashMap(): HashMap<String, String> {
//        return hashMapOf(
//            "name" to this.name,
//            "value" to this.value,
//        )
//    }


}