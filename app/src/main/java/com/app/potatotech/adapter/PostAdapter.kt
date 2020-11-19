package com.app.potatotech.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.potatotech.R
import com.app.potatotech.adapter.callback.AdapterClickListener
import com.app.potatotech.adapter.callback.AdapterType
import com.app.potatotech.data.PostData
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.post_view_large.view.*

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var bottomReachedListener: (() -> Unit)? = null
    var onActionClicked: ((type: Int, link: String?) -> Unit)? = null

    var adapterClickListener : AdapterClickListener? = null

    private var list = ArrayList<PostData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_view_large, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
        if (position == list.size - 1) {
            bottomReachedListener?.invoke()
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(newList: ArrayList<PostData>) {
        this.list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.imageView?.setColorFilter(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.black_100
                ), PorterDuff.Mode.SRC_OVER
            )
        }

        fun bind(postData: PostData) {
            itemView.apply {
                Glide.with(this.context).asBitmap().load(postData.imageList[1]).into(this.imageView)
                this.nameView.text = "By ${postData.author}"
                this.titleView.text = postData.title
                this.shareBtn.setOnClickListener { onActionClicked?.invoke(1, postData.link) }
                this.loveBtn.apply {
                    if (postData.loved) {
                        this.setImageResource(R.drawable.ic_love)
                        this.setTint(R.color.red)
                    } else {
                        this.setImageResource(R.drawable.ic_love_outline)
                    }
                    this.setOnClickListener {
                        postData.loved = !postData.loved
                        notifyItemChanged(adapterPosition)
                    }
                }
                this.imageView.setOnClickListener { adapterClickListener?.onClicked(AdapterType.POST, postData) }
            }
        }

    }

    fun ShapeableImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
        )
    }

}