package edu.bluejack22_1.beestack.model
import java.io.Serializable

class Notification(val from : User, val type: String, val message: String)  {

    fun getHashMap(): HashMap<String, Serializable> {
        return hashMapOf(
            "from" to this.from,
            "type" to this.type,
            "message" to this.message,
        )
    }
}