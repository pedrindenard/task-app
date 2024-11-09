package com.software.todo

import androidx.activity.ComponentActivity

//sealed class Operation {
//
//    data class Plus(val result: Int) : Operation()
//    data class Multiply(val result: Int) : Operation()
//
//    data object Nothing : Operation()
//
//}
//
//data class Numbers(
//    val a: Int,
//    val b: Int
//)
//
//open class Calculator {
//
//    private var lastOperation: Operation = Operation.Nothing
//
//    private fun multiply(a: Int, b: Int): Int {
//        lastOperation = Operation.Multiply(a * b)
//        return a * b
//    }
//
//    fun plus(a: Int, b: Int): Int {
//        lastOperation = Operation.Plus(a + b)
//        return a + b
//    }
//
//    fun plus(numbers: Numbers): Int {
//        lastOperation = Operation.Plus(numbers.a + numbers.b)
//        return numbers.a + numbers.b
//    }
//
//}
//
//class DynamicCalculator : Calculator() {
//
//    fun complexCalculation(a: Int, b: Int): Int {
//        return plus(a, b)
//    }
//
//}
//
//fun main() {
//    val calculator = Calculator() // Crio uma instância da minha classe
//
//    // variáveis genéricas
//
//    val result = calculator.plus(4, 8) // Método público da minha classe
//    print("Resultado: $result")
//
//    // variáveis complexas (objetos)
//
//    val numbers = Numbers(4, 8)
//    print("Resultado: ${calculator.plus(numbers = numbers)}") // Método público da minha classe
//
//    val dynamicCalculator = DynamicCalculator()
//    dynamicCalculator.complexCalculation(4, 8)
//}
//
//fun calculate(a: Int, b: Int, result: (Int) -> Unit) {
//    result(a + b)
//}
//
//fun main() {
//    calculate(4, 8) { result ->
//        print("Resultado: $result")
//    }
//}
//
//open class BaseActivity : ComponentActivity() {
//
//    open fun setContent() {
//        print("BaseActivity")
//    }
//
//}
//
//class MyActivity : BaseActivity() {
//    override fun setContent() {
//        print("MyActivity")
//    }
//}