package com.ksc.geotasker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity( tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "date_and_time")
    val dateAndTime: Long?,
    @ColumnInfo(name = "location")
    val location: String?,
    @ColumnInfo(name = "completed")
    val completed: Boolean?
)
