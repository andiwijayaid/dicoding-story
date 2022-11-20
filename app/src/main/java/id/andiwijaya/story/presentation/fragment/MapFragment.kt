package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.CAMERA_LAT_LNG_BOUNDS_PADDING
import id.andiwijaya.story.core.Constants.ZERO_DOUBLE
import id.andiwijaya.story.core.base.BaseFragment
import id.andiwijaya.story.databinding.FragmentMapBinding
import id.andiwijaya.story.presentation.viewmodel.MapViewModel

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback {

    private val viewModel by viewModels<MapViewModel>()

    private lateinit var mMap: GoogleMap
    private val boundsBuilder = LatLngBounds.Builder()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMapBinding.inflate(inflater, container, false).also {
            val mapFragment =
                childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.stbMap.setNavigationOnClickListener { back() }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addManyMarker()
    }

    private fun addManyMarker() = with(viewModel) {
        stories.observe(viewLifecycleOwner) {
            it.forEach { story ->
                LatLng(story.lat ?: ZERO_DOUBLE, story.lon ?: ZERO_DOUBLE).apply {
                    mMap.addMarker(MarkerOptions().position(this))
                    boundsBuilder.include(this)
                }
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        boundsBuilder.build(),
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        CAMERA_LAT_LNG_BOUNDS_PADDING
                    )
                )
            }
        }
    }

}