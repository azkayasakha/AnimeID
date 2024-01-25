package com.mobiledevidn.animeid

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val TITLE = "title"
    }

    private lateinit var btnSubmit: Button
    private lateinit var edtTitle: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit = findViewById(R.id.btn_submit)
        edtTitle = findViewById(R.id.edt_title)

        btnSubmit.setOnClickListener {
            if (isOnline(this)) {
                val title =  edtTitle.text.toString()
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra(TITLE, title)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Tidak ada internet :(", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }
}