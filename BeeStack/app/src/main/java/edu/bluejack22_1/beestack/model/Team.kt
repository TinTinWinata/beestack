package edu.bluejack22_1.beestack.model

class Team(var name: String, var description: String ) {

    fun getHashMap() : HashMap<String, String>{
//        Get object map
        return hashMapOf(
            "name" to this.name,
            "description" to this.description,
        )
    }
}