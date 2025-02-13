package com.example.umlandowallet

import android.app.Application
import org.ldk.batteries.ChannelManagerConstructor
import org.ldk.batteries.NioPeerHandler
import org.ldk.structs.*

class Global : Application() {
    companion object {
        @JvmField
        var homeDir: String = ""
        val prefixChannelMonitor = "channel_monitor_"
        val prefixChannelManager = "channel_manager"
        val prefixNetworkGraph = "network_graph.bin"


        val feerateFast = 7500; // estimate fee rate in BTC/kB
        val feerateMedium = 7500; // estimate fee rate in BTC/kB
        val feerateSlow = 7500; // estimate fee rate in BTC/kB

        var eventsTxBroadcast: Array<String> = arrayOf<String>()
        var eventsPaymentSent: Array<String> = arrayOf<String>()
        var eventsPaymentPathFailed: Array<String> = arrayOf<String>()
        var eventsPaymentFailed: Array<String> = arrayOf<String>()
        var eventsPaymentReceived: Array<String> = arrayOf<String>()
        var eventsFundingGenerationReady: Array<String> = arrayOf<String>()
        var eventsPaymentForwarded: Array<String> = arrayOf<String>()
        var eventsChannelClosed: Array<String> = arrayOf<String>()
        var eventsRegisterTx: Array<String> = arrayOf<String>()
        var eventsRegisterOutput: Array<String> = arrayOf<String>()

        var refundAddressScript = ""

        var channelManager: ChannelManager? = null
        var keysManager: KeysManager? = null
        var chainMonitor: ChainMonitor? = null
        var temporaryChannelId: ByteArray? = null
        var channelManagerConstructor: ChannelManagerConstructor? = null
        var nioPeerHandler: NioPeerHandler? = null
        var peerManager: PeerManager? = null

        var router: NetworkGraph? = null
        var txFilter: Filter? = null

    }
}