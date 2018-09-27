package com.j.ming.easybar2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView


/**
 * Created by Sunny on 2017/8/17 0017.
 */

class EasyBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {
    private var title: String? = null
    @Dimension
    private var title_size: Int = 0
    @ColorInt
    private var title_color: Int = 0
    @DrawableRes
    private var leftIcon: Int = 0
    @DrawableRes
    private var rightIcon: Int = 0
    @Dimension
    private var icon_size: Int = 0
    @Dimension
    private var icon_margin: Int = 0

    private var leftText: String = ""
    private var rightText: String = ""
    private var displayMode: Mode = Mode.TEXT


    private lateinit var imgLeft: ImageView
    private lateinit var imgRight: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvLeftText: TextView
    private lateinit var tvRightText: TextView
    private var mListener: OnEasyBarClickListener? = null

    init {
        initAttr(context, attrs)
        initView(context)
    }

    private fun initView(context: Context) {

        //left icon
        imgLeft = ImageView(context)
        imgLeft.setImageResource(leftIcon)
        imgLeft.scaleType = ImageView.ScaleType.FIT_CENTER
        val imgLeftParam = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        imgLeftParam.addRule(RelativeLayout.CENTER_VERTICAL)
        imgLeftParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        imgLeftParam.width = icon_size
        imgLeftParam.height = icon_size
        imgLeft.layoutParams = imgLeftParam
        imgLeft.setPadding(icon_margin, icon_margin, icon_margin, icon_margin)
        addView(imgLeft, imgLeftParam)

        imgLeft.setOnClickListener { v ->
            mListener?.onLeftIconClick(v)
        }

        //center title
        tvTitle = TextView(context)
        tvTitle.ellipsize = TextUtils.TruncateAt.END
        tvTitle.setSingleLine()
        tvTitle.text = title
        tvTitle.textSize = title_size.toFloat()
        tvTitle.setTextColor(title_color)
        val tvTitleParam = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        val titleMargin = icon_size
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tvTitleParam.marginStart = titleMargin
            tvTitleParam.marginEnd = titleMargin
        } else {
            tvTitleParam.leftMargin = titleMargin
            tvTitleParam.rightMargin = titleMargin
        }
        tvTitleParam.addRule(RelativeLayout.CENTER_IN_PARENT)
        tvTitle.layoutParams = tvTitleParam
        addView(tvTitle, tvTitleParam)

        //left text
        tvLeftText = TextView(context)
        tvLeftText.textSize = title_size.toFloat()
        tvLeftText.setTextColor(title_color)
        val tv_left_text_param = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        tv_left_text_param.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        tv_left_text_param.addRule(RelativeLayout.CENTER_VERTICAL)
        tv_left_text_param.leftMargin = icon_margin
        tvLeftText.layoutParams = tv_left_text_param
        addView(tvLeftText)
        tvLeftText.text = leftText
        tvLeftText.visibility = View.INVISIBLE
        tvLeftText.setOnClickListener { v ->
            mListener?.onLeftIconClick(v)
        }


        //right text
        tvRightText = TextView(context)
        tvRightText.textSize = title_size.toFloat()
        tvRightText.setTextColor(title_color)
        val tvRightTextParam = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        tvRightTextParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        tvRightTextParam.addRule(RelativeLayout.CENTER_VERTICAL)
        tvRightTextParam.rightMargin = icon_margin
        tvRightText.layoutParams = tvRightTextParam
        addView(tvRightText)
        tvRightText.text = rightText
        tvRightText.visibility = View.INVISIBLE
        tvRightText.setOnClickListener { v ->
            mListener?.onRightIconClick(v)
        }

        //right icon
        imgRight = ImageView(context)
        imgRight.setImageResource(rightIcon)
        imgRight.scaleType = ImageView.ScaleType.FIT_CENTER
        val imgRightParam = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        imgRightParam.addRule(RelativeLayout.CENTER_VERTICAL)
        imgRightParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        imgRightParam.width = icon_size
        imgRightParam.height = icon_size
        imgRight.layoutParams = imgRightParam
        imgRight.setPadding(icon_margin, icon_margin, icon_margin, icon_margin)
        addView(imgRight, imgRightParam)

        imgRight.visibility = View.INVISIBLE
        imgRight.setOnClickListener { v ->
            mListener?.onRightIconClick(v)
        }

