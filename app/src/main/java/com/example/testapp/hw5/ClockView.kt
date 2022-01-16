package com.example.testapp.hw5

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.example.testapp.R
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("Recycle")
class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @Px
    private var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)

    @ColorInt
    private var borderColor: Int = DEFAULT_BORDER_COLOR
    private var backgroundClockColor: Int = DEFAULT_CLOCK_BACKGROUND_COLOR

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.ClockView)
            borderWidth = ta.getDimension(
                R.styleable.ClockView_border_width,
                context.dpToPx(DEFAULT_BORDER_WIDTH)
            )
            borderColor = ta.getColor(
                R.styleable.ClockView_border_color,
                DEFAULT_BORDER_COLOR
            )
            backgroundClockColor = ta.getColor(
                R.styleable.ClockView_background_clock_color,
                DEFAULT_CLOCK_BACKGROUND_COLOR
            )
        }
    }

    private var mHeight = 0
    private var mWidth = 0
    private var mRadius = 0
    private var mAngle = 0.0
    private var mCentreX = 0
    private var mCentreY = 0
    private var mPadding = 0
    private var mIsInit = false
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path
    private lateinit var mRect: Rect
    private lateinit var mNumbers: IntArray
    private var mMinimum = 0
    private var mHour = 0f
    private var mMinute = 0f
    private var mSecond = 0f
    private var mHourHandSize = 0
    private var mHandSize = 0
    private var mFontSize = 0
    private var mNumeralSpacing = 0
    private var mHandTruncation = 0
    private var mHourHandTruncation = 0

    private fun init() {
        mHeight = height
        mWidth = width
        mPadding = mNumeralSpacing + 50
        mCentreX = mWidth / 2
        mCentreY = mHeight / 2
        mMinimum = mHeight.coerceAtMost(mWidth)
        mRadius = mMinimum / 2 - mPadding
        mAngle = (Math.PI / 30 - Math.PI / 2)
        mPaint = Paint()
        mPath = Path()
        mRect = Rect()
        mHourHandSize = mRadius - mRadius / 2
        mHandSize = mRadius - mRadius / 4
        mNumbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        mIsInit = true
        mFontSize = (TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            18F,
            resources.displayMetrics
        )).toInt()
        mHandTruncation = mMinimum / 20
        mHourHandTruncation = mMinimum / 7
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!mIsInit) init()
        canvas.drawColor(Color.WHITE)
        drawCircle(canvas)
        drawCenter(canvas)
        drawNumeral(canvas)
        drawHands(canvas)
        drawBorder(canvas)
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHand(canvas: Canvas, loc: Double, isHour: Boolean) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius =
            if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (width / 2 + cos(angle) * handRadius).toFloat(),
            (height / 2 + sin(angle) * handRadius).toFloat(),
            mPaint
        )
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        mHour = calendar[Calendar.HOUR_OF_DAY].toFloat()
        mMinute = calendar[Calendar.MINUTE].toFloat()
        mSecond = calendar[Calendar.SECOND].toFloat()
        drawHand(canvas, (mHour + calendar.get(Calendar.MINUTE) / 60.0) * 5f, true)
        drawHand(canvas, (mMinute + calendar.get(Calendar.SECOND) / 60.0), false)
        drawHand(canvas, (calendar.get(Calendar.SECOND)).toDouble(), false)
    }

    private fun drawNumeral(canvas: Canvas) {
        mPaint.textSize = mFontSize.toFloat()
        for (number in mNumbers) {
            mPaint.color = Color.WHITE
            val tmp = number.toString()
            mPaint.getTextBounds(tmp, 0, tmp.length, mRect)
            val angle = Math.PI / 6 * (number - 3)
            val x = (width / 2 + cos(angle) * mRadius - mRect.width() / 2).toFloat()
            val y = (height / 2 + sin(angle) * mRadius + mRect.height() / 2).toFloat()
            canvas.drawText(tmp, x, y, mPaint)
        }
    }

    private fun drawCenter(canvas: Canvas) {
        mPaint.apply {
            style = Paint.Style.FILL
            color = Color.BLACK
        }
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 12F, mPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        mPaint.apply {
            reset()
            style = Paint.Style.FILL
            color = backgroundClockColor
            strokeWidth = 6F
            isAntiAlias = true
            canvas.drawCircle(
                (mWidth / 2).toFloat(),
                (mHeight / 2).toFloat(),
                (mRadius + mPadding - 10).toFloat(),
                this
            )
        }
    }

    private fun drawBorder(canvas: Canvas) {
        borderPaint.apply {
            reset()
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
            isAntiAlias = true
            canvas.drawCircle(
                (mWidth / 2).toFloat(),
                (mHeight / 2).toFloat(),
                (mRadius + mPadding - 10).toFloat(),
                borderPaint
            )
        }
    }

    private fun Context.dpToPx(dp: Int): Float {
        return dp.toFloat() * this.resources.displayMetrics.density
    }

    companion object {
        private const val DEFAULT_SIZE = 40
        private const val DEFAULT_BORDER_WIDTH = 3
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_CLOCK_BACKGROUND_COLOR = Color.RED
    }
}