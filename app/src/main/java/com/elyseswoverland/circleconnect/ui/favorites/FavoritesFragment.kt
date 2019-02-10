package com.elyseswoverland.circleconnect.ui.favorites

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
import kotlinx.android.synthetic.main.fragment_favorites.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class FavoritesFragment : Fragment() {

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dagger.getInstance().component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.elyseswoverland.circleconnect.R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        favoritesTabs.setupWithViewPager(viewPager)

        circleConnectApiManager.customerFavorites
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetFavoritesSuccess, this::onGetFavoritesFailure)
    }

    private fun setupViewPager() {
        val adapter = Adapter(childFragmentManager)
        adapter.addFragment(AllFavoritesFragment.newInstance(), "All")
        adapter.addFragment(NearbyFavoritesFragment.newInstance(), "Near Me")
        viewPager.adapter = adapter
    }

    private fun onGetFavoritesSuccess(favorites: ArrayList<Merchant>) {

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