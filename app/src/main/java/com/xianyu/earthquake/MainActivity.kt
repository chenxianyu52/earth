package com.xianyu.earthquake

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xianyu.earthquake.util.Utils
import com.xianyu.earthquake.util.Utils.MAX_LAN_LONG
import com.xianyu.earthquake.util.Utils.MIN_MAG
import com.xianyu.earthquake.view.ItemAdapter
import com.xianyu.earthquake.viewmodel.EarthViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val itemViewModel: EarthViewModel = EarthViewModel()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val marginDecoration = ItemMarginDecoration(Utils.getDpToPixel(this, 5))
        recyclerView.addItemDecoration(marginDecoration)


        // 使用生命周期感知的协程，直接收集 Flow
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            itemViewModel.getItemsFlow().collect { data ->
                progressBar.visibility = View.GONE
                if (data.isNullOrEmpty()) {
                    return@collect
                }
                recyclerView.adapter =
                    ItemAdapter(data, object : ItemAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val longitude = data[position].longitude
                            val latitude = data[position].latitude
                            val magnitude = data[position].mag ?: MIN_MAG
                            if (longitude == MAX_LAN_LONG || latitude == MAX_LAN_LONG) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "经纬度数据异常",
                                    Toast.LENGTH_LONG
                                ).show()
                                return
                            }
                            val intent = Intent(this@MainActivity, MapActivity::class.java)
                            val bundle = Bundle()
                            bundle.putDouble("longitude", longitude)
                            bundle.putDouble("latitude", latitude)
                            bundle.putDouble("magnitude", magnitude)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    })
            }
        }
    }

    inner class ItemMarginDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.left = margin
            outRect.right = margin
            outRect.top = margin
            outRect.bottom = margin
        }
    }
}