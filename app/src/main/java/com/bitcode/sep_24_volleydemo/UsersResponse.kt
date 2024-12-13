package com.bitcode.sep_24_volleydemo

import com.google.gson.annotations.SerializedName

data class UsersResponse(

    var page : Int,

    @SerializedName("per_page")
    var perPage : Int,

    var total : Int,

    @SerializedName("total_pages")
    var totalPages : Int,

    var data : ArrayList<User>,

    var support : Support
)
{
    override fun toString(): String {
        return "page = $page"
    }
}

data class Support(
    var url : String,
    var text : String
)
