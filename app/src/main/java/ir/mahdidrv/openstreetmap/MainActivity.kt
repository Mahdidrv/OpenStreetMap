package ir.mahdidrv.openstreetmap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider

import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MainActivity : AppCompatActivity() {


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ctx = applicationContext
        //Configuration.getIs

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        setContentView(R.layout.activity_main)

        // setip mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        val mapController = mapView.getController();
        mapController.setZoom(15.0)
        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setTilesScaledToDpi(true);

        // setup marker
        val point = GeoPoint(35.7448416, 51.3775099, 17.0)
        val marker = Marker(mapView)
        marker.position = point
        marker.title = "یه چیز"
        marker.subDescription = "یه چیز دیگه"
        mapView.overlays.add(marker)
        mapController.setCenter(point);


        // custom onClick on marker
        marker.setOnMarkerClickListener(object : Marker.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {

                val lat = marker!!.position.latitude
                val long = marker!!.position.longitude

                val labelLocation = "یه جایی"
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:<" + lat + ">,<" + long + ">?q=<" + lat + ">,<" + long + ">(" + labelLocation + ")")
                    )
                );
                return true
            }
        })

        setMarker(35.748132, 51.351574, "پارک پردیسان", "فول تایم، تایم آزاد شماره تماس: 09195689975")
        setMarker(35.748027, 51.448879, "پارک شریعتی", "همه روزه، ساعت ۸")

    }

    // set marker method
    fun setMarker(lat: Double, long: Double, title: String, description: String) {
        val point = GeoPoint(lat, long)
        val marker = Marker(mapView)
        marker.position = point
        marker.title = title
        marker.subDescription = description
        //marker.icon =ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_location_on_24)
        mapView.overlays.add(marker)


    }
}