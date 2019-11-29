package com.zhangyc.media_player.fragment

import android.content.Context
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.media_player.ConstantKey
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.VideoContact
import kotlinx.android.synthetic.main.fragment_music.fast_forward
import kotlinx.android.synthetic.main.fragment_music.fast_rewind
import kotlinx.android.synthetic.main.fragment_music.playing
import kotlinx.android.synthetic.main.fragment_music.skip_next
import kotlinx.android.synthetic.main.fragment_music.skip_prev
import kotlinx.android.synthetic.main.fragment_video.*

class VideoFragment : BaseFragment<VideoContact.VideoPresenter>(), VideoContact.IVideoView  {

    @InjectPresenter
    lateinit var mPresenter : VideoContact.VideoPresenter

    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id){
            R.id.fast_rewind->{

            }
            R.id.fast_forward->{

            }
            R.id.playing->{
                mPresenter.playVideo()
            }
            R.id.skip_next->{
                mPresenter.playNext()
            }
            R.id.skip_prev->{
                mPresenter.playPrev()
            }
        }
    }

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_video
    }

    override fun initData() {
        setOnClickListeners(skip_next, skip_prev, fast_forward, fast_rewind, playing)
        mPresenter.position = arguments?.getInt(ConstantKey.KEY_MEDIA_CLICK_POSITION) ?: 0
        surfaceViewAddCallback()
    }

    override fun refreshData() {

    }

    override fun pause() {
        mPresenter.pause()
    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context? {
        return activity
    }

    override fun surfaceViewAddCallback() {
        surfaceView.holder?.addCallback(mPresenter.getSurfaceCallback(mPresenter.mMediaPlayer))
        surfaceView2.setZOrderOnTop(true)
        surfaceView2.holder?.addCallback(mPresenter.getSurfaceCallback(mPresenter.mMediaPlayer2))
    }
}