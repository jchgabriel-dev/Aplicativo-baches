package com.romzc.bachesapp.data.entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.romzc.bachesapp.data.model.PotholeDao
import com.romzc.bachesapp.data.model.SeverityDao
import com.romzc.bachesapp.data.model.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class,  PotholeEntity::class, SeverityEntity::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun potholeDao(): PotholeDao
    abstract fun severityDao(): SeverityDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app-database-name"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                    INSTANCE = instance

                }
                return instance
            }
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val severityDao = INSTANCE?.severityDao()
                    severityDao?.addSeverity(SeverityEntity(sevName = "Grave"))
                    severityDao?.addSeverity(SeverityEntity(sevName = "Medio"))
                    severityDao?.addSeverity(SeverityEntity(sevName = "Leve"))
                }
            }
        }

    }
}