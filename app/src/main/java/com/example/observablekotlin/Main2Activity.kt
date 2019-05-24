package com.example.observablekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        RxBus.listen(UserModel::class.java).subscribe {
            println("nikos CSecond Activity ${it.name} ${it.name} "+ Calendar.getInstance().getTime())
        }
        RxBus.listen(SomeData::class.java).subscribe {
            Log.d("nikos", "Second "+it.name.toString())
            textView2.text = it.name

        }
        button2?.setOnClickListener {

            UserModel.name = Calendar.getInstance().getTime().time .toString()
            RxBus.publish(UserModel)
            RxBus.publish(MainScreenState.Data(SomeData("somedata")))
            RxBus.publish(SomeData("Second"))
        }
    }
}
