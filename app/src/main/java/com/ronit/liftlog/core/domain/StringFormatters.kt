package com.ronit.liftlog.core.domain

import androidx.compose.ui.util.fastJoinToString
import kotlin.reflect.KClass



fun String.titlecase():String{

    return this.lowercase().replaceFirstChar { it.titlecase() }
}