package com.example.newsly

object ColorPicker {
    val colors=
        arrayOf("#fcba03" , "#fcba03" ,"#a5cc6e","#549639","#f5ea4c","#549639","#3342c4","#b17feb","#e069dc","#e06983","#4a2f2f" )
    var colorIndex = 1
    fun getColor(): String{
        return colors[colorIndex++ % colors.size]
    }
}