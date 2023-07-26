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
        val start = intent?.getIntExtra(EXTRA_START,0)?:0
        coroutineScopre.launch {
            for(i in start until start+100){
            delay(1000)
            log("Timer $i")
        } }
        return START_REDELIVER_INTENT
    }
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String){
        Log.d("SERVICE_TAG","MyService: $message")
    }

    companion object{

        private const val EXTRA_START = "start"
        fun newIntent(context: Context,start:Int):Intent{
            return Intent(context,MyService::class.java).apply {
                putExtra(EXTRA_START,start)
            }
        }
    }
}