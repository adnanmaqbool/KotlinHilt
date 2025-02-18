package com.adnan.kotlinhilt

import com.adnan.kotlinhilt.services.MusicPlayerService


import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.adnan.kotlinhilt.services.Track
import com.adnan.kotlinhilt.services.songs

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MusicActivity : ComponentActivity() {

    private val isPlaying = MutableStateFlow(false)
    private val maxDuration = MutableStateFlow(0f)
    private val currentDuration = MutableStateFlow(0f)
    private val currentTrack = MutableStateFlow(Track())

    private var service: MusicPlayerService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            service = (binder as MusicPlayerService.MusicBinder).getService()
            binder.setMusicList(songs)
            lifecycleScope.launch {
                binder.isPlaying().collectLatest { isPlaying.value = it }
            }
            lifecycleScope.launch {
                binder.maxDuration().collectLatest { maxDuration.value = it }
            }
            lifecycleScope.launch {
                binder.currentDuration().collectLatest { currentDuration.value = it }
            }
            lifecycleScope.launch {
                binder.getCurrentTrack().collectLatest { currentTrack.value = it }
            }
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music) // Replace with your XML layout file
        requestNotificationPermission()

        val playButton: ImageButton = findViewById(R.id.btn_play)
        val stopButton: ImageButton = findViewById(R.id.btn_stop)
        val prevButton: ImageButton = findViewById(R.id.btn_prev)
        val nextButton: ImageButton = findViewById(R.id.btn_next)
        val btnStopActual: ImageButton = findViewById(R.id.btn_stop_actual)
        val songImage: ImageView = findViewById(R.id.img_album)
        val songTitle: TextView = findViewById(R.id.tv_song_title)
        val seekBar: SeekBar = findViewById(R.id.seekbar)
        val currentTime: TextView = findViewById(R.id.tv_current_time)
        val maxTime: TextView = findViewById(R.id.tv_total_time)

        playButton.setOnClickListener {
            val intent = Intent(this, MusicPlayerService::class.java)
            startService(intent)
            bindService(intent, connection, BIND_AUTO_CREATE)
        }

        stopButton.setOnClickListener {
            val intent = Intent(this, MusicPlayerService::class.java)
            stopService(intent)
            unbindService(connection)
        }
        btnStopActual.setOnClickListener{
            service?.stopMusic()

        }

        prevButton.setOnClickListener { service?.prev() }
        nextButton.setOnClickListener { service?.next() }
        nextButton.setOnClickListener { service?.next() }

        lifecycleScope.launch {
            currentTrack.collectLatest {
                runOnUiThread {
                    songImage.setImageResource(it.image)
                    songTitle.text = it.name
                }
            }
        }

        lifecycleScope.launch {
            maxDuration.collectLatest {
                runOnUiThread {
                    seekBar.max = it.toInt()
                    maxTime.text = (it / 1000).toString()
                }
            }
        }

        lifecycleScope.launch {
            currentDuration.collectLatest {
                runOnUiThread {
                    seekBar.progress = it.toInt()
                    currentTime.text = (it / 1000).toString()
                }
            }
        }
    }
}
