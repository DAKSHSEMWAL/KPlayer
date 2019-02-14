package com.kuro.daksh.kplayer.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kuro.daksh.kplayer.Interface.CustomItemClickListener
import com.kuro.daksh.kplayer.R
import com.kuro.daksh.kplayer.models.SongModel
import com.kuro.daksh.kplayer.services.PlayMusicService
import java.util.concurrent.TimeUnit

class SongListAdapter(SongModel:ArrayList<SongModel>,context: Context):RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    var mSongModel = SongModel
    var mContext = context
    var allMusicList:ArrayList<String> = ArrayList()

    companion object {
        val MUSICLIST = "musiclist"
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SongListViewHolder {

        var view = LayoutInflater.from(p0!!.context).inflate(R.layout.music_row,p0,false)
        return SongListViewHolder(view)

    }

    override fun getItemCount(): Int {

        return mSongModel.size

    }

    override fun onBindViewHolder(p0: SongListViewHolder, p1: Int) {

        var model = mSongModel[p1]
        var songName = model.mSongName
        var songDuration = toMandS(model.mSongDuration.toLong())

        p0.songTV.text = songName
        p0.durationTV.text = songDuration
        p0.setOnCustomItemClickListener(object :CustomItemClickListener{
            override fun OnCustomItemClick(view: View, pos: Int) {
                for(i in 0 until mSongModel.size){
                    allMusicList.add(mSongModel[i].mSongPath)
                }
                Log.i("allMusicList",allMusicList.toString())
                var musicDataIntent = Intent(mContext,PlayMusicService::class.java)
                musicDataIntent.putStringArrayListExtra(MUSICLIST,allMusicList)
            }
        })
    }

    fun toMandS(millis:Long):String{

        var duration = String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis)-
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))

        return duration
    }

    class SongListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var songTV: TextView
        var durationTV: TextView
        var albumArt: ImageView
        var mCustomItemClickListener:CustomItemClickListener?=null

        init {

            songTV = itemView.findViewById(R.id.songname)
            durationTV = itemView.findViewById(R.id.songduration)
            albumArt = itemView.findViewById(R.id.albumart)
            itemView.setOnClickListener(this)

        }

        fun setOnCustomItemClickListener(customItemClickListener: CustomItemClickListener){
            this.mCustomItemClickListener = customItemClickListener
        }

        override fun onClick(v: View?) {
            this.mCustomItemClickListener!!.OnCustomItemClick(v!!,adapterPosition)
        }
    }
}