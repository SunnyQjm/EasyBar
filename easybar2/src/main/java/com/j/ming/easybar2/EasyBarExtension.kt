package com.j.ming.easybar2

import android.annotation.SuppressLint
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View

/**
 * Created by sunny on 18-1-25.
 */

class EasyBarParams(val mode: EasyBar.Mode = EasyBar.Mode.ICON_, val title: String = "", val leftCallback: (v: View) -> Unit = {},
                    val rightCallback: (v: View) -> Unit = {
                        println("empty right")
                    }, @DrawableRes val leftRes: Int = R.drawable.back,
                    @DrawableRes val rightRes: Int = R.drawable.icon_add, val leftText: String = "",
                    val rightText: String = "", @StringRes val titleRes: Int = -1){
}

fun EasyBar.init(easyBarParams: EasyBarParams){
    init(easyBarParams.mode, easyBarParams.title, easyBarParams.leftCallback, easyBarParams.rightCallback, easyBarParams.leftRes,
            easyBarParams.rightRes, easyBarParams.leftText, easyBarParams.rightText, easyBarParams.titleRes)
}

/**
 * 标题栏初始化
 */
@SuppressLint("ResourceType")
fun EasyBar.init(mode: EasyBar.Mode = EasyBar.Mode.ICON_, title: String = "", leftCallback: (v: View) -> Unit = {},
                 rightCallback: (v: View) -> Unit = {
                     println("empty right")
                 }, @DrawableRes leftRes: Int = R.drawable.back,
                 @DrawableRes rightRes: Int = R.drawable.icon_add, leftText: String = "",
                 rightText: String = "", @StringRes titleRes: Int = -1) {
    this.setDisplayMode(mode)
    if (titleRes > 0)
        this.setTitle(titleRes)
    else
        this.setTitle(title)
    this.setRightIcon(rightRes)
    this.setLeftText(leftText)
    this.setRightText(rightText)
    this.setLeftIcon(leftRes)
    this.setOnEasyBarClickListener(object : EasyBar.OnEasyBarClickListener {
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