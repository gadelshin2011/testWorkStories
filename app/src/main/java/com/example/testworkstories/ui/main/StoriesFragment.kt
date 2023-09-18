package com.example.testworkstories.ui.main


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testworkstories.data.model.Story
import com.example.testworkstories.databinding.FragmentMainBinding
import com.example.testworkstories.ui.main.adapter.StoriesAdapter
import com.example.testworkstories.ui.main.viewModel.StoriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoriesFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: StoriesAdapter
    var checkInet = CheckInternetConnection()

    companion object {
        fun newInstance() = StoriesFragment()
    }

    private lateinit var viewModel: StoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[StoriesViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        view?.setOnClickListener {
            binding.searchStory.requestFocus()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        showStories()
        searchView()
        setListener()
    }

    private fun setListener() {
        binding.button.setOnClickListener {
            activity?.finish()
        }
    }

    private fun initAdapter() {
        val itemClickListener = object : ItemClickListener {
            override fun onItemClick(position: Int, data: Story) {
                val url = data.url
                val browser = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
                startActivity(browser)
            }
        }

        val itemFavoriteClickListener = object : ItemFavoriteClickListener {
            override fun onFavoriteClick(data: Story) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.changeLikeOnItem(data)

                }

            }

        }

        adapter = StoriesAdapter(itemClickListener, itemFavoriteClickListener)
        binding.rcViewPartners.layoutManager = GridLayoutManager(context, 2)
        binding.rcViewPartners.adapter = adapter

        binding.rcViewPartners.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState > 0) {
                    binding.searchStory.clearFocus()
                    hideKeyboard()
                }
            }
        })
    }

    private fun showStories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stories.collect {
                    adapter.setStories(it)
                }
            }
        }

        val isInternet = context?.let { checkInet.isInternetAvailable(it) }

        if (isInternet == true) {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.requestStories()
            }
        }
        if (isInternet == false) {
            binding.searchTextInputLayout.visibility = View.GONE
            binding.rcViewPartners.visibility = View.GONE
            binding.checkedInternetLayout.visibility = View.VISIBLE
        }
    }

    private fun searchView() {
        binding.searchStory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.stories.collect {
                            adapter.setListStories(it, p0.toString())

                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    fun Fragment.hideKeyboard() = ViewCompat.getWindowInsetsController(requireView())
        ?.hide(WindowInsetsCompat.Type.ime())
}
