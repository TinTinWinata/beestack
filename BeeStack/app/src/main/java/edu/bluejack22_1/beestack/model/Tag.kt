package edu.bluejack22_1.beestack.model;

import edu.bluejack22_1.beestack.`interface`.GetHashMap

class Tag(name:String,description:String) : GetHashMap{

    var name :String = name;
    var description :String = description;

//    Static Variable
//    Companion -> create new instance automatic without using keyword new
    companion object{
        val TAG_FROM_DETAIL = 1
        val TAG_FROM_HOME = 0;
    }

    override fun getHashMap(): HashMap<String, String> {
        return hashMapOf(
            "name" to this.name,
            "description" to this.description,
        )
    }


}

