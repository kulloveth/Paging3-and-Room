package dev.kulloveth.countriesandlanguages.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.kulloveth.countriesandlanguages.data.db.CalDao

class Repository(private val calDao: CalDao) {

    val flow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        calDao.getALl()
    }.flow


    val cals =calDao.getCal()
}