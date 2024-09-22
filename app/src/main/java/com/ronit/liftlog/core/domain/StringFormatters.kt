package com.ronit.liftlog.core.domain

import androidx.compose.ui.util.fastJoinToString
import kotlin.reflect.KClass


interface StringFormatter{

    fun format(str:String):String
}


class TitleCaseStringFormatter:StringFormatter{
    override fun format(str: String): String {

        if(str.isBlank()){
            return str
        }

        val list = str.split(" ").toMutableList()

        list[0] = list[0].replaceFirstChar { it.titlecase() }
        return list.fastJoinToString()
    }
}


fun <T:StringFormatter> String.format(formatter:KClass<T>):String{

    val instance = formatter.constructors.first().call()

    return instance.format(this)
}