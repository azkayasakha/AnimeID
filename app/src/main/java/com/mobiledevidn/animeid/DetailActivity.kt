package com.mobiledevidn.animeid

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mobiledevidn.animeid.data.remote.AnimeApi
import kotlinx.coroutines.launch
import me.bush.translator.Language
import me.bush.translator.Translator

class DetailActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var lnDetail : LinearLayout

    private lateinit var ivPoster : ImageView
    private lateinit var tvTitle : TextView
    private lateinit var tvTitleEng : TextView
    private lateinit var tvScore : TextView
    private lateinit var tvRank : TextView
    private lateinit var tvPopularity : TextView
    private lateinit var tvSynopsis : TextView
    private lateinit var tvSynopsisAll : TextView

    private lateinit var lnTrailer : LinearLayout
    private lateinit var wvTrailer : WebView

    private lateinit var tvInfoProducers : TextView
    private lateinit var tvInfoStatus : TextView
    private lateinit var tvInfoEpisode : TextView
    private lateinit var tvInfoAired : TextView
    private lateinit var tvInfoStudio : TextView
    private lateinit var tvInfoGenre : TextView

    private lateinit var cvStreamCrunchyroll : CardView
    private lateinit var cvStreamBilibili : CardView
    private lateinit var cvStreamIqiyi : CardView
    private lateinit var cvStreamVidio : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra(SearchActivity.ID)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Detail Anime"

        lnDetail = findViewById(R.id.ln_detail)

        ivPoster = findViewById(R.id.iv_poster)
        tvTitle = findViewById(R.id.tv_title)
        tvTitleEng = findViewById(R.id.tv_title_english)
        tvScore = findViewById(R.id.tv_score)
        tvRank = findViewById(R.id.tv_rank)
        tvPopularity = findViewById(R.id.tv_popularity)
        tvSynopsis = findViewById(R.id.tv_synopsis)
        tvSynopsisAll = findViewById(R.id.tv_synopsis_all)

        lnTrailer = findViewById(R.id.ln_trailer)
        wvTrailer = findViewById(R.id.wv_trailer)

        tvInfoGenre = findViewById(R.id.tv_info_genre)
        tvInfoProducers = findViewById(R.id.tv_info_producers)
        tvInfoStudio = findViewById(R.id.tv_info_studio)
        tvInfoStatus = findViewById(R.id.tv_info_status)
        tvInfoEpisode = findViewById(R.id.tv_info_episode)
        tvInfoAired = findViewById(R.id.tv_info_aired)

        cvStreamCrunchyroll = findViewById(R.id.cv_stream_crunchyroll)
        cvStreamBilibili = findViewById(R.id.cv_stream_bilibili)
        cvStreamIqiyi = findViewById(R.id.cv_stream_iqiyi)
        cvStreamVidio = findViewById(R.id.cv_stream_vidio)

        val animeApi = AnimeApi.create()
        lifecycleScope.launch {
            val anime = animeApi.detailAnime(id!!)

            with(anime.data) {
                if (malId != null) {
                    lnDetail.visibility = View.VISIBLE
                }
                Glide.with(this@DetailActivity)
                    .load(images.webp.largeImageUrl)
                    .into(ivPoster)
                tvTitle.text = title
                tvTitleEng.text = titleEnglish

                tvScore.text = "$score / 10.00"
                tvRank.text = "#$rank"
                tvPopularity.text = "$popularity"

                val synopsisID = translate(synopsis)
                var short = true
                if(synopsisID.length > 313) {
                    val synopsisIDX = synopsisID.substring(startIndex = 0, endIndex = 313) + "...ㅤ"
                    tvSynopsis.text = synopsisIDX
                    tvSynopsisAll.setOnClickListener {
                        if (short){
                            tvSynopsis.text = synopsisID
                            tvSynopsisAll.text = "LIHAT SEBAGIAN ▲"
                            short = false
                        } else {
                            tvSynopsis.text = synopsisIDX
                            tvSynopsisAll.text = "LIHAT SEMUA ▼"
                            short = true
                        }
                    }
                } else {
                    tvSynopsisAll.visibility = View.GONE
                    tvSynopsis.text = synopsisID
                }

                val embedUrl = trailer.embedUrl
                if (embedUrl != null) {
                    wvTrailer.webViewClient = WebViewClient()
                    wvTrailer.loadUrl("$embedUrl")
                    wvTrailer.settings.javaScriptEnabled = true
                    wvTrailer.settings.setSupportZoom(true)
                } else {
                    lnTrailer.visibility = View.GONE
                }

                val genre = genres.joinToString(", ", transform = { it.name })
                tvInfoGenre.text = "▪ Genre: $genre"
                val producer = producers.joinToString(", ", transform = { it.name })
                tvInfoProducers.text = "▪ Producers: $producer"
                val studio = studios.joinToString(", ", transform = { it.name })
                tvInfoStudio.text = "▪ Studio: $studio"
                tvInfoStatus.text = "▪ Status: ${translate(status)}"
                tvInfoEpisode.text = "▪ Total Episode: $episodes"
                tvInfoAired.text = "▪ Tanggal Rilis: ${translate(aired.string)}"

                val intent = CustomTabsIntent.Builder().build()
                val title = titleEnglish ?: title
                cvStreamCrunchyroll.setOnClickListener {
                    intent.launchUrl(this@DetailActivity, Uri.parse("https://www.crunchyroll.com/search?q=$title"))
                }
                cvStreamBilibili.setOnClickListener {
                    intent.launchUrl(this@DetailActivity, Uri.parse("https://www.bilibili.tv/en/search-result?q=$title"))
                }
                cvStreamIqiyi.setOnClickListener {
                    intent.launchUrl(this@DetailActivity, Uri.parse("https://www.iq.com/search?query=$title&originInput=$title"))
                }
                cvStreamVidio.setOnClickListener {
                    intent.launchUrl(this@DetailActivity, Uri.parse("https://www.vidio.com/search?q=$title&originInput=$title"))
                }
            }
        }
    }

    private suspend fun translate(text: String) : String {
        val translator = Translator()
        val translation = translator.translate(text, Language.INDONESIAN, Language.ENGLISH)
        return translation.translatedText
    }

}