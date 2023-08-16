package com.task.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.task.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var expression: Expression
    private var lastEnteredNumber = false
    private var enteredDot = false
    private var showError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onBtnAllClearClick(view: View) {
        binding.txtDisplay.text = ""
        binding.txtResultDisplay.text = ""
        lastEnteredNumber = false
        enteredDot = false
        showError = false
    }
    fun onBtnClearClick(view: View) {
        binding.txtDisplay.text = ""
        lastEnteredNumber = false
    }


    fun onNumericClick(view: View) {
        if(showError){
            binding.txtDisplay.text = (view as Button).text
            showError = false
        }
        else{
            binding.txtDisplay.append((view as Button).text)
        }
        lastEnteredNumber = true
        onEqual()
    }

    fun onBtnBackspaceClick(view: View) {
        binding.txtDisplay.text = binding.txtDisplay.text.dropLast(1)
        try{
            val lastChar = binding.txtDisplay.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }
        catch (e : Exception){
            binding.txtResultDisplay.text = ""
            Log.e("Last char error", e.toString())
        }
    }

    fun onBtnEqualClick(view: View) {
        onEqual()
        binding.txtDisplay.text  = binding.txtResultDisplay.text.toString().drop(1)
    }

    fun onOperationClick(view: View) {
        if(!showError && lastEnteredNumber){
            binding.txtDisplay.append((view as Button).text)
            enteredDot = false
            lastEnteredNumber = false
            onEqual()
        }
    }

    fun onEqual() {
        if(lastEnteredNumber && !showError){
            val txt = binding.txtDisplay.text.toString()
            expression = ExpressionBuilder(txt).build()
            try{
                val result = expression.evaluate()
                binding.txtResultDisplay.text = "=" + result.toString()
            }
            catch (ex : ArithmeticException){
                Log.e("Error:", ex.toString())
                showError = true
                lastEnteredNumber = false
            }
        }
    }
}