package com.fr1014.mycoludmusic.ui.block

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import com.fr1014.mycoludmusic.R
import com.fr1014.mycoludmusic.databinding.BlockHomeClickBinding

class HomeTopClickBlock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var mViewBinding : BlockHomeClickBinding? = null

    init {
        initView()
    }

    private fun initView() {
        mViewBinding = BlockHomeClickBinding.inflate(LayoutInflater.from(context), this)

        mViewBinding?.apply {
            ivLike.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.userInfoFragment)
            }

            ivTop.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.topListFragment)
            }
        }
    }
}