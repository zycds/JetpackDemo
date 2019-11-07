package com.zhangyc.media_player.fragment

import android.content.Context
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.library.db.Music
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.media_player.ConstantKey
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.MusicContact
import kotlinx.android.synthetic.main.fragment_music.*
import kotlinx.android.synthetic.main.item_music.*

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
                mPresenter.getMusicBinder()?.play()
            }
            R.id.fast_forward->{
                mPresenter.getMusicBinder()?.fastForward()
            }
            R.id.fast_rewind->{
                mPresenter.getMusicBinder()?.fastRewind()
            }
        }
    }

    override fun init() {
        val position = arguments?.getInt(ConstantKey.KEY_MEDIA_CLICK_POSITION)
        ReadSdMedia.instance.getMusicLists()?.get(position!!)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_music
    }

    override fun initData() {
        setOnClickListeners(skip_next, skip_prev, fast_forward, fast_rewind, playing)
        mPresenter.checkBindMusicService()
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
    }

    override fun updateProgress(progress: Int) {

    }

}