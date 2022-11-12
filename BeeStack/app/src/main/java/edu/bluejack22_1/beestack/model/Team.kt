package edu.bluejack22_1.beestack.model

class Team(var photoUrl: String = "", var id: String = "", var name: String, var description: String, var motto: String ) {

    fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "name" to this.name,
            "description" to this.description,
            "motto" to this.motto,
            "photo_url" to this.photoUrl
        )
    }
}