package com.photon.xome.home.view

import android.graphics.Rect
import android.view.View
import android.widget.Space
import androidx.recyclerview.widget.RecyclerView

class ItemSpaceDecoration constructor(val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
    }
}