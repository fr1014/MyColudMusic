package com.fr1014.mycoludmusic.ui.block

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fr1014.frecyclerviewadapter.BaseAdapter
import com.fr1014.frecyclerviewadapter.BaseViewHolder
import com.fr1014.mycoludmusic.R
import com.fr1014.mycoludmusic.data.entity.http.wangyiyun.PlayListResult
import com.fr1014.mycoludmusic.databinding.BlockRecommendPlaylistBinding
import com.fr1014.mycoludmusic.ui.home.toplist.PlayListDetailFragment

class RecommendPlayListBlock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var mViewBinding: BlockRecommendPlaylistBinding
    private lateinit var viewAdapter: RecommendPlayListAdapter

    init {
        initView()
    }

    private fun initView() {
        mViewBinding = BlockRecommendPlaylistBinding.inflate(LayoutInflater.from(context), this, false)
        addView(mViewBinding.root)
        viewAdapter = RecommendPlayListAdapter(R.layout.item_recommend_playlist)
        mViewBinding.rvRecommend.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = viewAdapter
        }
    }

    fun bindData(playListResults: List<PlayListResult>) {
        viewAdapter.apply {
            setData(playListResults)
        }
    }

    fun setTitle(title: String) {
        mViewBinding.tvTitle.text = title
    }
}

class RecommendPlayListAdapter(layoutResId: Int) : BaseAdapter<PlayListResult, BaseViewHolder>(layoutResId),BaseAdapter.OnItemClickListener{

    override fun convert(holder: BaseViewHolder, data: PlayListResult) {
        holder.getView<TextView>(R.id.tv_description).text = data.name
        Glide.with(holder.itemView)
                .load(data.picUrl)
                .into(holder.getView(R.id.iv_cover))
        onItemClickListener = this
        holder.addOnClickListener(R.id.item)
    }

    override fun onItemClick(adapter: BaseAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            R.id.item -> {
                val data = getData(position)
                val bundle = PlayListDetailFragment.createBundle(data.id, data.name, data.picUrl)
                Navigation.findNavController(view).navigate(R.id.playListDetailFragment,bundle)
            }
            else ->{}
        }
    }

}