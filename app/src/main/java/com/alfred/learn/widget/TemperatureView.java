package com.alfred.learn.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.alfred.learn.R;

/**
 * 步骤：
 * 1、整个背景圆（可有可无）
 * 2、进度弧（分为三段，颜色分别为绿黄红）
 * 3、进度弧上的文字（正常，预警，警告）
 * 4、刻度弧（紧靠着进度弧内侧的黑色弧）
 * 5、刻度
 * 6、中间的圆
 * 7、指针
 * 8、当前温度
 * <p>
 * Created by alfred on 16/8/31.
 */
public class TemperatureView extends View {

    private float progressWidth;
    private String tempText;
    private float tempTextSize;

    private Paint outCirclePaint; // 整个背景圆
    private Paint progressPaint; // 进度
    private Paint scaleArcPaint; // 刻度弧
    private Paint scalePaint; // 刻度
    private Paint panelTextPaint; // 表盘文字
    private Paint progressTextPaint; // 进度条上的文字
    private Paint pointPaint; // 中心圆
    private Paint leftPointerPaint; // 表针左半部分
    private Paint rightPointerPaint; // 表针右半部分
    private Paint pointerCirclePaint; // 表针的圆轴

    private int mSize; // 最终的大小
    private static final int PADDING = 15; // 进度的宽度
    private static final int OFFSET = 5;
    private String scale; // 刻度数值
    private int mTikeCount = 40; // 40条刻度(包括长短)
    private int mLongTikeHeight = dp2px(10); // 长刻度
    private int mShortTikeHeight = dp2px(5); // 短刻度
    private int progressRadius; // 进度弧的半径
    private int scaleArcRadius; // 刻度弧的半径
    private int pointRadius = dp2px(17); // 中心圆半径

