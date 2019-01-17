package com.elyseswoverland.circleconnect.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AllFavoritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_favorites_all, container, false)
    }

    companion object {
        fun newInstance(): AllFavoritesFragment {
            return AllFavoritesFragment()
        }
    }
}