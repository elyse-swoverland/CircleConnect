package com.elyseswoverland.circleconnect.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elyseswoverland.circleconnect.models.Merchant

class NearbyFavoritesFragment : Fragment() {
    private lateinit var favorites: ArrayList<Merchant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favorites = arguments?.getParcelableArrayList<Merchant>("FAVORITES") as ArrayList<Merchant>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_favorites_nearby, container, false)
    }

    companion object {
        fun newInstance(favorites: ArrayList<Merchant>): NearbyFavoritesFragment {
            val fragment = NearbyFavoritesFragment()
            val args = Bundle()
            args.putParcelableArrayList("FAVORITES", favorites)
            fragment.arguments = args
            return NearbyFavoritesFragment()
        }
    }
}