package com.elyseswoverland.circleconnect.ui.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_favorites.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class FavoritesFragment : Fragment(), TabLayout.OnTabSelectedListener {
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private lateinit var ctx: Context

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Dagger.getInstance().component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctx = context ?: return

        setUpRecyclerView()

        circleConnectApiManager.getCustomerFavorites(appPreferences.recentLat, appPreferences.recentLong)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetFavoritesSuccess, this::onGetFavoritesFailure)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.preferences)?.isVisible = false
        menu?.findItem(R.id.logout)?.isVisible = false
    }

    private fun setUpRecyclerView() {
        val lm = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        favoritesRecyclerview.layoutManager = lm
        val dividerItemDecoration = DividerItemDecoration(favoritesRecyclerview.context, lm.orientation)
        favoritesRecyclerview.addItemDecoration(dividerItemDecoration)
    }

//    private fun setupViewPager(favorites: ArrayList<Merchant>) {
//        val adapter = Adapter(childFragmentManager)
//        adapter.addFragment(AllFavoritesFragment.newInstance(favorites), "All")
//        adapter.addFragment(NearbyFavoritesFragment.newInstance(favorites), "Near Me")
//        viewPager.adapter = adapter
//    }

    // TODO: - Redo actionbar
    private fun onGetFavoritesSuccess(favorites: ArrayList<Merchant>) {
        Log.d("TAG", "Success")
//        setupViewPager(favorites)
//        favoritesTabs.setupWithViewPager(viewPager)
//
//        favoritesTabs.forEachIndexed { index, view ->
//            val tab: TabLayout.Tab? = favoritesTabs.getTabAt(index)
//            tab?.let {
//                val tabTextView = TextView(ctx)
//                tab.customView = tabTextView
//
//                tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
//                tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//
//                tabTextView.text = tab.text
//            }
//        }
//        favoritesTabs.addOnTabSelectedListener(this)

        groupAdapter.clear()
        groupAdapter.add(Section().apply {
            favorites.forEachIndexed { _, merchant ->
                add(FavoritesItem(ctx, merchant))
            }
        })
        favoritesRecyclerview.adapter = groupAdapter
    }

    private fun onGetFavoritesFailure(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

//    internal class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
//        private val mFragmentList = ArrayList<Fragment>()
//        private val mFragmentTitleList = ArrayList<String>()
//
//        override fun getItem(position: Int): Fragment {
//            return mFragmentList[position]
//        }
//
//        override fun getCount(): Int {
//            return mFragmentList.size
//        }
//
//        fun addFragment(fragment: Fragment, title: String) {
//            mFragmentList.add(fragment)
//            mFragmentTitleList.add(title)
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mFragmentTitleList[position]
//        }
//    }
}