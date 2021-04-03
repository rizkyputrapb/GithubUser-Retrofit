package com.example.githubuserdetailed.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.api.Status
import com.example.githubuserdetailed.databinding.ContentDetailedBinding
import com.example.githubuserdetailed.databinding.DetailFragmentBinding
import com.example.githubuserdetailed.model.User
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.content_detailed.*
import kotlinx.android.synthetic.main.main_fragment.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    lateinit var binding: DetailFragmentBinding
    lateinit var viewModelFactory: DetailViewModelFactory
    lateinit var constraintLayout: ConstraintLayout
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val titles = arrayOf("Followers", "Following")
    lateinit var detailedContent: ContentDetailedBinding
    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        detailedContent = binding.detailedContent
        (activity as AppCompatActivity).setSupportActionBar(binding.detailToolbar)
        context?.getColor(R.color.design_default_color_background)?.let {
            binding.detailToolbar.setTitleTextColor(
                it
            )
        }
        binding.detailToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.detailToolbar.setNavigationOnClickListener{
            it.setOnClickListener {
                (activity as AppCompatActivity).onBackPressed()
            }
        }
        binding.lifecycleOwner = this
        vmSetup()
        observerSetup()
        tabSetup()
        return binding.root
    }

    private fun tabSetup() {
        val tabLayout = binding.detailedContent.tabLayout
        viewPagerAdapter = ViewPagerAdapter(this, user?.login ?: "")
        binding.detailedContent.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, binding.detailedContent.viewPager2) { tab, position ->
            tab.text = titles[position]
            binding.detailedContent.viewPager2.setCurrentItem(tab.position, true)
        }.attach()
    }

    private fun vmSetup() {
        viewModelFactory = DetailViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewmodel = viewModel
        user = arguments?.let { DetailFragmentArgs.fromBundle(it).user }
    }

    private fun observerSetup() {
        viewModel.getUser(user?.login).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {
                            binding.coordinatorLayout.visibility = View.VISIBLE
                            binding.progressBar3.visibility = View.GONE
                            binding.detailErrorMsg.visibility = View.GONE
                            Log.d(
                                "STATUS",
                                "SUCCESS: data retrieved"
                            )
                        }
                    }
                    Status.LOADING -> {
                        binding.detailErrorMsg.visibility = View.GONE
                        Log.d("STATUS", "LOADING....")
                    }
                    Status.ERROR -> {
                        binding.progressBar3.visibility = View.GONE
                        binding.detailErrorMsg.text = "Error: ${it.message}"
                        Log.d("STATUS", "ERROR: ${it.message}")
                    }
                }
            }
        })

    }
}