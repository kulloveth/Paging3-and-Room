package dev.kulloveth.countriesandlanguages.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

object AppScope : CoroutineScope {

    var context: CoroutineContext = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = context
}