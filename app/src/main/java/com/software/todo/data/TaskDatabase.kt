@file:Suppress("SpellCheckingInspection")

package com.software.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.software.todo.domain.Task

// @Database define esta classe como um banco de dados Room com a entidade `Task` e a versão 1
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    // Fornece acesso ao DAO `TaskDao` para operações no banco de dados
    abstract fun dao(): TaskDao

    // No Kotlin, o companion object é uma maneira de definir membros estáticos dentro
    // de uma classe. Isso significa que os métodos e propriedades definidos
    // dentro do companion object pertencem à classe em si,
    // e não a uma instância específica dessa classe.
    companion object {

        // Função para obter uma instância de `TaskDao` utilizando um singleton do banco de dados
        fun getInstance(context: Context): TaskDao {
            return Room.databaseBuilder(context, TaskDatabase::class.java, "tasks")
                .fallbackToDestructiveMigration() // Permite migrações destrutivas
                .build() // Constroi o banco de dados
                .dao() // Retorna o DAO `TaskDao` para interagir com o banco de dados
        }

    }

}