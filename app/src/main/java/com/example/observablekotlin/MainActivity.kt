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



class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Listen for MessageEvents only
//        RxBus.listen(UserModel::class.java).subscribe {
//
////            setScreenState(MainScreenState.Data(SomeData("nikos")))
////            println("nikos Im a Message event ${it.name} ${it.name}")
//        }
        RxBus.listen(MainScreenState::class.java).subscribe {

            setScreenState(it)
        }

        button?.setOnClickListener {
            UserModel.name =  Calendar.getInstance().getTime().time.toString()
            RxBus.publish(MainScreenState.Error())
            UserModel.name =  Calendar.getInstance().getTime().time.toString()
            RxBus.publish(UserModel)
            val myIntent = Intent(this, Main2Activity::class.java)

            this.startActivity(myIntent)
        }
        loading?.setOnClickListener {
            RxBus.publish(MainScreenState.Loading())
        }
        data?.setOnClickListener {
            RxBus.publish(MainScreenState.Data(SomeData("somedata")))
        }


//        viewState.observe(this, Observer<ViewState> {
//            it?.let { render(it) }
//        })
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
fun setScreenState(screenState: MainScreenState) {
    println("nikos setScreenName")
    when(screenState) {
        is MainScreenState.Error -> { textView?.text = "Error" }
        is MainScreenState.Loading -> {textView?.text = "Loading"}
        is MainScreenState.Data -> {

            textView?.text =  screenState.someData.name
            /* hide loading or error states in the view and display data*/
            //sometextView.text = screenState.someData.name
        }
    }
}

}


sealed class MainScreenState {
    class Error : MainScreenState()
    class Loading : MainScreenState()
    data class Data(val someData: SomeData) : MainScreenState()
}

data class SomeData(var name:String)

