package edu.bluejack22_1.beestack.model
import java.io.Serializable

class Notification(val from: User, val type: String, val data: DataInvite, val to: User)  {

    var uid : String? = null



    fun getHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "from" to this.from,
            "to" to this.to,
            "type" to this.type,
            "data" to this.data,
        )
    }

    fun setId(newUid: String){
        this.uid = newUid
    }
}

class DataInvite(val message: String, val teamId: String){

}