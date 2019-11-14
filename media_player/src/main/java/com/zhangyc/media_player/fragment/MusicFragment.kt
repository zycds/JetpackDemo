package com.zhangyc.media_player.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.Constants
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.library.db.Music
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.event.RxHelper
import com.zhangyc.media_player.ConstantKey
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.MusicContact
import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment :BaseFragment<MusicContact.MusicPresenter>(), MusicContact.IMusicView {


    @InjectPresenter
    lateinit var mPresenter : MusicContact.MusicPresenter

    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id!!){
            R.id.skip_prev->{
                mPresenter.getMusicBinder()?.skipPrev()
            }
            R.id.skip_next->{
                mPresenter.getMusicBinder()?.skipNext()
            }
            R.id.playing->{
                mPresenter.getMusicBinder()?.playPause()
            }
            R.id.fast_forward->{
                mPresenter.getMusicBinder()?.fastForward()
            }
            R.id.fast_rewind->{
                mPresenter.getMusicBinder()?.fastRewind()
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun init() {
        RxBus.instance.toObservable(this, MsgEvent::class.java)
            .compose(RxHelper.handlerResultIO())
            .subscribe {
                when(it.code) {
                    MsgEvent.REFRESH_MUSIC_PLAY_STATUS_PLAYING->{
                        playing.setImageResource(R.drawable.selector_pause)
                        mPresenter.subscribeUpdateProgress()
                    }
                    MsgEvent.REFRESH_MUSIC_PLAY_STATUS_PAUSE->{
                        playing.setImageResource(R.drawable.selector_playing)
                        mPresenter.disposeUpdateProgress()
                    }
                    MsgEvent.REFRESH_MUSIC_INFO->{
                        mPresenter.disposeUpdateProgress()
                        mPresenter.checkBindMusicService(mPresenter.position.inc())
                        ReadSdMedia.instance.getMusicLists()?.get(mPresenter.position)?.let { it1 -> refreshMusicInfo(it1) }
                    }
                }
            }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_music
    }

    override fun initData() {
        setOnClickListeners(skip_next, skip_prev, fast_forward, fast_rewind, playing)
        val position = arguments?.getInt(ConstantKey.KEY_MEDIA_CLICK_POSITION) ?: 0
        ReadSdMedia.instance.getMusicLists()?.get(position)?.let { refreshMusicInfo(it) }
        mPresenter.checkBindMusicService(position)
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return activity
    }

    override fun getCurrentFragment(): MusicFragment {
        return this
    }

    override fun refreshMusicInfo(music: Music) {
        text_title.text = music.title
        text_album_name.text = music.displayName
        val parse = Uri.parse(Constants.AUDIO_MUSIC_ALBUM_PATH.plus(music.albumId))
        val query = BaseApp.instance.contentResolver.query(parse, arrayOf("album_art"), null, null, null)
        var albumPath : String? = null
        if (query != null && query.count > 0 && query.columnCount > 0) {
            query.moveToNext()
            albumPath = query.getString(0)
        }
        query?.close()
        Lg.debug(bTag, "albumPath : $albumPath")
        if (albumPath == null) {
            Glide.with(BaseApp.instance).load(R.drawable.ic_album_black_160dp).into(image_album)
        } else {
            Glide.with(BaseApp.instance).load(albumPath).into(image_album)
        }

    }

    override fun getProgressBar(): ProgressBar {
        return progress_horizontal
    }

}