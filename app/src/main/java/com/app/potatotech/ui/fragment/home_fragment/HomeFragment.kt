package com.app.potatotech.ui.fragment.home_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.potatotech.R
import com.app.potatotech.adapter.PostAdapter
import com.app.potatotech.adapter.callback.AdapterClickListener
import com.app.potatotech.adapter.callback.AdapterType
import com.app.potatotech.data.PostData
import com.app.potatotech.ui.activity.post_ui.PostUI
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), AdapterClickListener {

    lateinit var viewModel: HomeFragmentViewModel
    private var postAdapter: PostAdapter? = null
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)

        viewModel.posts.observe(this) {
            postAdapter?.setList(it)
            if (it.size == 10) {
                page++
            }
        }

        postAdapter = PostAdapter().also {
            it.bottomReachedListener = this::onBottomReached
            it.onActionClicked = this::onAdapterAction
            it.adapterClickListener = this
        }
        postHolder?.apply {
            this.adapter = postAdapter
            this.layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }

        viewModel.load(false)

    }

    private fun onAdapterAction(type: Int, s: String?) {
        if (type == 1) {
            activity?.let {
                ShareCompat.IntentBuilder.from(it).apply {
                    this.setType("text/plain")
                    this.setText(s)
                }.also { share ->
                    share.startChooser()
                }
            }
        }
    }

    private fun onBottomReached() {
        viewModel.load(true)
    }

    override fun onClicked(type: AdapterType, data: Any) {
        context?.startActivity(Intent(context, PostUI::class.java).apply {
            this.putExtras(Bundle().apply {
                this.putSerializable("DATA", data as PostData)
            })
        })
    }

    override fun onLongPressed(type: AdapterType, data: Any) {
        TODO("Not yet implemented")
    }

}