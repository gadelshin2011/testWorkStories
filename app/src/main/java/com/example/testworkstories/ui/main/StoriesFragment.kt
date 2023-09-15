package com.example.testworkstories.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testworkstories.R
import com.example.testworkstories.databinding.FragmentMainBinding
import com.example.testworkstories.ui.main.adapter.StoriesAdapter
import com.example.testworkstories.ui.main.viewModel.StoriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoriesFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: StoriesAdapter

    companion object {
        fun newInstance() = StoriesFragment()
    }

    private lateinit var viewModel: StoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[StoriesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        showStories()


    }

    private fun init() {
        val itemClickListener = object : ItemClickListener {
            override fun onItemClickCategory(position: Int) {
//                val browser = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("")
//                )
//                startActivity(browser)
            }
        }
        adapter = StoriesAdapter(itemClickListener)
        binding.rcViewPartners.layoutManager = GridLayoutManager(context, 2)
        binding.rcViewPartners.adapter = adapter
    }


    private fun showStories() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stories.collect {
                     adapter.setListStories(it)
                }
            }
        }


        lifecycleScope.launch(Dispatchers.IO) {
            val data = viewModel.requestStories()
            lifecycleScope.launch {
                if (data == R.string.loading_successful.toString()) {
                   // Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}