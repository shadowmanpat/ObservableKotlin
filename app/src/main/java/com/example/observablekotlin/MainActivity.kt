package com.example.observablekotlin

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent



class MainActivity : AppCompatActivity() ,MainViewInterface{
    override fun clickErrorIntent() {
        button?.setOnClickListener {
            println("nikos clickErrorIntent")
            RxBus.publish(MainScreenState.Data(SomeData("First")))
        }
    }

    override fun render(state: MainScreenState) {
        currentState = state
         println("nikos setScreenName")
        when(state) {
            is MainScreenState.Error -> { textView?.text = "Error" }
            is MainScreenState.Loading -> {textView?.text = "Loading"}
            is MainScreenState.Data -> {

                textView?.text =  state.someData.name
                /* hide loading or error states in the view and display data*/
                //sometextView.text = screenState.someData.name
            }

        }
    }

    override lateinit var currentState: MainScreenState



    override fun initalizeState() {

        currentState= MainScreenState.Loading()
        RxBus.listen(MainScreenState::class.java).subscribe{
            println("nikos render")
            render(it)
        }
        RxBus.publish(currentState)
        clickDataIntent()
        clickLoadingIntent()
        clickErrorIntent()
    }

    override fun clickDataIntent() {
        data?.setOnClickListener {
            println("nikos clickDataIntent")

            RxBus.publish(MainScreenState.Data(SomeData("button")))
        }
    }

    override fun clickLoadingIntent() {
        loading?.setOnClickListener {
            RxBus.publish(MainScreenState.Loading())

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initalizeState()


    }
    private fun renderName( name: String?){
        Log.d("nikos", name.toString())
        textView?.text = name
    }



//    private fun render(viewState: MainScreenState) {
//        when (viewState) {
//            is MainScreenState.Loading -> textView?.text = "Loading"
//            is MainScreenState.Error  -> textView?.text = "Error"
//            is MainScreenState.Data -> {
//                viewState
//                /* hide loading or error states in the view and display data*/
//                //sometextView.text = screenState.someData.name
//            }
//        }
//
//
////
//    }


}


sealed class MainScreenState {
    class Error : MainScreenState()
    class Loading : MainScreenState()
    data class Data(val someData: SomeData) : MainScreenState()
}
interface MainViewInterface: BindIntends {
    var currentState: MainScreenState
    fun render(state: MainScreenState)
    fun initalizeState()

}
interface BindIntends{
    fun clickDataIntent()
    fun clickLoadingIntent()
    fun clickErrorIntent()
}
data class SomeData(var name:String)
