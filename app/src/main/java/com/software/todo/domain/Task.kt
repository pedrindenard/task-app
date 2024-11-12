@file:Suppress("SpellCheckingInspection")

package com.software.todo.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity // @Entity define esta classe como uma entidade de tabela no bd (banco de dados)
data class Task(

    // @ColumnInfo especifica o nome da coluna "title" para o campo `title` no bd
    @ColumnInfo("title")
    val title: String = "",

    // @ColumnInfo especifica o nome da coluna "description" para o campo `description` no bd
    @ColumnInfo("description")
    val description: String = "",

    // @ColumnInfo especifica o nome da coluna "checked" para o campo `checked` no bd
    @ColumnInfo("checked")
    val checked: Boolean = false,

    // @PrimaryKey define `id` como a chave primária, com a geração automática de valores
    @PrimaryKey(true)
    val id: Int = 0

)