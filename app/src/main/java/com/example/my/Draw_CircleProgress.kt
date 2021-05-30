package com.example.my

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class Draw_CircleProgress:View {
    //圆的中心点
    private var cx = 0f
    private var cy = 0f
    //圆的半径
    private var radius = 0f
    //画笔的宽度
     var paintwidth = 0f
    set(value) {
        field = value
        backgroundPaint.strokeWidth = field
    }
    //背景色
     var backgrounColor:Int = 0
    set(value) {
        field = value
        backgroundPaint.color = field
    }
    //前景色
    var forgroundColor:Int = 0
    set(value) {
        field = value
        forgroundPaint.color = field
    }
    //文本类容
  private  var loadtext = 0
    //背景圆的画笔
    private val backgroundPaint:Paint by lazy {
        Paint().apply {
      style = Paint.Style.STROKE
      color = backgrounColor
      strokeWidth = paintwidth
        }
    }
    //下载动画圆画笔
    private val forgroundPaint:Paint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE
            color = forgroundColor
            strokeWidth = paintwidth
            //画笔笔头类型
            strokeCap = Paint.Cap.ROUND
        }
    }
    //文本在y轴移到中心的距离
    private var textY = 0f
    //文本的画笔
    private val paintText:Paint by lazy {
        Paint().apply {
            color = forgroundColor
            textSize = 100f
            //只能设置水平方向的
            textAlign = Paint.Align.CENTER
            textY = (descent()-ascent())/2f
        }
    }
    //所转动的幅度度数
    private var sweepAngle = 0f
    //下载速度
    var load = 0f
    set(value) {
        field = value
        sweepAngleAnimat(field)
        textLoadChanged(field)
    }
    constructor(context: Context):super(context){}
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        //解析所有属性
        val typeArry = context.obtainStyledAttributes(attributeSet,R.styleable.Draw_CircleProgress)
        //从所有属性中将特定的属性取出,并且赋值
        //默认圆的颜色
      backgrounColor = typeArry.getColor(R.styleable.Draw_CircleProgress_backgroundColor,Color.BLUE)
        //画笔的宽度
        paintwidth = typeArry.getFloat(R.styleable.Draw_CircleProgress_paintwidth,50f).toFloat()
        //前景色
        forgroundColor = typeArry.getColor(R.styleable.Draw_CircleProgress_forgroundColre,Color.RED)
        //回收
        typeArry.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //确定中心点
        cx = measuredWidth/2f
        cy = measuredHeight/2f
        //确定圆的半径
        radius = if (measuredHeight>measuredWidth){
            measuredWidth/2f-paintwidth } else{
            measuredHeight/2f-paintwidth }
    }
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        //画背景圆
        canvas?.drawCircle(cx,cy,radius,backgroundPaint)
        //画下载运动的圆
        canvas?.drawArc(cx-radius,cy-radius,cx+radius,cy+radius,
            0f,sweepAngle,false,forgroundPaint)
       //画文本
        canvas?.drawText("$loadtext%",cx,cy+textY,paintText)
    }
    //改变圆的下载运动状态
    private fun sweepAngleAnimat(load:Float){
            sweepAngle = load*360
        invalidate()
    }
    //改变文本
    private fun textLoadChanged(load: Float){
        loadtext = (load*100).toInt()
        invalidate()
    }

}
