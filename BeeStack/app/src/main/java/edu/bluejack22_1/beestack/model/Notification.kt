package edu.bluejack22_1.beestack.model
import java.io.Serializable

class Notification(val from : User, val type: String, val message: String, val to : User)  {

    fun getHashMap(): HashMap<String, Serializable> {
        return hashMapOf(
            "from" to this.from,
            "to" to this.to,
            "type" to this.type,
            "message" to this.message,
        )
    }
}