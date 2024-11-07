package com.xianyu.earthquake

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.xianyu.earthquake.util.Utils.MAX_LAN_LONG
import com.xianyu.earthquake.util.Utils.MIN_MAG

class MapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private var aMap: AMap? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState) // 初始化地图
        aMap = mapView.map


        val longitude = intent.extras?.getDouble("longitude", MAX_LAN_LONG) ?: MAX_LAN_LONG
        val latitude = intent.extras?.getDouble("latitude", MAX_LAN_LONG) ?: MAX_LAN_LONG
        val magnitude = intent.extras?.getDouble("magnitude", MIN_MAG) ?: MIN_MAG
        val title = if (magnitude == MIN_MAG) {
            "Magnitude: null"
        } else {
            "Magnitude: $magnitude"
        }


        val latLng = LatLng(latitude, longitude)
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title(title)

        aMap?.addMarker(markerOptions)
        aMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory() // 低内存时调用
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}