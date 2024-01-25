package com.mobiledevidn.animeid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobiledevidn.animeid.data.remote.respon.search.Data
import me.bush.translator.Language
import me.bush.translator.Translator

class SearchAdapter(private val listAnime: List<Data>) : RecyclerView.Adapter<SearchAdapter.AnimeViewHolder>() {

    var itemClickListener: ((Data) -> Unit)? = null

    class AnimeViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val ivPoster = view.findViewById<ImageView>(R.id.iv_poster)
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvScore = view.findViewById<TextView>(R.id.tv_score)
        private val tvAired = view.findViewById<TextView>(R.id.tv_aired)
        private val tvStatus = view.findViewById<TextView>(R.id.tv_status)

        private fun translate(text: String) : String {
            val translator = Translator()
            val translation = translator.translateBlocking(text, Language.INDONESIAN, Language.AUTO)
            return  translation.translatedText
        }

        fun bindView(anime: Data) {
            Glide.with(itemView.context)
                .load(anime.images.webp.largeImageUrl)
                .into(ivPoster)

            val animeTitle = if (anime.title.length < 35) {
                anime.title
            } else {
                anime.title.substring(startIndex = 0, endIndex = 35) + "..."
            }

            tvTitle.text = animeTitle
            tvScore.text = "Skor: ${anime.score}"
            tvAired.text = "Tayang: ${translate(anime.aired.string)}"
            tvStatus.text = "Status: ${translate(anime.status)}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAnime.size
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = listAnime[position]

        holder.bindView(anime)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(anime)
        }
    }
}