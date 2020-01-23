package com.example.partycheckapp.presentation.feature.party.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.partycheckapp.R
import com.example.partycheckapp.data.debtors.UserDebtor
import com.example.partycheckapp.presentation.feature.debtors.DebtorsListAdapter
import com.example.partycheckapp.presentation.feature.debtors.DebtorsListPresenter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.presentation.feature.debtordetails.DebtorDetailsActivity
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_debtor_list.*
import javax.inject.Inject


class DebtorsListFragment : MvpAppCompatFragment(), DebtorsListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var debtorsListPresenter: DebtorsListPresenter

    @ProvidePresenter
    fun initPresenter() = debtorsListPresenter

    private var localMenu: Menu? = null
    private var debtList = ArrayList<UserDebtor>()
    private var currentUser = User()
    private val recyclerAdapter = DebtorsListAdapter { onItemClick(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp
            .instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_debtor_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this.context, resources.configuration.orientation
            )
        )
        recycler_view.adapter = recyclerAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        debtorsListPresenter.getCurrentUser()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
        initToolbar()
    }

    override fun getDebtorsList(dataList: ArrayList<UserDebtor>) {
        debtList = dataList
        showDebtorsList(dataList)
    }

    private fun showDebtorsList(dataList: ArrayList<UserDebtor>) {
        swipe_container.isRefreshing = false
        recyclerAdapter.list = dataList
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        debtorsListPresenter.setUsersDebtorsList()
    }

    override fun getUser(user: User) {
        currentUser = user
    }

    private fun initToolbar() {
        val activity = (activity as MainActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.debtor_list)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val activity = (activity as MainActivity)
        activity.menuInflater.inflate(R.menu.toolbar_items, menu)
        val mSearch = menu?.findItem(R.id.action_search)
        val mSearchView = mSearch?.actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                getFilterList(newText)
                return false
            }
        })
        localMenu = menu
    }

    fun getFilterList(textQuery: String) =
        showDebtorsList(ArrayList(debtList.filter { it.name.contains(textQuery) }))

    private fun onItemClick(userDebtor: UserDebtor) {
        val intent = Intent(activity, DebtorDetailsActivity::class.java)
        intent.putExtra("debt_ref", userDebtor.debtRef)
        startActivity(intent)

    }

    companion object {
        fun newInstance(): DebtorsListFragment {
            val args = Bundle()
            val fragment = DebtorsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
