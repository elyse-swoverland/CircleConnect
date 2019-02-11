package com.elyseswoverland.circleconnect.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject


class FavoritesFragment : Fragment() {
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private lateinit var ctx: Context

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = context ?: return

        setupViewPager()
        favoritesTabs.setupWithViewPager(viewPager)

//        circleConnectApiManager.getCustomerFavorites(appPreferences.recentLat, appPreferences.recentLong)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::onGetFavoritesSuccess, this::onGetFavoritesFailure)
    }

    private fun setupViewPager() {
        val adapter = Adapter(childFragmentManager)
        adapter.addFragment(AllFavoritesFragment.newInstance(), "All")
        adapter.addFragment(NearbyFavoritesFragment.newInstance(), "Near Me")
        viewPager.adapter = adapter
    }

    private fun onGetFavoritesSuccess(favorites: ArrayList<Merchant>) {
        groupAdapter.clear()
        groupAdapter.add(Section().apply {
            favorites.forEachIndexed { _, merchant ->
                add(FavoritesItem(ctx, merchant))
            }
        })

    }

    private fun onGetFavoritesFailure(throwable: Throwable) {

    }

    internal class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}