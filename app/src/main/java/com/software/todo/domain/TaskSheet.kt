@file:Suppress("SpellCheckingInspection")

package com.software.todo.domain

import androidx.compose.runtime.Immutable

@Immutable // @Immutable indica para o compilador do Compose que essa classe nunca irá mudar os valores
sealed class TaskSheet { // Define uma classe selada chamada TaskSheet.
                         // Classes seladas permitem restringir a hierarquia de classes, ou seja,
                         // TaskSheet só pode ter subclasses declaradas dentro do mesmo arquivo

    data object Insert : TaskSheet() // Representa um estado específico de TaskSheet chamado Insert.
                                     // Como um `data object`, ele é um singleton que pode ser usado
                                     // para indicar uma ação de inserção de tarefa.

    data class Update(val task: Task) : TaskSheet() // Representa o estado Update de TaskSheet.
                                                    // Como `data class`, contém dados específicos,
                                                    // nesse caso, uma tarefa (task) que será usada
                                                    // para atualizar informações.

    data object Closed : TaskSheet() // Representa o estado Closed de TaskSheet.
                                     // É um `data object` singleton, usado
                                     // para indicar que editor de tarefa
                                     // está fechado

}