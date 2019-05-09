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
import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.user.User
import com.example.partycheckapp.presentation.feature.debtorDetails.DebtorDetailsActivity
import com.example.partycheckapp.presentation.feature.main.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_debtor_list.*
import javax.inject.Inject


class DebtorsListFragment : MvpAppCompatFragment(), DebtorsListView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    @InjectPresenter
    lateinit var debtorsListPresenter: DebtorsListPresenter

    @ProvidePresenter
    fun initPresenter() = debtorsListPresenter


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
        val manager = LinearLayoutManager(context)
        recycler_view.adapter = recyclerAdapter
        recycler_view.layoutManager = manager
        debtorsListPresenter.getCurrentUser()
        swipe_container.setColorSchemeResources(R.color.colorAccent)
        swipe_container.setOnRefreshListener(this)
        initToolbar()
    }

    override fun getDebtorsList(dataList: ArrayList<Debt>) {
        debtList = dataList
        showDebtorsList(dataList)
    }

    private fun showDebtorsList(dataList: ArrayList<Debt>) {
        swipe_container.isRefreshing = false
        val debtorsList = ArrayList<UserDebtor>()
        for (debt in dataList) {
            if (debt.creditor.name == currentUser.name)
                debtorsList.add(
                    UserDebtor(
                        debt.debtor.name,
                        debt.debtor.phoneNumber,
                        debt.debtor.cardNumber,
                        debt.debtSize,
                        debt.debtor.imageUrl
                    )
                )
            else
                debtorsList.add(
                    UserDebtor(
                        debt.creditor.name,
                        debt.creditor.phoneNumber,
                        debt.creditor.cardNumber,
                        debt.debtSize * -1,
                        debt.creditor.imageUrl
                    )
                )
        }

        recyclerAdapter.list = debtorsList
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
        toolbar.title = "Debtor List"
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

    fun getFilterList(textQuery: String){
        val newList = ArrayList<Debt>()
        for (debt in debtList){
            if(debt.creditor.name.contains(textQuery) || debt.debtor.name.contains(textQuery))
                newList.add(debt)
        }
        showDebtorsList(newList)
    }

    private fun onItemClick(userDebtor: UserDebtor) {
        var currentDebt = Debt()
        for (debt in debtList) {
            if (userDebtor.debtSize > 0) {
                if (debt.debtor.name == userDebtor.name)
                    currentDebt = debt
            } else {
                if (debt.creditor.name == userDebtor.name)
                    currentDebt = debt
            }
        }

        val intent = Intent(activity, DebtorDetailsActivity::class.java)
        intent.putExtra("debt_ref", currentDebt.ref)
        startActivity(intent)

    }

    companion object {
        var localMenu: Menu? = null
        var debtList = ArrayList<Debt>()
        var currentUser = User()
        fun newInstance(): DebtorsListFragment {
            val args = Bundle()
            val fragment = DebtorsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
