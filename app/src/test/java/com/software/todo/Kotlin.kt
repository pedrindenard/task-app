@file:Suppress("SpellCheckingInspection", "KotlinConstantConditions")

package com.software.todo

import android.app.Activity
import android.os.Bundle

// Classe com tipos de instâncias deferentes
sealed class Operation {

    // Representação de um objeto sem dados, pertencente ao Operation
    data object Idle : Operation()

    // Representação de um objeto com dados do tipo [Int], pertencente ao Operation
    data class Plus(val a: Int, val b: Int) : Operation()

    // Representação de um objeto com dados do tipo [Int], pertencente ao Operation
    data class Minus(val a: Int, val b: Int) : Operation()

}

// Representação de um objeto com dados
data class Numbers(
    val a: Int,
    val b: Int
)

// Uma classe que contém métodos responsável por fazer somas e divisões de valores/números
open class Calculator {

    // Função responsável por somar dois números e retornar a soma
    fun plus(numbers: Numbers): Int { // Declaramos o tipo de retorno da função
        return numbers.a + numbers.b // [return] palavra reservada do kotlin
    }

    // Função responsável por somar dois números do tipo [Int] e retornar a soma
    fun minus(a: Int, b: Int): Int { // Declaramos o tipo de retorno da função
        return a - b // [return] palavra reservada do kotlin
    }

}

// Uma classe que contém métodos responsável por fazer somas e divisões
// dos valores passados no construtor 'Calculate(1,1)'
class Calculate(
    private val a: Int,
    private val b: Int
) {

    // Função responsável por multiplicar dois números do tipo [Int] e retornar a multiplicação
    private fun multiply(a: Int, b: Int): Int { // Declaramos o tipo de retorno da função
        return a * b // [return] palavra reservada do kotlin
    }

    // Função responsável por somar dois números do tipo [Int] e retornar a soma
    fun plus(): Int { // Declaramos o tipo de retorno da função
        return a + b // [return] palavra reservada do kotlin
    }

    // Função responsável por somar dois números e retornar a soma
    fun minus(): Int { // Declaramos o tipo de retorno da função
        return a - b // [return] palavra reservada do kotlin
    }

}

// Classe com herança de [Calculator] com novas funcionalidades da nossa calculadora
// OBS: Agora é possível herdar a [class] 'Calculator' uma vez que ela é aberta
class CalculatorV2 : Calculator() {

    // Função responsável por multiplicar dois números do tipo [Int] e retornar a multiplicação
    fun multiply(a: Int, b: Int): Int { // Declaramos o tipo de retorno da função
        return a * b // [return] palavra reservada do kotlin
    }

    // Função responsável por dividir dois números do tipo [Int] e retornar a divisão
    fun divide(a: Int, b: Int): Int { // Declaramos o tipo de retorno da função
        return a / b // [return] palavra reservada do kotlin
    }

    // Função que faz uma calculo complexo
    fun complexCalculus(a: Int, b: Int): Int { // Declaramos o tipo de retorno da função
        val result1 = multiply(a,b)
        val result2 = minus(result1,b)
        val result3 = divide(result2,a)
        return result3 + result1 // [return] palavra reservada do kotlin
    }

}

// Lambda retornando nada '() -> Unit';
// Lambda requisitando um valor '() -> Int`;
// Lambda retornando um valor '(result: Int) -> Unit`;

// Função que faz uma soma e tem como parâmetro um lambda '(result: Int) -> Unit'
fun calculate1(a: Int, b: Int, result: (result: Int) -> Unit) {
    result(a + b) // Instanciar um lambda passando valores
}

// Função que faz uma soma e tem como parâmetro um lambda '() -> Int'
fun calculate2(a: Int, b: Int, result: () -> Int) {
    result() // Instanciar um lambda sem passar valores
}

// Função que faz uma soma e tem como parâmetro um lambda '() -> Unit'
fun calculate3(a: Int, b: Int, result: () -> Unit) {
    result() // Instanciar um lambda sem passar valores
}

// Activity é uma [class] do Android usada para instanciar screens (telas)
class MyActivity : Activity() {

    // Estamos sobreescrevendo a função aberta 'onCreate' (aqui é onde desenhamos o layout)
    // OBS: override significa que vamos sobreescrever a função
    // que já existe dentro da [class] que herdamos
    override fun onCreate(savedInstanceState: Bundle?) {
        // OBS: super.nomeDaFunção significa que primeiro vamos executar o código 'onCreate'
        // dentro da [class] 'Activity' para depois executar o nosso código
        // que vamos escrever
        super.onCreate(savedInstanceState)
        // código
        // (...)
    }

}

fun main() {
    // Instanciar uma [data class]
    val numbers = Numbers(1,2)

    // Para acessar os valores/métodos da [data class]
    numbers.a
    numbers.b

    // Para instanciar uma [class]
    val calculator = Calculator()

    // Para accessar as funções da [class]
    calculator.plus(numbers)
    calculator.minus(1,2)

    // Para instanciar uma [class] que tem parâmetros no construtor
    val calculate = Calculate(1,2)

    // OBS: métodos/variaveis privadas não é possível acessar fora da [class]
    // calculate.multiply(1,2)

    // Função que tem como parametro um lambda '(result: Int) -> Unit'
    calculate1(1,2) { result ->
        val a = result // Aqui eu consigo acessar o resultado que obtive do calculo da função
    }

    // Função que tem como parametro um lambda '() -> Int'
    calculate2(1,2) {
        1 // No final do bloco, é necessário retornar um valor

        // OBS: é possível retornar um valor assim também: 'return@calculate2 1'
        // mas o compilar é esperto o suficiente e não precisa escrever o return
    }

    // Função que tem como parametro um lambda '() -> Unit'
    calculate3(1,2) {
        // Não retorna nada, logo, apenas executa esse trecho de código
    }
}