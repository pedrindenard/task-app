@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("SpellCheckingInspection")

package com.software.todo.presentation.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.software.todo.domain.Task

private val defaultValue = Task() // Valor inicial de tarefa para o modal de inserção

/* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

@Composable
fun TaskModalBottomSheet(
    text: String,
    task: Task = defaultValue,
    onDismissRequest: () -> Unit,
    onConfirm: (Task) -> Unit
) {
    ModalBottomSheet( // Vai mostrar um dialog sobrepondo o nosso layout
        onDismissRequest = onDismissRequest
    ) { // Por padrão, o conteúdo está dentro de um 'Column'
        var title by remember { mutableStateOf(value = task.title) } // remember significa que quando o
                                                                     // compose recriar a tela quando um
                                                                     // valor alterar, esse se manterá,
                                                                     // ou seja, não vai ser resetado para
                                                                     // o valor padrão
        ModalOutlinedTextField( // Função que criamos anteriormente
            text = "Título",
            onValueChanged = { newValue -> title = newValue }, // Alteramos o valor de 'title'
            value = title // Valor inicial                     // para o recebido (newValue)
        )

        var description by remember { mutableStateOf(value = task.description) } // remember significa que quando o
                                                                                 // compose recriar a tela quando um
                                                                                 // valor alterar, esse se manterá,
                                                                                 // ou seja, não vai ser resetado para
                                                                                 // o valor padrão
        ModalOutlinedTextField( // Função que criamos anteriormente
            text = "Descrição",
            onValueChanged = { newValue -> description = newValue }, // Alteramos o valor de 'description'
            value = description // Valor inicial                     // para o recebido (newValue)
        )

        var checked by remember { mutableStateOf(value = task.checked) } // remember significa que quando o
                                                                         // compose recriar a tela quando um
                                                                         // valor alterar, esse se manterá,
                                                                         // ou seja, não vai ser resetado para
                                                                         // o valor padrão
        ModalCheckBox( // Função que criamos anteriormente
            onValueChanged = { newValue -> checked = newValue }, // Alteramos o valor de 'checked'
            value = checked // Valor inicial                     // para o recebido (newValue)
        )

        ModalButton(text = text) { // Função que criamos anteriormente
            onDismissRequest() // Ao clicar no botão, queremos ocular nosso modal
            onConfirm( // Nova tarefa para inserir ou a tarefa atual com os valores editados
                task.copy(title = title, description = description, checked = checked) // copy é exclusivo de data class
            )
        }

        Spacer( // Layout simples, serve apenas para adicionar espaçamento
            modifier = Modifier.navigationBarsPadding() // Modificador do compose para adicionar o
                                                        // espaço da barra de navegação
        )
    }
}

/* ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- */

@Composable
private fun ModalOutlinedTextField(text: String, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp) // Espaçamentos
            .fillMaxWidth(), // Preenche toda a largura da tela
        onValueChange = onValueChanged, // Ouvinte para saber quando o usuário digita/apaga/cola
        value = value, // Valor inicial do texto digitavel
        label = {
            Text( // Texto para desenhar
                text = text // Texto
            )
        }
    )
}

@Composable
private fun ModalCheckBox(value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Row( // Layout horizontal (linha)
        verticalAlignment = Alignment.CenterVertically // Alinhamento dos componentes interno no centro
    ) {
        Checkbox( // Caixa de seleção
            modifier = Modifier.padding(horizontal = 8.dp), // Espaçamentos
            onCheckedChange = onValueChanged, // Quando ocorre uma ação de clique, ele retorna true ou false
            checked = value // Valor inicial (true ou false)
        )

        Text( // Texto para desenhar
            modifier = Modifier.padding(vertical = 8.dp), // Espaçamentos
            fontWeight = FontWeight.Light, // Estilo de fonte
            text = "Feito" // Texto
        )
    }
}

@Composable
private fun ModalButton(text: String, onClick: () -> Unit) {
    Row( // Layout horizontal (linha)
        modifier = Modifier
            .padding(horizontal = 16.dp) // Espaçamentos
            .fillMaxWidth(), // Preenche toda a largura da tela
        horizontalArrangement = Arrangement.End // Inicio do arranjo no lado direito (end)
    ) {
        Button( // Botão para capturar a ação de click
            onClick = onClick // Ação de click
        ) {
            Text( // Texto para mostrar internamente no botão
                text = text // Texto
            )
        }
    }
}