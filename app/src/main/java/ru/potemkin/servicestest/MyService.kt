package ru.potemkin.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Message
import android.util.Log
import kotlinx.coroutines.*

class MyService: Service() {

    private val coroutineScopre= CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("OnCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScopre.cancel()
        log("OnDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("OnStartCommand")
        coroutineScopre.launch {  for(i in 0 until 100){
            delay(1000)
            log("Timer $i")
        } }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String){
        Log.d("SERVICE_TAG","MyService: $message")
    }

    companion object{

        fun newIntent(context: Context):Intent{
            return Intent(context,MyService::class.java)
        }
    }
}