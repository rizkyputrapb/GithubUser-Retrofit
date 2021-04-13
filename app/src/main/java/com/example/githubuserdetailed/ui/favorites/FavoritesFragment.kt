package com.example.githubuserdetailed.ui.favorites

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.githubuserdetailed.R
import com.example.githubuserdetailed.api.Status
import com.example.githubuserdetailed.dao.DbBuilder
import com.example.githubuserdetailed.dao.Favorites
import com.example.githubuserdetailed.databinding.FragmentFavoritesBinding
import com.example.githubuserdetailed.model.User
import com.example.githubuserdetailed.ui.AutofitGridLayoutManager
import com.example.githubuserdetailed.ui.main.MainFragmentDirections

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentFavoritesBinding
    lateinit var viewModelFactory: FavoriteViewModelFactory
    lateinit var viewModel: FavoriteViewModel
    lateinit var favAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar2)
        context?.getColor(R.color.design_default_color_background)?.let {
            binding.toolbar2.setTitleTextColor(
                it
            )
        }
        binding.toolbar2.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar2.setNavigationOnClickListener {
            it.setOnClickListener {
                (activity as AppCompatActivity).onBackPressed()
            }
        }
        vmSetup()
        observerSetup()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSetup()
    }

    fun vmSetup() {
        viewModelFactory = FavoriteViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        activity?.applicationContext?.let { DbBuilder.getInstance(it) }?.let {
            viewModel.setDbHelper(
                it
            )
        }
    }

    fun rvSetup() {
        val layoutManager = AutofitGridLayoutManager(activity?.applicationContext, 500)
        favAdapter = FavoriteAdapter(object : OnItemFavoriteListener {
            override fun OnBtnDeleteListener(username: String) {
                Log.w("Favorite", "Button clicked username: $username")
                viewModel.deleteFav(username).observe(viewLifecycleOwner, {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data.let {
                                    Toast.makeText(
                                        activity?.applicationContext,
                                        "$username successfully deleted from favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val action =
                                        FavoritesFragmentDirections.actionFavoritesFragmentSelf()
                                    view?.findNavController()?.navigate(action)
                                }
                            }

                            Status.ERROR -> {
                                Toast.makeText(
                                    activity?.applicationContext,
                                    "Error deleted from favorites: ${resource.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.w("Favorite", "Error deleting favorites: ${resource.message}")
                            }
                        }
                    }
                })
            }

            override fun onItemClick(user: User) {
                viewModel.onUserClicked(user)
            }
        })
        with(binding.rvFavorites) {
            this.adapter = favAdapter
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
        }
    }

    fun cursorMapping(it: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()
        while (it?.moveToNext() == true) {
            it?.apply {
                list.add(
                    User(
                        getString(getColumnIndexOrThrow(Favorites.USERNAME)),
                        getString(getColumnIndexOrThrow(Favorites.AVATAR_URL)),
                        getString(getColumnIndexOrThrow(Favorites.NAME))
                    )
                )
            }
        }
        Log.d("Mapping", "cursorMapping: SIZE ${list.size}")
        it?.close()
        return list
    }

    fun observerSetup() {
        lateinit var list: ArrayList<User>
        activity?.applicationContext?.let {
            viewModel.setFavoriteList(it).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data.let {
                                list = cursorMapping(it)
                                favAdapter.setFavList(list)
                                favAdapter.notifyDataSetChanged()
                                Log.w("Favorite", "Cursor retrieved: ${cursorMapping(it).size}")
                            }
                        }
                        Status.ERROR -> {
                            Log.w("Favorite", "Error retrieving favorites: ${resource.message}")
                        }
                    }
                }
            })
        }
        viewModel.navigatetoDetail().observe(viewLifecycleOwner, { user ->
            if (user != null) {
                val action: NavDirections =
                    FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(user)
                Navigation.findNavController(requireView()).navigate(action)
                viewModel.onUserMainDetailNavigated()
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoritesFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}