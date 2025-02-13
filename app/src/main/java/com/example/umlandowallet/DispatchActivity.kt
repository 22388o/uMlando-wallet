package com.example.umlandowallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umlandowallet.data.remote.Service
import kotlinx.coroutines.*
import java.io.File

class DispatchActivity : AppCompatActivity() {

    private val service = Service.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val entropy = "42C3818EC03AE97D74E817B9C6B6D7AA0E382ED9CA286CF4C0CDB43EF3C8D07B"
        var latestBlockHash = ""
        var latestBlockHeight = 0

        // Probably a cleaner way to do this!
        runBlocking {
            latestBlockHash = service.getlatestBlockHash()
            latestBlockHeight = service.getlatestBlockHeight()

        }

        Global.homeDir = filesDir.absolutePath + "/uMlando"
        val directory = File(Global.homeDir)
        if(!directory.exists()) {
            directory.mkdir()
        }

        // Initialize the LDK data directory if necessary.
        Global.homeDir += "/" + sha256(sha256(entropy)).substring(0, 8)
        val ldkDataDirectory = File(Global.homeDir)
        if(!ldkDataDirectory.exists()) {
            ldkDataDirectory.mkdir()
        }

        var serializedChannelManager = ""
        var serializedChannelMonitors = ""
        var monitors = arrayOf<String>()

        File(Global.homeDir).walk().forEach {
            if(it.name.startsWith(Global.prefixChannelManager)) {
                serializedChannelManager = it.absoluteFile.readText(Charsets.UTF_8)
            }
            if(it.name.startsWith(Global.prefixChannelMonitor)) {
                val serializedMonitor = it.absoluteFile.readText(Charsets.UTF_8);
                monitors = monitors.plus(serializedMonitor)
            }
        }

        serializedChannelMonitors = monitors.joinToString(separator = ",")

        println("serializedChannelManager: $serializedChannelManager")
        println("serializedChannelMonitors: $serializedChannelMonitors")

        start(entropy, latestBlockHeight, latestBlockHash, serializedChannelManager, serializedChannelMonitors)

        startActivity(Intent(this, MainActivity::class.java))
    }
}