        setDisplayMode(displayMode)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.EasyBar)
        title = ta.getString(R.styleable.EasyBar_title)
        title_size = DensityUtil.px2sp(context,
                ta.getDimensionPixelSize(R.styleable.EasyBar_title_size,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics).toInt()).toFloat())
        title_color = ta.getColor(R.styleable.EasyBar_title_color, resources.getColor(R.color.text_gray))
        leftIcon = ta.getResourceId(R.styleable.EasyBar_left_icon, R.drawable.back)
        rightIcon = ta.getResourceId(R.styleable.EasyBar_right_icon, R.drawable.icon_add)
        icon_size = ta.getDimensionPixelSize(R.styleable.EasyBar_icon_size, DensityUtil.dip2px(context, 24f))
        icon_margin = ta.getDimensionPixelOffset(R.styleable.EasyBar_icon_margin, DensityUtil.dip2px(context, 12f))
        leftText = ta.getString(R.styleable.EasyBar_left_text)
        rightText = ta.getString(R.styleable.EasyBar_right_text)
        val mode = ta.getInt(R.styleable.EasyBar_displayMode, 0)
        displayMode = when (mode) {
            0 -> Mode.ICON
            1 -> Mode.TEXT
            else -> Mode.ICON
        }
        ta.recycle()
    }

    fun setTitle(title: String) {
        this.title = title
        tvTitle.text = title
    }

    fun setTitle(@StringRes titleRes: Int) {
        this.title = context.getString(titleRes)
        tvTitle.setText(titleRes)
    }

    fun setLeftText(text: String) {
        this.leftText = leftText
        tvLeftText.text = text
    }

    fun setRightText(text: String) {
        this.rightText = text
        tvRightText.text = text
    }

    fun setOnEasyBarClickListener(listener: OnEasyBarClickListener) {
        this.mListener = listener
    }


    fun setRightIcon(drawable: Drawable) {
        imgRight.setImageDrawable(drawable)
    }

    fun setRightIcon(bitmap: Bitmap) {
        imgRight.setImageBitmap(bitmap)
    }

    fun setRightIcon(@DrawableRes res: Int) {
        imgRight.setImageResource(res)
    }

    fun getRightIcon(): View? {
        return imgRight
    }

    fun getRightText(): String? {
        return tvRightText.text.toString()
    }

    fun setLeftIcon(drawable: Drawable) {
        imgLeft.setImageDrawable(drawable)
    }

    fun setLeftIcon(bitmap: Bitmap) {
        imgLeft.setImageBitmap(bitmap)
    }

    fun setLeftIcon(@DrawableRes res: Int) {
        imgLeft.setImageResource(res)
    }

    fun getLeftIcon(): View? {
        return imgLeft
    }

    interface OnEasyBarClickListener {
        /**
         * 如果是图标模式，则响应左图标点击，否则响应左文字点击
         *
         * @param view
         */
        fun onLeftIconClick(view: View)

        /**
         * 如果是图标模式，则响应右图标点击，否则响应右文字点击
         *
         * @param view
         */
        fun onRightIconClick(view: View)
    }


    enum class Mode {
        ICON, TEXT, ICON_TEXT, TEXT_ICON, ICON_, TEXT_, _ICON, _TEXT, NONE
    }

    fun setDisplayMode(mode: Mode?) {
        when (mode) {
            Mode.ICON -> {
                tvLeftText.visibility = View.INVISIBLE
                tvRightText.visibility = View.INVISIBLE
                imgLeft.visibility = View.VISIBLE
                imgRight.visibility = View.VISIBLE

            }
            Mode.TEXT -> {
                tvLeftText.visibility = View.VISIBLE
                tvRightText.visibility = View.VISIBLE
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.INVISIBLE
            }
            Mode.ICON_TEXT -> {
                tvLeftText.visibility = View.INVISIBLE
                tvRightText.visibility = View.VISIBLE
                imgLeft.visibility = View.VISIBLE
                imgRight.visibility = View.INVISIBLE
            }
            Mode.TEXT_ICON -> {
                tvLeftText.visibility = View.VISIBLE
                tvRightText.visibility = View.INVISIBLE
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.VISIBLE
            }
            Mode.TEXT_ -> {
                tvLeftText.visibility = View.VISIBLE
                tvRightText.visibility = View.INVISIBLE
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.INVISIBLE
            }
            Mode.ICON_ -> {
                tvLeftText.visibility = View.INVISIBLE
                tvRightText.visibility = View.INVISIBLE
                imgLeft.visibility = View.VISIBLE
                imgRight.visibility = View.INVISIBLE
            }
            Mode._ICON -> {
                tvLeftText.visibility = View.INVISIBLE
                tvRightText.visibility = View.INVISIBLE
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.VISIBLE
            }
            Mode._TEXT -> {
                tvLeftText.visibility = View.INVISIBLE
                tvRightText.visibility = View.VISIBLE
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.INVISIBLE
            }
            Mode.NONE -> {
                tvLeftText.visibility = View.INVISIBLE
                tvRightText.visibility = View.INVISIBLE
                imgLeft.visibility = View.INVISIBLE
                imgRight.visibility = View.INVISIBLE
            }
        }
    }
}
