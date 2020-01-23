package com.example.partycheckapp.presentation.feature.debtordetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.partycheckapp.PartyApp
import com.example.partycheckapp.R
import com.example.partycheckapp.data.debtors.Debt
import com.example.partycheckapp.data.user.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_debtor_details.*
import javax.inject.Inject

class DebtorDetailsFragment : MvpAppCompatFragment(), DebtorDetailsView {
    @Inject
    @InjectPresenter
    lateinit var debtorDetailsPresenter: DebtorDetailsPresenter

    @ProvidePresenter
    fun initPresenter() = debtorDetailsPresenter

    private var localDebt = Debt()
    private var secondUser = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        PartyApp.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_debtor_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initClickListener()
        arguments?.getString("debt_ref")?.let { initPresenter().getDebt(it) }
    }

    private fun initToolbar() {
        val activity = (activity as DebtorDetailsActivity)
        activity.setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.debt_details)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        toolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    private fun initView() {
        Picasso.with(context).load(secondUser.imageUrl).into(iv_photo)
        tv_user_name.text = secondUser.name
        tv_phone_num.text = secondUser.phoneNumber
        tv_card_num.text = secondUser.cardNumber
        tv_debt_size.text = MONET_FORMAT.format(localDebt.debtSize)

        val colorRes = if (localDebt.debtSize < 0) {
            R.color.colorButton
        } else {
            R.color.colorGreenNumber
        }
        val color = context?.let { ContextCompat.getColor(it, colorRes) }
        if (color != null) {
            tv_debt_size.setTextColor(color)
        }
        initApprove()
    }

    private fun initApprove() {
        if (localDebt.debtSize > 0) {
            if (localDebt.creditorApproval) {
                tv_debt_approve.text = getString(R.string.debt_approve)
                btn_debt.text = getString(R.string.cancel)
            } else {
                tv_debt_approve.text = ""
                btn_debt.text = getString(R.string.debt_repaid)
            }
        } else {
            if (localDebt.debtorApproval) {
                tv_debt_approve.text = getString(R.string.debt_approve)
                btn_debt.text = getString(R.string.cancel)
            } else {
                tv_debt_approve.text = ""
                btn_debt.text = getString(R.string.debt_repaid)
            }
        }
    }

    override fun receiveDebt(debt: Debt) {
        localDebt = debt
        secondUser = if (debt.debtSize > 0)
            debt.debtor
        else
            debt.creditor
        initView()
    }

    private fun initClickListener() {
        btn_debt.setOnClickListener {
            if (localDebt.debtSize > 0) {
                arguments?.getString("debt_ref")?.let { debtorDetailsPresenter.updateDebt(it, true) }
                localDebt.creditorApproval = !localDebt.creditorApproval
                initApprove()
            } else {
                arguments?.getString("debt_ref")?.let { debtorDetailsPresenter.updateDebt(it, false) }
                localDebt.debtorApproval = !localDebt.debtorApproval
                initApprove()
            }
        }
    }

    companion object {
        private const val MONET_FORMAT = "%.2f $"
        fun newInstance(debtRef: String): DebtorDetailsFragment {
            val args = Bundle()
            args.putString("debt_ref", debtRef)
            val fragment = DebtorDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
