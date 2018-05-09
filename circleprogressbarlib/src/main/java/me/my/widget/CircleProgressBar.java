package me.my.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * description:自定义圆形进度条控件
 * Created by mingyue on 2018/5/8
 */
public class CircleProgressBar extends View {

    // 画圆环的画笔
    private Paint ringPaint;
    // 画圆线的画笔
    private Paint ringLinePaint;
    // 画文本的画笔
    private Paint textPaint;
    // 画百分号的画笔
    private Paint textPercentPaint;
    // 圆环颜色
    private int ringColor;
    // 圆环渐变颜色
    private int[] ringColors;
    // 圆线颜色
    private int ringLineColor;
    // 圆线渐变颜色
    private int[] ringLineColors;
    // 文本颜色
    private int textColor;
    // 百分号颜色
    private int textPercentColor;
    // 半径
    private float radius;
    // 圆环宽度
    private float strokeWidth;
    // 圆线宽度
    private float ringLineWidth;
    // 文本的长度
    private float textWidth;
    // 百分号文本的长度
    private float textPercentWidth;
    // 文本高度
    private float textHeight;
    // 百分号文本高度
    private float textPercentHeight;
    // 文本大小
    private float textSize;
    // 百分号文本大小
    private float textPercentSize;
    // 总进度
    private int maxProgress;
    // 当前进度
    private int currentProgress;
    // 标记进度
    private int tempProgress;
    // 百分号格式
    private String percentStyle = "%";
    // 每次刷新增加的进度
    private int stepProgress = 1;
    // 加载100%的刷新次数
    private int refreshCount = 50;
    // 渐变类型
    private Gradient gradientType = Gradient.LINEAR_FROM_TOP;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initPaint();
    }

    /**
     * 获取控件相关自定义属性的值
     *
     * @param context 上下文对象
     * @param attrs   属性对象
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
        radius = typeArray.getDimension(R.styleable.CircleProgressBar_radius, 100);
        strokeWidth = typeArray.getDimension(R.styleable.CircleProgressBar_strokeWidth, 10);
        ringLineWidth = typeArray.getDimension(R.styleable.CircleProgressBar_ringLineWidth, 2);
        ringColor = typeArray.getColor(R.styleable.CircleProgressBar_ringColor, Color.parseColor("#000000"));
        ringLineColor = typeArray.getColor(R.styleable.CircleProgressBar_ringLineColor, Color.parseColor("#000000"));
        textColor = typeArray.getColor(R.styleable.CircleProgressBar_textColor, Color.parseColor("#000000"));
        textPercentColor = typeArray.getColor(R.styleable.CircleProgressBar_textPercentColor, Color.parseColor("#000000"));
        textSize = typeArray.getDimension(R.styleable.CircleProgressBar_textSize, radius / 2);
        textPercentSize = typeArray.getDimension(R.styleable.CircleProgressBar_textPercentSize, radius / 4);
        maxProgress = typeArray.getInt(R.styleable.CircleProgressBar_maxProgress, 100);
        currentProgress = typeArray.getInt(R.styleable.CircleProgressBar_currentProgress, 0);
        typeArray.recycle();
    }

    /**
     * 初始化相关画笔
     */
    private void initPaint() {
        /**
         * 圆环画笔初始化及设置
         */
        ringPaint = new Paint();
        // 是否使用抗锯齿
        ringPaint.setAntiAlias(true);
        // 是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        ringPaint.setDither(true);
        // 设置画笔颜色
        ringPaint.setColor(ringColor);
        // 设置画笔的样式
        ringPaint.setStyle(Paint.Style.STROKE);
        // 设置笔刷的图形样式
        ringPaint.setStrokeCap(Paint.Cap.ROUND);
        // 设置笔刷的粗细度
        ringPaint.setStrokeWidth(strokeWidth);

        /**
         * 圆线画笔初始化及设置
         */
        ringLinePaint = new Paint();
        ringLinePaint.setAntiAlias(true);
        ringLinePaint.setDither(true);
        ringLinePaint.setColor(ringLineColor);
        ringLinePaint.setStyle(Paint.Style.STROKE);
        ringLinePaint.setStrokeCap(Paint.Cap.ROUND);
        ringLinePaint.setStrokeWidth(ringLineWidth);

        /**
         * 进度文本画笔初始化及设置
         */
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        /**
         * 进度文本画笔初始化及设置
         */
        textPercentPaint = new Paint();
        textPercentPaint.setAntiAlias(true);
        textPercentPaint.setStyle(Paint.Style.FILL);
        textPercentPaint.setColor(textPercentColor);
        textPercentPaint.setTextSize(textPercentSize);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (currentProgress >= 0) {
            float addDistance = strokeWidth > ringLineWidth ? strokeWidth / 2 : ringLineWidth / 2;
            // 画圆线部分
            RectF ringLineOval = new RectF(0 + addDistance, 0 + addDistance, 2 * radius + addDistance, 2 * radius + addDistance);
            canvas.drawArc(ringLineOval, 0, 360, false, ringLinePaint);

            // 画圆环部分
            RectF ringOval = new RectF(0 + addDistance, 0 + addDistance, 2 * radius + addDistance, 2 * radius + addDistance);
            canvas.drawArc(ringOval, -90, ((float) tempProgress / maxProgress) * 360, false, ringPaint);

            // 计算进度文本和百分号文本的宽度、高度
            String progressText = String.valueOf(tempProgress);
            textWidth = textPaint.measureText(progressText, 0, progressText.length());
            Paint.FontMetrics textFm = textPaint.getFontMetrics();
            textHeight = textFm.descent + Math.abs(textFm.ascent);

            String percentText = percentStyle;
            textPercentWidth = textPercentPaint.measureText(percentText, 0, percentText.length());
            Paint.FontMetrics textPercentFm = textPercentPaint.getFontMetrics();
            textPercentHeight = textPercentFm.descent + Math.abs(textPercentFm.ascent);

            // 此处得到Baseline以使文本垂直居中画出
            float baseLine = 0;
            // 如果百分号文本的高度小于等于进度文本，因为这两个文本的对齐方式是底部对齐，所以要以进度文本的底部为齐，否则以百分号文本底部为齐
            if (textPercentHeight <= textHeight)
                baseLine = (2 * (addDistance + radius) - textFm.bottom + textFm.top) / 2 - textFm.top;
            else
                baseLine = (2 * (addDistance + radius) - textPercentFm.bottom + textPercentFm.top) / 2 - textPercentFm.top;

            // 画进度文本部分
            canvas.drawText(progressText, radius - textWidth / 2 - textPercentWidth / 2 + addDistance, baseLine, textPaint);

            // 画百分号文本部分
            canvas.drawText(percentText, radius - textPercentWidth / 2 + addDistance + textWidth / 2, baseLine, textPercentPaint);

            if (tempProgress < currentProgress) {
                if ((currentProgress - tempProgress) >= stepProgress)
                    tempProgress += stepProgress;
                else
                    tempProgress++;
                postInvalidate();
            }
        }
    }

    /**
     * 设置最大进度
     *
     * @param maxProgress 最大进度值
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 设置当前进度（调用此方法开始绘制）
     *
     * @param currentProgress 当前进度值
     */
    public void setProgress(int currentProgress) {
        if (currentProgress > maxProgress)
            return;
        this.currentProgress = currentProgress;

        setGradient();

        if (maxProgress >= 50)
            stepProgress = maxProgress / refreshCount;
        tempProgress = 0;
        postInvalidate();
    }

    /**
     * 设置圆环颜色（渐变色）
     *
     * @param colors 渐变颜色数组
     */
    public void setRingColor(int[] colors) {
        this.ringColors = colors;
    }

    /**
     * 设置圆环颜色（单色）
     *
     * @param color 圆环颜色值
     */
    public void setRingColor(int color) {
        this.ringColor = color;
        ringPaint.setShader(null);
        ringPaint.setColor(ringColor);
    }

    /**
     * 设置圆环宽度
     *
     * @param ringWidth 圆环宽度值
     */
    public void setRingWidth(float ringWidth) {
        this.strokeWidth = ringWidth;
        ringPaint.setStrokeWidth(strokeWidth);
    }

    /**
     * 设置圆线颜色（渐变色）
     *
     * @param colors 渐变颜色数组
     */
    public void setRingLineColor(int[] colors) {
        this.ringLineColors = colors;
    }

    /**
     * 设置圆线颜色（单色）
     *
     * @param color 圆线颜色值
     */
    public void setRingLineColor(int color) {
        this.ringLineColor = color;
        ringLinePaint.setShader(null);
        ringLinePaint.setColor(ringLineColor);
    }

    /**
     * 设置圆线宽度
     *
     * @param lineWidth 圆线宽度值
     */
    public void setRingLineWidth(float lineWidth) {
        this.ringLineWidth = lineWidth;
        ringPaint.setStrokeWidth(ringLineWidth);
    }

    /**
     * 设置百分号样式
     *
     * @param percentStyle 百分号样式，默认%
     */
    public void setPercentStyle(String percentStyle) {
        this.percentStyle = percentStyle;
    }

    /**
     * 设置进度文本颜色
     *
     * @param color 进度文本颜色值
     */
    public void setTextColor(int color) {
        this.textColor = color;
        textPaint.setColor(textColor);
    }

    /**
     * 设置进度文本字体大小
     *
     * @param textsize 进度文本字体大小值
     */
    public void setTextSize(float textsize) {
        this.textSize = textsize;
        textPaint.setTextSize(textSize);
    }

    /**
     * 设置百分号文本颜色
     *
     * @param color 百分号文本颜色值
     */
    public void setTextPercentColor(int color) {
        this.textPercentColor = color;
        textPercentPaint.setColor(textPercentColor);
    }

    /**
     * 设置百分号文本字体大小
     *
     * @param textsize 百分号文本字体大小值
     */
    public void setTextPercentSize(float textsize) {
        this.textPercentSize = textsize;
        textPercentPaint.setTextSize(textPercentSize);
    }

    /**
     * 加载100%刷新的次数（值越大加载展示越慢）
     *
     * @param count 刷新次数
     */
    public void setRefreshCount(int count) {
        this.refreshCount = count;
    }

    /**
     * 设置渐变样式
     *
     * @param gradientType 渐变样式
     */
    public void setGradientType(Gradient gradientType) {
        this.gradientType = gradientType;
    }

    /**
     * 设置圆环半径
     *
     * @param radiusValue 圆环半径值
     */
    public void setRadius(float radiusValue) {
        this.radius = radiusValue;
    }

    /**
     * 设置画笔渐变
     */
    private void setGradient() {
        // 设置圆环的渐变类型
        if (ringColors != null && ringColors.length > 0) {
            switch (gradientType) {
                case LINEAR_FROM_TOP:
                    LinearGradient ringGradientTop = new LinearGradient(radius / 2, 0, radius / 2, 2 * radius,
                            ringColors, null, Shader.TileMode.CLAMP);
                    ringPaint.setShader(ringGradientTop);
                    break;

                case LINEAR_FROM_LEFT:
                    LinearGradient ringGradientBottom = new LinearGradient(0, radius / 2, radius * 2, radius / 2,
                            ringColors, null, Shader.TileMode.CLAMP);
                    ringPaint.setShader(ringGradientBottom);
                    break;

                case SWEEP:
                    SweepGradient ringGradient = new SweepGradient(radius, radius, ringColors, null);
                    Matrix matrix = new Matrix();
                    matrix.setRotate(-90, radius, radius);
                    ringGradient.setLocalMatrix(matrix);
                    ringPaint.setShader(ringGradient);
                    break;
            }
        }

        // 设置圆线的渐变类型
        if (ringLineColors != null && ringLineColors.length > 0) {
            switch (gradientType) {
                case LINEAR_FROM_TOP:
                    LinearGradient gradientTop = new LinearGradient(radius / 2, 0, radius / 2, 2 * radius,
                            ringLineColors, null, Shader.TileMode.CLAMP);
                    ringLinePaint.setShader(gradientTop);
                    break;

                case LINEAR_FROM_LEFT:
                    LinearGradient gradientBottom = new LinearGradient(0, radius / 2, radius * 2, radius / 2,
                            ringLineColors, null, Shader.TileMode.CLAMP);
                    ringLinePaint.setShader(gradientBottom);
                    break;

                case SWEEP:
                    SweepGradient ringLineGradient = new SweepGradient(radius, radius, ringLineColors, null);
                    Matrix matrix = new Matrix();
                    matrix.setRotate(-90, radius, radius);
                    ringLineGradient.setLocalMatrix(matrix);
                    ringLinePaint.setShader(ringLineGradient);
                    break;
            }
        }
    }

    // 渐变类型枚举
    public enum Gradient {
        //由上向下线性渐变
        LINEAR_FROM_TOP,
        //由左向右线性渐变
        LINEAR_FROM_LEFT,
        //环形渐变
        SWEEP
    }
}



