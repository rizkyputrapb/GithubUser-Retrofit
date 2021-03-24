package com.example.githubuserdetailed.ui.detail

import android.os.Bundle
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
import com.example.githubuserdetailed.databinding.DetailFragmentBinding
import com.example.githubuserdetailed.model.User
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.content_detailed.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    lateinit var binding: DetailFragmentBinding
    lateinit var viewModelFactory: DetailViewModelFactory
    lateinit var constraintLayout: ConstraintLayout
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val titles = arrayOf("Followers", "Following")
    var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.detailToolbar)
        context?.getColor(R.color.design_default_color_background)?.let {
            binding.detailToolbar.setTitleTextColor(
                it
            )
        }
        viewModelFactory = DetailViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewmodel = viewModel
        assert(arguments != null)
        user = arguments?.let { DetailFragmentArgs.fromBundle(it).user }
        val detailedContent = binding.detailedContent
        viewModel.getUser(user?.login)?.observe(viewLifecycleOwner, { user ->
            binding.user = user
            detailedContent.user = user
            Glide.with(requireView()).load(user.avatar_url).into(binding.detailAvatar)
        })
tabSetup()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        constraintLayout = binding.detailedContent.detailedConstraint
        var constraintSet = ConstraintSet()
        if (user?.bio == null) {
            constraintSet.clone(constraintLayout)
            constraintSet.clear(
                binding.detailedContent.followingLayout.id,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                binding.detailedContent.followingLayout.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                16
            )
            constraintSet.applyTo(constraintLayout)
        } else if (user?.location == null) {
            constraintSet.connect(
                binding.detailedContent.compIcon.id,
                ConstraintSet.START,
                binding.detailedContent.viewDivider.id,
                ConstraintSet.START,
                0
            )
            constraintSet.connect(
                binding.detailedContent.compIcon.id,
                ConstraintSet.TOP,
                binding.detailedContent.viewDivider.id,
                ConstraintSet.BOTTOM,
                8
            )
            binding.detailedContent.locIcon.visibility = View.GONE
            binding.detailedContent.userLoc.visibility = View.GONE
        }
    }

    fun tabSetup() {
        val tabLayout = binding.detailedContent.tabLayout
        viewPagerAdapter = ViewPagerAdapter(this, user?.login ?: "")
        binding.detailedContent.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, binding.detailedContent.viewPager2) { tab, position ->
            tab.text = titles[position]
            binding.detailedContent.viewPager2.setCurrentItem(tab.position, true)
        }.attach()
    }

}