    private float currentTemp;

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.temperatureProgress);
        progressWidth = ta.getDimension(R.styleable.temperatureProgress_progressWidth, PADDING);
        tempText = ta.getString(R.styleable.temperatureProgress_tempText);
        tempTextSize = ta.getDimension(R.styleable.temperatureProgress_tempTextSize, sp2px(15));
        ta.recycle();

        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int realWidth = startMeasure(widthMeasureSpec);
        int realHeight = startMeasure(heightMeasureSpec);

        /**
         * 因为是以正方形为基础
         */
        mSize = Math.min(realHeight, realWidth);
        setMeasuredDimension(mSize, mSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将画布移到中央
        canvas.translate(mSize / 2, mSize / 2);

        // 画最外面的圆
        drawOutCircle(canvas);
        // 画进度
        drawProgress(canvas);
        // 画进度上的文字
        drawProgressText(canvas);
        // 画表盘
        drawPanel(canvas);
    }


    /**
     * 最外面的圆
     *
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        // 已经将画布移到中心，所以圆心为（0,0）
        // mSize 是控件的大小，mSize /2-dp2px(1) 是让这个背景圆和控件有点距离。
        canvas.drawCircle(0, 0, mSize / 2 - dp2px(1), outCirclePaint);
        canvas.save();
    }

    /**
     * 进度弧
     * <p>
     * 整个进度弧是 从150度的位置开始到30度为止，也就是说整个进度弧所占的角度为 240度。将它分为 40份（也就是 最高40度 的意思），所以刚好 每份是6度。
     * <p>
     * 给出的效果图中进度弧是 绿黄红 的，在这里的绘制顺序却是 绿红黄，这是因为我想要将进度弧的两端（绿和红）设置为圆角，而黄色与绿色和红色的衔接处为直角。
     * <p>
     * 如果在这里的 Paint 只是设置为 Paint.Cap.ROUND ，那么中间黄色部分和红绿的衔接处也是圆角的；如果按照绿黄红的顺序来绘制，那么黄色和红色衔接处是圆角的，而绿色和黄色衔接处是直角的
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        // dp2px(10):留一点位置（可有可无）
        progressRadius = mSize / 2 - dp2px(10);
        canvas.save();
        RectF rectF = new RectF(-progressRadius, -progressRadius, progressRadius, progressRadius);

        // 设置为圆角
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(Color.GREEN);
        // 从150度位置开始，经过120度
        canvas.drawArc(rectF, 150, 120, false, progressPaint);
        progressPaint.setColor(Color.RED);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, 330, 60, false, progressPaint);
        progressPaint.setColor(Color.YELLOW);
        progressPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawArc(rectF, 270, 60, false, progressPaint);

        canvas.restore();
    }

    /**
     * 进度上的文字
     * <p>
     * 如果直接 drawText ，那么绘制的文字是水平的，想要把文字绘制在进度弧上，得先旋转一定的角度。拿绿色这段弧来说：它占了 120度，所以中间位置是 60度，那么就需要 旋转60度。
     * <p>
     * 有一点要注意的是，绿色这段弧在左边，所以要 逆时针旋转60度（旋转角度为 负数是逆时针，正数是顺时针）。第二段弧上的文字为什么旋转了90度呢？它只需要旋转30度就可以了啊？那是因为第一段弧时 旋转了－60度（逆时针），而第二段是从第一段的基础上旋转过来的，所以需要 顺时针旋转90度。
     *
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {
        canvas.save();
        String normal = "正常";
        String warn = "预警";
        String danger = "警告";
        // 因为文字在进度弧上，所以要旋转一定的角度
        canvas.rotate(-60, 0, 0);
        progressTextPaint.setTextSize(sp2px(12));
        canvas.drawText(normal, -dp2px(12), -scaleArcRadius - dp2px(4), progressTextPaint);
        canvas.rotate(90, 0, 0);
        canvas.drawText(warn, -dp2px(12), -scaleArcRadius - dp2px(4), progressTextPaint);
        canvas.rotate(60, 0, 0);
        canvas.drawText(danger, -dp2px(12), -scaleArcRadius - dp2px(4), progressTextPaint);
        canvas.rotate(-60, 0, 0);
        canvas.restore();
    }


    /**
     * 表盘
     *
     * @param canvas
     */
    private void drawPanel(Canvas canvas) {
        // 画刻度弧
        drawScaleArc(canvas);
        // 画中间圆
        drawInPoint(canvas);
        // 画指针
        drawPointer(canvas);
        // 绘制文字
        drawPanelText(canvas);
    }

    /**
     * 表盘上的文字
     *
     * @param canvas
     */
    private void drawPanelText(Canvas canvas) {

        canvas.save();
        String text = "当前温度";
        float length = panelTextPaint.measureText(text);
        panelTextPaint.setTextSize(sp2px(15));
        canvas.drawText(text, -length / 2, scaleArcRadius / 2 + dp2px(20), panelTextPaint);
        String temp = currentTemp + " ℃";
        panelTextPaint.setTextSize(sp2px(15));
//        panelTextPaint.setColor(tempTextColor);
        float tempTextLength = panelTextPaint.measureText(temp);
        canvas.drawText(temp, -tempTextLength / 2, scaleArcRadius, panelTextPaint);
        canvas.restore();

    }

    /**
     * 指针（这里分为左右部分是为了画出来的指针有立体感）
     * <p>
     * 其实指针部分可以一次性绘制完，但是为了看起来有点立体感，就将它分为左右两部分，但是都是类似的。
     * <p>
     * 就拿左半部分来说：首先将画笔移动到中心圆的边缘 ( leftPointerPath.moveTo(pointRadius/2, 0)  )，然后来个弧度是让指针的根部为弧形的，接下来是指针的长度（长度＝刻度弧的半径(scaleArcRadius) － 长刻度的长度(mLongTikeHeight) － 刻度值与刻度的间隔(dp2px(15)) － 尾部与刻度值的间隔(dp2px(OFFSET))），最后再将它们连接起来。
     * <p>
     * 指针上的圆意思是指针根部灰色的小圆，这样看起来指针的根部是有个小洞的。
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        RectF rectF = new RectF(-pointRadius / 2, -pointRadius / 2, pointRadius / 2, pointRadius / 2);
        canvas.save();
        // 先将指针与刻度0位置对齐
        canvas.rotate(60, 0, 0);
        float angle = currentTemp * 6.0f;
        canvas.rotate(angle, 0, 0);
        // 表针左半部分
        Path leftPointerPath = new Path();
        leftPointerPath.moveTo(pointRadius / 2, 0);
        leftPointerPath.addArc(rectF, 0, 360);
        leftPointerPath.lineTo(0, scaleArcRadius - mLongTikeHeight - dp2px(OFFSET) - dp2px(15));
        leftPointerPath.lineTo(-pointRadius / 2, 0);
        leftPointerPath.close();
        // 表针右半部分
        Path rightPointerPath = new Path();
        rightPointerPath.moveTo(-pointRadius / 2, 0);
        rightPointerPath.addArc(rectF, 0, -180);
        rightPointerPath.lineTo(0, scaleArcRadius - mLongTikeHeight - dp2px(OFFSET) - dp2px(15));
        rightPointerPath.lineTo(0, pointRadius / 2);
        rightPointerPath.close();
        // 表针的圆
        Path circlePath = new Path();
        circlePath.addCircle(0, 0, pointRadius / 4, Path.Direction.CW);
        canvas.drawPath(leftPointerPath, leftPointerPaint);
        canvas.drawPath(rightPointerPath, rightPointerPaint);
        canvas.drawPath(circlePath, pointerCirclePaint);
        canvas.restore();
    }

    /**
     * 中心圆
     *
     * @param canvas
     */
    private void drawInPoint(Canvas canvas) {
        canvas.save();
        canvas.drawCircle(0, 0, pointRadius, pointPaint);
        canvas.restore();
    }

    /**
     * 刻度弧
     * <p>
     * 这里只给出了右半部分的刻度，左半部分类似。绘制时先是旋转角度（一共240度，分成40份，每次旋转6度），然后再画直线；         画直线时还要判断如果是 5的倍数，就画长一点，并且标上刻度值，就是那些 0，5，10，15…
     * 这里是右半部分，所以刻度值是 从20开始，总共是 20个刻度。最后要注意画布回正 canvas.rotate(-mAngle * mTikeCount/2 - 6, 0, 0) 。
     *
     * @param canvas
     */
    private void drawScaleArc(Canvas canvas) {
        // 刻度弧紧靠进度弧
        scaleArcRadius = mSize / 2 - (dp2px(15) + dp2px(PADDING) / 4);
        canvas.save();
        // 画弧
        RectF rectF = new RectF(-scaleArcRadius, -scaleArcRadius,
                scaleArcRadius, scaleArcRadius);
        canvas.drawArc(rectF, 150, 240, false, scaleArcPaint);

        // 旋转的角度
        float mAngle = 240f / mTikeCount;
        // 画右半部分的刻度
        for (int i = 0; i <= mTikeCount / 2; i++) {
            // 5的倍数就画长刻度，并标上刻度数值
            if (i % 5 == 0) {
                scale = 20 + i + "";
                panelTextPaint.setTextSize(sp2px(15));
                float scaleWidth = panelTextPaint.measureText(scale);
                canvas.drawLine(0, -scaleArcRadius, 0, -scaleArcRadius + mLongTikeHeight, scalePaint);
                canvas.drawText(scale, -scaleWidth / 2, -scaleArcRadius + mLongTikeHeight + dp2px(15), panelTextPaint);
            } else {
                canvas.drawLine(0, -scaleArcRadius, 0, -scaleArcRadius + mShortTikeHeight, scalePaint);
            }
            canvas.rotate(mAngle, 0, 0);
        }
        // 画布回正
        canvas.rotate(-mAngle * mTikeCount / 2 - 6, 0, 0);
        // 画左半部分的刻度
        for (int i = 0; i <= mTikeCount / 2; i++) {
            if (i % 5 == 0) {
                scale = 20 - i + "";
                panelTextPaint.setTextSize(sp2px(15));
                float scaleWidth = panelTextPaint.measureText(scale);
                canvas.drawLine(0, -scaleArcRadius, 0, -scaleArcRadius + mLongTikeHeight, scalePaint);
                canvas.drawText(scale, -scaleWidth / 2, -scaleArcRadius + mLongTikeHeight + dp2px(15), panelTextPaint);
            } else {
                canvas.drawLine(0, -scaleArcRadius, 0, -scaleArcRadius + mShortTikeHeight, scalePaint);
            }
            canvas.rotate(-mAngle, 0, 0);
        }
        // 画布回正
        canvas.rotate(-mAngle * mTikeCount / 2 + 6, 0, 0);
        canvas.restore();
    }


    /**
     * 测量大小
     *
     * @param whSpec
     * @return
     */
    private int startMeasure(int whSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(whSpec);
        int mode = MeasureSpec.getMode(whSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = dp2px(200);
        }
        return result;
    }

    /**
     * 将 dp 转换为 px
     *
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        outCirclePaint = new Paint();
        progressPaint = new Paint();
        scaleArcPaint = new Paint();
        scalePaint = new Paint();
        panelTextPaint = new Paint();
        progressTextPaint = new Paint();
        pointPaint = new Paint();
        leftPointerPaint = new Paint();
        rightPointerPaint = new Paint();
        pointerCirclePaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(dp2px(PADDING));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeJoin(Paint.Join.ROUND);
        outCirclePaint.setAntiAlias(true);
        outCirclePaint.setStrokeWidth(5);
        outCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        outCirclePaint.setColor(getResources().getColor(R.color.temperatureBackground));
        scaleArcPaint.setAntiAlias(true);
        scaleArcPaint.setStrokeWidth(dp2px(2));
        scaleArcPaint.setStyle(Paint.Style.STROKE);
        scalePaint.setAntiAlias(true);
        scalePaint.setStrokeWidth(5);
        scalePaint.setStyle(Paint.Style.STROKE);
        panelTextPaint.setAntiAlias(true);
        panelTextPaint.setStyle(Paint.Style.FILL);
        panelTextPaint.setColor(Color.BLACK);
        progressTextPaint.setAntiAlias(true);
        progressTextPaint.setStyle(Paint.Style.FILL);
        progressTextPaint.setColor(Color.BLACK);
        progressTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.GRAY);
        leftPointerPaint.setAntiAlias(true);
        leftPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        leftPointerPaint.setColor(getResources().getColor(R.color.leftPointer));
        rightPointerPaint.setAntiAlias(true);
        rightPointerPaint.setColor(getResources().getColor(R.color.rightPointer));
        rightPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointerCirclePaint.setAntiAlias(true);
        pointerCirclePaint.setColor(Color.GRAY);
        pointerCirclePaint.setStyle(Paint.Style.FILL);
        pointerCirclePaint.setDither(true);
    }

    /**
     * 设置当前温度
     *
     * @param currentTemp
     */
    public void setCurrentTemp(float currentTemp) {
        if (currentTemp < 0) {
            currentTemp = 0;
        } else if (currentTemp > 40) {
            currentTemp = 40;
        } else {
            this.currentTemp = currentTemp;
            postInvalidate();
        }
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public float getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(float progressWidth) {
        this.progressWidth = progressWidth;
    }

    public String getTempText() {
        return tempText;
    }

    public void setTempText(String tempText) {
        this.tempText = tempText;
    }

    public float getTempTextSize() {
        return tempTextSize;
    }

    public void setTempTextSize(float tempTextSize) {
        this.tempTextSize = tempTextSize;
    }
}

