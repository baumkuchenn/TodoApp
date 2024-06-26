package com.misoramen.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.misoramen.todoapp.model.TodoDatabase

val DB_NAME = "newtododb"
//fun buildDb(context: Context): TodoDatabase{
//    val db = Room.databaseBuilder(context, TodoDatabase::class.java, DB_NAME)
//        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
//        .build()
//    return db
//}

//Panggil dari method di ToDoDatabase
fun buildDb(context: Context): TodoDatabase {
    val db = TodoDatabase.buildDatabase(context)
    return db
}

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null")
    }
}

//is_done memakai integer karena boolean didalam MySql disimpan dalam bentuk tiny integer
val MIGRATION_2_3 = object: Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 not null")
    }
}

val MIGRATION_3_4 = object: Migration(3,4){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN todo_date INTEGER DEFAULT 0 not null")
    }
}