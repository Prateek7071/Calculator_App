package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var tvops:TextView?= null
    private var lastNum : Boolean= false
    private var lastDot : Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.tvops =findViewById(R.id.tvops)
    }
     fun onDigit(view: View){
         if (view is Button) {
             tvops?.append((view as Button).text)
             lastNum=true
             lastDot=false
         }
    }

    fun onClear(view:View){
        tvops?.text=""
        lastNum=false
        lastDot=false
    }
    fun onDecimal(view:View){
        if(lastNum && !lastDot){
            tvops?.append(".")
            lastNum=false
            lastDot=true
        }
    }
    fun onOperator(view: View){
        tvops?.text?.let {
            if(lastNum && !isOperatorAdded(it.toString())){
                tvops?.append((view as Button).text)
                lastNum=true
                lastDot=false
            }
        }
    }
    fun onEqual(view: View){
        if(lastNum){
            var tvValue=tvops?.text.toString()
            var prefix=""
            try {
                if(tvValue.startsWith("-"))
                {
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitedValue=tvValue.split("-")
                    var one=splitedValue[0]
                    var two=splitedValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvops?.text=removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                }

                else if(tvValue.contains("+")){
                    val splitedValue=tvValue.split("+")
                    var one=splitedValue[0]
                    var two=splitedValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvops?.text=removeZeroAfterDot((one.toDouble()+two.toDouble()).toString())
                }

                else if(tvValue.contains("*")){
                    val splitedValue=tvValue.split("*")
                    var one=splitedValue[0]
                    var two=splitedValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvops?.text=removeZeroAfterDot((one.toDouble()*two.toDouble()).toString())
                }

                else if(tvValue.contains("/")){
                    val splitedValue=tvValue.split("/")
                    var one=splitedValue[0]
                    var two=splitedValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvops?.text=removeZeroAfterDot((one.toDouble()/two.toDouble()).toString())
                }

            }catch (e:java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String):String{
        var value=result
        val splitednum=result.split(".")
        var dec=splitednum[1]
        if(value.contains(".0") && dec.length<2)
            value=result.substring(0,result.length-2)

        return value

    }
    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    ||value.contains("*")
                    ||value.contains("+")
                    ||value.contains("-")
        }
    }
}