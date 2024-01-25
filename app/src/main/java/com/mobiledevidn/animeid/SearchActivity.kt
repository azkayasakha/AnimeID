package com.mobiledevidn.animeid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiledevidn.animeid.data.remote.AnimeApi
import com.mobiledevidn.animeid.data.remote.respon.search.Data
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var rvAnime : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = intent.getStringExtra(MainActivity.TITLE)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Hasil Pencarian: $query"

        rvAnime = findViewById(R.id.rv_anime)

        val animeApi = AnimeApi.create()
        lifecycleScope.launch {
            val listAnime = animeApi.searchAnime(query!!)
            anime(listAnime.data)
        }
    }

    private fun anime(listAnime: List<Data>) {
        rvAnime.layoutManager = LinearLayoutManager(this)

        val animeAdapter = SearchAdapter(listAnime)
        rvAnime.adapter = animeAdapter
        animeAdapter.itemClickListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ID, "${it.malId}")
            startActivity(intent)
        }
    }
}