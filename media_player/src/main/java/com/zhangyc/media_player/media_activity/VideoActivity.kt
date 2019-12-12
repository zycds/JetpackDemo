package com.zhangyc.media_player.media_activity

import android.content.Context
import android.util.Log
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseActivity
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.VideoActivityContact
import io.vov.vitamio.MediaPlayer
import io.vov.vitamio.Vitamio
import io.vov.vitamio.widget.MediaController
import io.vov.vitamio.widget.VideoView
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : BaseActivity<VideoActivityContact.VideoPresenter>(), MediaPlayer.OnPreparedListener
    , MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private val testUrl = "http://qiubai-video.qiushibaike.com/91B2TEYP9D300XXH_3g.mp4"
    private val testUrl2 = "http://bmob-cdn-5540.b0.upaiyun.com/2016/09/09/d0fff44f40ffbc32808db91e4d0e3b4f.mp4"

    @InjectPresenter
    lateinit var videoPresenter : VideoActivityContact.VideoPresenter

    override fun init() {
        setContentView(R.layout.activity_video)
    }

    override fun initData() {
        val initialized = Vitamio.isInitialized(application)
        Log.d(tag , "initialized : $initialized")
        if (!initialized) return

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        videoView.setOnPreparedListener(this)
        videoView.setOnCompletionListener(this)
        videoView.setOnErrorListener(this)

        videoView.setVideoPath(testUrl2)
    }

    override fun refreshData() {
    }

    override fun unInit() {

    }

    override fun back(): Boolean {
       return false
    }

    override fun getActivityContext(): Context? {
        return this
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(tag, " onPrepared...")
        videoView.start()
        videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0f)
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Lg.debug(tag, "onError, $what")
        return false
    }
}