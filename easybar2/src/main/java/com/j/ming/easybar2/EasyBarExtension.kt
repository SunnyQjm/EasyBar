package com.j.ming.easybar2

import android.annotation.SuppressLint
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View

/**
 * Created by sunny on 18-1-25.
 */

@SuppressLint("ResourceType")
        /**
 * 标题栏初始化
 */
fun EasyBar.init(mode: EasyBar.Mode = EasyBar.Mode.ICON_, title: String = "", leftCallback: (v: View)->Unit = {},
                 rightCallback: (v: View)->Unit = {
                     println("empty right")
                 }, @DrawableRes leftRes: Int = R.drawable.back,
                 @DrawableRes rightRes: Int = R.drawable.icon_add, leftText: String = "",
                 rightText: String = "", @StringRes titleRes: Int = -1){
    this.setDisplayMode(mode)
    if(titleRes > 0)
        this.setTitle(titleRes)
    else
        this.setTitle(title)
    this.setRightIcon(rightRes)
    this.setLeftText(leftText)
    this.setRightText(rightText)
    this.setLeftIcon(leftRes)
    this.setOnEasyBarClickListener(object : EasyBar.OnEasyBarClickListener{
        override fun onLeftIconClick(view: View) {
            println("left click")
            leftCallback(view)
        }

        override fun onRightIconClick(view: View) {
            println("right click: ${rightCallback.hashCode()}")
            rightCallback.invoke(view)
            println("right click finish: ${view.hashCode()}")
        }

    })
}