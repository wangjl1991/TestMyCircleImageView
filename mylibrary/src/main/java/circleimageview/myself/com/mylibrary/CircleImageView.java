package circleimageview.myself.com.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CircleImageView extends ImageView {

    //外圆的颜色及默认值
    private int outCircleColor = Color.WHITE;
    //外圆的宽度及默认值
    private int outCircleWidth = 5;
    //背景画笔
    private Paint mPaintBorder;
    //定义控件的宽高
    private int viewWidth;
    private int viewHeight;
    //定义绘制的图片
    private Bitmap bitmap;

    public CircleImageView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    //初始化属性
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            //2和3构造方法
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.circleImageView);
            int length = typedArray.length();
            for (int i = 0; i < length; i++) {
                //声明的在attr的属性
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.circleImageView_outCircleColor) {
                    this.outCircleColor = typedArray.getColor(attr, Color.WHITE);
                } else if (attr == R.styleable.circleImageView_outCircleWidth) {
                    this.outCircleWidth = (int) typedArray.getDimension(attr, 5);
                }
               /* switch (attr) {
                    case R.styleable.circleImageView_outCircleColor:
                        this.outCircleColor = typedArray.getColor(attr, Color.WHITE);
                        break;
                    case R.styleable.circleImageView_outCircleWidth:
                        this.outCircleWidth = (int) typedArray.getDimension(attr, 5);
                        break;
                }*/
            }
        }
        //初始化画笔
        mPaintBorder = new Paint();
        mPaintBorder.setColor(outCircleColor);
        //抗锯齿
        mPaintBorder.setAntiAlias(true);
    }

    //测量宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量宽度
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //减去外圆的边框占据的宽度
        viewWidth = width - outCircleWidth * 2;
        viewHeight = height - outCircleWidth * 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制之前先加载进图片
        loadImg();
        if (bitmap != null) {
            //计算图片的最小尺寸和圆的半径
            int minsize = Math.min(viewHeight, viewWidth);
            int circleradius = minsize / 2;
            bitmap = Bitmap.createScaledBitmap(bitmap, minsize, minsize, false);
            //画好了外圆
            canvas.drawCircle((outCircleWidth + circleradius), (outCircleWidth + circleradius), (outCircleWidth + circleradius), mPaintBorder);
            //画图片,一个圆形的图片，偏移量
            canvas.drawBitmap(createCircleBitmap(bitmap, minsize), outCircleWidth, outCircleWidth, null);
        }
    }

    //将图片加工成圆形的Bitmap
    private Bitmap createCircleBitmap(Bitmap bitmap, int minsize) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap targetBitmap = Bitmap.createBitmap(minsize, minsize, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        canvas.drawCircle(minsize / 2, minsize / 2, minsize / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return targetBitmap;
    }

    //测量宽度的方法
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            //mode == MeasureSpec.AT_MOST
            //mode == MeasureSpec.UNSPECIFIED
            result = viewWidth;
        }
        return result;
    }

    //测量高度的方法
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            //mode == MeasureSpec.AT_MOST
            //mode == MeasureSpec.UNSPECIFIED
            result = viewHeight;
        }
        return result;
    }

    //加载图片的方法
    private void loadImg() {
        //加载之前需要一个Bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }
    }

    //给Java代码提供的设置颜色的方法
    public void setOutCircleColor(int color) {
        if (null != mPaintBorder) {
            mPaintBorder.setColor(color);
        }
        this.invalidate();
    }

    //设置外圆的宽度
    public void setOutCircleWidth(int width) {
        this.outCircleWidth = width;
        this.invalidate();
    }
}
