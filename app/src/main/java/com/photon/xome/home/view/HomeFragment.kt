package com.photon.xome.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.photon.xome.R
import com.photon.xome.home.data.FlickrImage
import com.photon.xome.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var imageSearchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var homeViewModel: HomeViewModel
    private val imagesList = arrayListOf<FlickrImage>()

    init {
        lifecycleScope.launch {
            whenStarted {
                homeViewModel.flickrImagesListLiveData.observe(viewLifecycleOwner, Observer {
                    imagesList.clear()
                    imagesList.addAll(it)
                    imagesAdapter.notifyDataSetChanged()
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.flickr_images_recyclerview)
        imageSearchView = view.findViewById(R.id.image_search_view)

        imagesAdapter = ImagesAdapter(requireActivity(),imagesList = imagesList)
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(),3)
            adapter = imagesAdapter
            itemAnimator = null
            addItemDecoration(ItemSpaceDecoration(20))
        }

        imageSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchKeyword: String?): Boolean {
                if (searchKeyword != null) {
                     homeViewModel.getFlickrImages(searchKeyword)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}