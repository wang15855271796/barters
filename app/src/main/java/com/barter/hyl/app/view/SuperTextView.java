package com.barter.hyl.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.barter.hyl.app.R;

/**
 * Created by Administrator on 2018/3/30.
 */

public class SuperTextView extends AppCompatTextView {
    // Some Property Default Value
    private static final float DEFAULT_CORNER = 0f;
    private static final int DEFAULT_SOLID = Color.TRANSPARENT;
    private static final float DEFAULT_STROKE_WIDTH = 0f;
    private static final int DEFAULT_STROKE_COLOR = Color.BLACK;
    private static final int DEFAULT_STATE_DRAWABLE_MODE = 4;
    private static final int DEFAULT_TEXT_STROKE_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_FILL_COLOR = Color.BLACK;
    private static final float DEFAULT_TEXT_STROKE_WIDTH = 0f;

    private float corner;
    private boolean leftTopCornerEnable;
    private boolean rightTopCornerEnable;
    private boolean leftBottomCornerEnable;
    private boolean rightBottomCornerEnable;
    private int solid;
    private float strokeWidth;
    private int strokeColor;
    private int stateDrawableMode;
    private boolean isShowState;

    private Paint paint;
    private int width;
    private int height;
    private Drawable drawable;
    private float density;
    private boolean autoAdjust;
    private Adjuster adjuster;
    private boolean textStroke;
    private int textStrokeColor;
    private int textFillColor;
    private float textStrokeWidth;
    private boolean runnable = false;
    private boolean needRun = false;
    private Thread animThread;
    private Path strokeWidthPath;
    private Path solidPath;
    private RectF strokeLineRectF;
    private RectF solidRectF;
    private float leftTopCorner[] = new float[2];
    private float rightTopCorner[] = new float[2];
    private float leftBottomCorner[] = new float[2];
    private float rightBottomCorner[] = new float[2];
    private float corners[] = new float[8];
    private float[] drawableBounds = new float[4];
    private float drawableWidth;
    private float drawableHeight;
    private float drawablePaddingLeft;
    private float drawablePaddingTop;
    private boolean cacheRunnableState;
    private boolean cacheNeedRunState;
    private int frameRate = 60;
    private Runnable invalidate;
    private int shaderStartColor;
    private int shaderEndColor;
    private int shaderMode;
    private LinearGradient shader;
    private boolean shaderEnable;


    public SuperTextView(Context context) {
        super(context);
        init(null);
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        density = getContext().getResources().getDisplayMetrics().density;
        initAttrs(attrs);
        paint = new Paint();
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    getContext().obtainStyledAttributes(attrs, R.styleable.SuperTextView);
            corner = typedArray.getDimension(R.styleable.SuperTextView_corner, DEFAULT_CORNER);
            leftTopCornerEnable =
                    typedArray.getBoolean(R.styleable.SuperTextView_left_top_corner, false);
            rightTopCornerEnable =
                    typedArray.getBoolean(R.styleable.SuperTextView_right_top_corner, false);
            leftBottomCornerEnable =
                    typedArray.getBoolean(R.styleable.SuperTextView_left_bottom_corner, false);
            rightBottomCornerEnable =
                    typedArray.getBoolean(R.styleable.SuperTextView_right_bottom_corner, false);
            solid = typedArray.getColor(R.styleable.SuperTextView_solid, DEFAULT_SOLID);
            strokeWidth = typedArray.getDimension(R.styleable.SuperTextView_stroke_width,
                    DEFAULT_STROKE_WIDTH);
            strokeColor =
                    typedArray.getColor(R.styleable.SuperTextView_stroke_color, DEFAULT_STROKE_COLOR);
            drawable = typedArray.getDrawable(R.styleable.SuperTextView_state_drawable);
            drawableWidth =
                    typedArray.getDimension(R.styleable.SuperTextView_state_drawable_width, 0);
            drawableHeight =
                    typedArray.getDimension(R.styleable.SuperTextView_state_drawable_height, 0);
            drawablePaddingLeft =
                    typedArray.getDimension(R.styleable.SuperTextView_state_drawable_padding_left, 0);
            drawablePaddingTop =
                    typedArray.getDimension(R.styleable.SuperTextView_state_drawable_padding_top, 0);
            isShowState = typedArray.getBoolean(R.styleable.SuperTextView_isShowState, false);
            stateDrawableMode = typedArray.getInteger(R.styleable.SuperTextView_state_drawable_mode,
                    DEFAULT_STATE_DRAWABLE_MODE);
            textStroke = typedArray.getBoolean(R.styleable.SuperTextView_text_stroke, false);
            textStrokeColor = typedArray.getColor(R.styleable.SuperTextView_text_stroke_color,
                    DEFAULT_TEXT_STROKE_COLOR);
            textFillColor = typedArray.getColor(R.styleable.SuperTextView_text_fill_color,
                    DEFAULT_TEXT_FILL_COLOR);
            textStrokeWidth = typedArray.getDimension(R.styleable.SuperTextView_text_stroke_width,
                    DEFAULT_TEXT_STROKE_WIDTH);
            autoAdjust = typedArray.getBoolean(R.styleable.SuperTextView_autoAdjust, false);
            shaderStartColor =
                    typedArray.getColor(R.styleable.SuperTextView_shaderStartColor, 0);
            shaderEndColor =
                    typedArray.getColor(R.styleable.SuperTextView_shaderEndColor, 0);
            shaderMode = typedArray.getInteger(R.styleable.SuperTextView_shaderMode, 0);
            shaderEnable = typedArray.getBoolean(R.styleable.SuperTextView_shaderEnable, false);
            typedArray.recycle();
        }
    }

    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        drawStrokeLine(canvas);
        drawSolid(canvas);
        isNeedToAdjust(canvas, Adjuster.Opportunity.BEFORE_DRAWABLE);
        drawStateDrawable(canvas);
        isNeedToAdjust(canvas, Adjuster.Opportunity.BEFORE_TEXT);
        if (textStroke) {
            getPaint().setStyle(Paint.Style.STROKE);
            setTextColor(textStrokeColor);
            getPaint().setFakeBoldText(true);
            getPaint().setStrokeWidth(textStrokeWidth);
            super.onDraw(canvas);
            getPaint().setStyle(Paint.Style.FILL);
            getPaint().setFakeBoldText(false);
            setTextColor(textFillColor);
        }
        super.onDraw(canvas);
        isNeedToAdjust(canvas, Adjuster.Opportunity.AT_LAST);
    }

    private void drawStrokeLine(Canvas canvas) {
        if (strokeWidth > 0) {
            if (strokeWidthPath == null) {
                strokeWidthPath = new Path();
            } else {
                strokeWidthPath.reset();
            }
            if (strokeLineRectF == null) {
                strokeLineRectF = new RectF();
            } else {
                strokeLineRectF.setEmpty();
            }
            strokeLineRectF.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2,
                    height - strokeWidth / 2);
            getCorners(corner);
            strokeWidthPath.addRoundRect(strokeLineRectF, corners, Path.Direction.CW);
            initPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            canvas.drawPath(strokeWidthPath, paint);
        }
    }

    private void drawSolid(Canvas canvas) {
        if (solidPath == null) {
            solidPath = new Path();
        } else {
            solidPath.reset();
        }
        if (solidRectF == null) {
            solidRectF = new RectF();
        } else {
            solidRectF.setEmpty();
        }
        solidRectF.set(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth);
        getCorners(corner - strokeWidth / 2);
        solidPath.addRoundRect(solidRectF, corners, Path.Direction.CW);

        initPaint();
        paint.setStyle(Paint.Style.FILL);
        if (shaderEnable) {
            useShader(paint);
        } else {
            paint.setColor(solid);
        }
        canvas.drawPath(solidPath, paint);
    }

    private float[] getCorners(float corner) {
        leftTopCorner[0] = 0;
        leftTopCorner[1] = 0;
        rightTopCorner[0] = 0;
        rightTopCorner[1] = 0;
        leftBottomCorner[0] = 0;
        leftBottomCorner[1] = 0;
        rightBottomCorner[0] = 0;
        rightBottomCorner[1] = 0;
        if (this.leftTopCornerEnable || this.rightTopCornerEnable || this.leftBottomCornerEnable
                || this.rightBottomCornerEnable) {
            if (this.leftTopCornerEnable) {
                leftTopCorner[0] = corner;
                leftTopCorner[1] = corner;
            }
            if (this.rightTopCornerEnable) {
                rightTopCorner[0] = corner;
                rightTopCorner[1] = corner;
            }
            if (this.leftBottomCornerEnable) {
                leftBottomCorner[0] = corner;
                leftBottomCorner[1] = corner;
            }
            if (this.rightBottomCornerEnable) {
                rightBottomCorner[0] = corner;
                rightBottomCorner[1] = corner;
            }
        } else {
            leftTopCorner[0] = corner;
            leftTopCorner[1] = corner;
            rightTopCorner[0] = corner;
            rightTopCorner[1] = corner;
            leftBottomCorner[0] = corner;
            leftBottomCorner[1] = corner;
            rightBottomCorner[0] = corner;
            rightBottomCorner[1] = corner;
        }
        corners[0] = leftTopCorner[0];
        corners[1] = leftTopCorner[1];
        corners[2] = rightTopCorner[0];
        corners[3] = rightTopCorner[1];
        corners[4] = rightBottomCorner[0];
        corners[5] = rightBottomCorner[1];
        corners[6] = leftBottomCorner[0];
        corners[7] = leftBottomCorner[1];
        return corners;
    }

    private void useShader(Paint paint) {
        if (shader == null) {
            createShader();
        }
        paint.setShader(shader);
    }

    private boolean createShader() {
        if (shaderStartColor != 0 && shaderEndColor != 0) {
            float x0 = 0;
            float x1 = 0;
            float y0 = 0;
            float y1 = 0;
            int startColor = shaderStartColor;
            int endColor = shaderEndColor;
            switch (ShaderMode.valueOf(shaderMode)) {
                case TOP_TO_BOTTOM:
                    y1 = height;
                    break;
                case BOTTOM_TO_TOP:
                    y1 = height;
                    startColor = shaderEndColor;
                    endColor = shaderStartColor;
                    break;
                case LEFT_TO_RIGHT:
                    x1 = width;
                    break;
                case RIGHT_TO_LEFT:
                    x1 = width;
                    startColor = shaderEndColor;
                    endColor = shaderStartColor;
                    break;
            }
            shader = new LinearGradient(x0, y0, x1, y1, startColor, endColor, Shader.TileMode.CLAMP);
            return true;
        } else {
            return false;
        }
    }

    private void drawStateDrawable(Canvas canvas) {
        if (drawable != null && isShowState) {
            getDrawableBounds();
            drawable.setBounds((int) drawableBounds[0], (int) drawableBounds[1], (int) drawableBounds[2],
                    (int) drawableBounds[3]);
            drawable.draw(canvas);
        }
    }

    private float[] getDrawableBounds() {
        for (int i = 0; i < drawableBounds.length; i++) {
            drawableBounds[i] = 0;
        }
        drawableWidth = (drawableWidth == 0 ? width / 2f : drawableWidth);
        drawableHeight = (drawableHeight == 0 ? height / 2f : drawableHeight);
        switch (DrawableMode.valueOf(stateDrawableMode)) {
            case LEFT: // left
                drawableBounds[0] = 0 + drawablePaddingLeft;
                drawableBounds[1] = height / 2f - drawableHeight / 2f + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case TOP: // top
                drawableBounds[0] = width / 2f - drawableWidth / 2f + drawablePaddingLeft;
                drawableBounds[1] = 0 + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case RIGHT: // right
                drawableBounds[0] = width - drawableWidth + drawablePaddingLeft;
                drawableBounds[1] = height / 2 - drawableHeight / 2 + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case BOTTOM: // bottom
                drawableBounds[0] = width / 2f - drawableWidth / 2f + drawablePaddingLeft;
                drawableBounds[1] = height - drawableHeight + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case CENTER: // center
                drawableBounds[0] = width / 2f - drawableWidth / 2f + drawablePaddingLeft;
                drawableBounds[1] = height / 2 - drawableHeight / 2 + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case FILL: // fill
                drawableBounds[0] = 0;
                drawableBounds[1] = 0;
                drawableBounds[2] = width;
                drawableBounds[3] = height;
                break;
            case LEFT_TOP: // leftTop
                drawableBounds[0] = 0 + drawablePaddingLeft;
                drawableBounds[1] = 0 + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case RIGHT_TOP: // rightTop
                drawableBounds[0] = width - drawableWidth + drawablePaddingLeft;
                drawableBounds[1] = 0 + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case LEFT_BOTTOM: // leftBottom
                drawableBounds[0] = 0 + drawablePaddingLeft;
                drawableBounds[1] = height - drawableHeight + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
            case RIGHT_BOTTOM: // rightBottom
                drawableBounds[0] = width - drawableWidth + drawablePaddingLeft;
                drawableBounds[1] = height - drawableHeight + drawablePaddingTop;
                drawableBounds[2] = drawableBounds[0] + drawableWidth;
                drawableBounds[3] = drawableBounds[1] + drawableHeight;
                break;
        }

        return drawableBounds;
    }

    private void isNeedToAdjust(Canvas canvas, Adjuster.Opportunity currentOpportunity) {
        if (autoAdjust) {
            if (adjuster == null) {
                setAdjuster(new DefaultAdjuster());
            } else {
                if (currentOpportunity == adjuster.getOpportunity()) {
                    adjuster.adjust(this, canvas);
                }
            }
        }
    }

    /**
     * @return ??????Corner???????????????0???
     */
    public float getCorner() {
        return corner;
    }


    /**
     * @param corner ???????????????????????????0???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setCorner(float corner) {
        this.corner = corner;
        postInvalidate();

        return this;
    }

    /**
     * @return ???????????????????????????
     */
    public int getSolid() {
        return solid;
    }

    /**
     * @param solid ??????????????????, ?????????{@link Color#TRANSPARENT}???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setSolid(int solid) {
        this.solid = solid;
        postInvalidate();

        return this;
    }

    /**
     * @return ??????????????????????????????
     */
    public float getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * @param strokeWidth ???????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        postInvalidate();

        return this;
    }

    /**
     * @return ??????????????????????????????{@link Color#BLACK}???
     */
    public int getStrokeColor() {
        return strokeColor;
    }

    /**
     * @param strokeColor ????????????????????????{@link Color#BLACK}???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        postInvalidate();

        return this;
    }

    /**
     * @param adjuster ??????Adjuster???{@link Adjuster}???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setAdjuster(Adjuster adjuster) {
        this.adjuster = adjuster;
        postInvalidate();

        return this;
    }

    /**
     * @return ??????Adjuster????????????????????????
     */
    public Adjuster getAdjuster() {
        return adjuster;
    }

    /**
     * @return true ??????????????????????????????????????????????????????
     */
    public boolean isTextStroke() {
        return textStroke;
    }

    /**
     * @param textStroke true????????????????????????????????????false???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setTextStroke(boolean textStroke) {
        this.textStroke = textStroke;
        postInvalidate();

        return this;
    }

    /**
     * @return ????????????????????????
     */
    public int getTextStrokeColor() {
        return textStrokeColor;
    }

    /**
     * @param textStrokeColor ???????????????????????????????????????{@link Color#BLACK}???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setTextStrokeColor(int textStrokeColor) {
        this.textStrokeColor = textStrokeColor;
        postInvalidate();

        return this;
    }

    /**
     * @return ?????????????????????
     */
    public int getTextFillColor() {
        return textFillColor;
    }

    /**
     * @param textFillColor ????????????????????????????????????{@link Color#BLACK}???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setTextFillColor(int textFillColor) {
        this.textFillColor = textFillColor;
        postInvalidate();

        return this;
    }

    /**
     * @return ?????????????????????
     */
    public float getTextStrokeWidth() {
        return textStrokeWidth;
    }

    /**
     * @param textStrokeWidth ???????????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setTextStrokeWidth(float textStrokeWidth) {
        this.textStrokeWidth = textStrokeWidth;
        postInvalidate();

        return this;
    }

    /**
     * @return true???????????????Adjuster?????????
     */
    public boolean isAutoAdjust() {
        return autoAdjust;
    }

    /**
     * @param autoAdjust true??????Adjuster???????????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setAutoAdjust(boolean autoAdjust) {
        this.autoAdjust = autoAdjust;
        postInvalidate();

        return this;
    }

    /**
     * @return true???????????????????????????
     */
    public boolean isLeftTopCornerEnable() {
        return leftTopCornerEnable;
    }

    /**
     * @param leftTopCornerEnable true?????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setLeftTopCornerEnable(boolean leftTopCornerEnable) {
        this.leftTopCornerEnable = leftTopCornerEnable;
        postInvalidate();

        return this;
    }

    /**
     * @return true???????????????????????????
     */
    public boolean isRightTopCornerEnable() {
        return rightTopCornerEnable;
    }

    /**
     * @param rightTopCornerEnable true?????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setRightTopCornerEnable(boolean rightTopCornerEnable) {
        this.rightTopCornerEnable = rightTopCornerEnable;
        postInvalidate();

        return this;
    }

    /**
     * @return true???????????????????????????
     */
    public boolean isLeftBottomCornerEnable() {
        return leftBottomCornerEnable;
    }

    /**
     * @param leftBottomCornerEnable true?????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setLeftBottomCornerEnable(boolean leftBottomCornerEnable) {
        this.leftBottomCornerEnable = leftBottomCornerEnable;
        postInvalidate();

        return this;
    }

    /**
     * @return true???????????????????????????
     */
    public boolean isRightBottomCornerEnable() {
        return rightBottomCornerEnable;
    }

    /**
     * @param rightBottomCornerEnable true?????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setRightBottomCornerEnable(boolean rightBottomCornerEnable) {
        this.rightBottomCornerEnable = rightBottomCornerEnable;
        postInvalidate();

        return this;
    }

    /**
     * @return ????????????
     */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * @param drawable ??????????????????????????????isShowState(true)???????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setDrawable(Drawable drawable) {
        this.drawable = drawable;
        postInvalidate();

        return this;
    }

    /**
     * @param drawableRes ??????drawable????????????????????????????????????isShowState(true)???????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setDrawable(int drawableRes) {
        this.drawable = getResources().getDrawable(drawableRes);
        postInvalidate();

        return this;
    }

    /**
     * @return ??????????????????????????????
     */
    public boolean isShowState() {
        return isShowState;
    }

    /**
     * @param showState true????????????????????????????????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setShowState(boolean showState) {
        isShowState = showState;
        postInvalidate();

        return this;
    }

    /**
     * @return ????????????????????????{@link DrawableMode}
     */
    public int getStateDrawableMode() {
        return stateDrawableMode;
    }

    /**
     * @param stateDrawableMode ???????????????????????????????????????{@link DrawableMode#CENTER}???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setStateDrawableMode(int stateDrawableMode) {
        this.stateDrawableMode = stateDrawableMode;
        postInvalidate();

        return this;
    }

    /**
     * @return ?????????????????????
     */
    public float getDrawableWidth() {
        return drawableWidth;
    }

    /**
     * @param drawableWidth ??????????????????????????????????????????1???2???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setDrawableWidth(float drawableWidth) {
        this.drawableWidth = drawableWidth;
        postInvalidate();

        return this;
    }

    /**
     * @return ?????????????????????
     */
    public float getDrawableHeight() {
        return drawableHeight;
    }

    /**
     * @param drawableHeight ??????????????????????????????????????????1???2???????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setDrawableHeight(float drawableHeight) {
        this.drawableHeight = drawableHeight;
        postInvalidate();

        return this;
    }

    /**
     * @return ???????????????????????????????????????????????????
     */
    public float getDrawablePaddingLeft() {
        return drawablePaddingLeft;
    }

    /**
     * @param drawablePaddingLeft ?????????????????????????????????????????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setDrawablePaddingLeft(float drawablePaddingLeft) {
        this.drawablePaddingLeft = drawablePaddingLeft;
        postInvalidate();

        return this;
    }

    /**
     * @return ???????????????????????????????????????????????????
     */
    public float getDrawablePaddingTop() {
        return drawablePaddingTop;
    }

    /**
     * @param drawablePaddingTop ?????????????????????????????????????????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setDrawablePaddingTop(float drawablePaddingTop) {
        this.drawablePaddingTop = drawablePaddingTop;
        postInvalidate();
        return this;
    }

    /**
     * @return ??????????????????
     */
    public int getShaderStartColor() {
        return shaderStartColor;
    }

    /**
     * @param shaderStartColor ????????????????????????????????????{@link SuperTextView#setShaderEnable(boolean)}??????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setShaderStartColor(int shaderStartColor) {
        this.shaderStartColor = shaderStartColor;
        shader = null;
        postInvalidate();
        return this;
    }

    /**
     * @return ??????????????????
     */
    public int getShaderEndColor() {
        return shaderEndColor;
    }

    /**
     * @param shaderEndColor ????????????????????????????????????{@link SuperTextView#setShaderEnable(boolean)}??????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setShaderEndColor(int shaderEndColor) {
        this.shaderEndColor = shaderEndColor;
        shader = null;
        postInvalidate();
        return this;
    }

    /**
     * @return ???????????????{@link ShaderMode}???
     */
    public int getShaderMode() {
        return shaderMode;
    }

    /**
     * @param shaderMode ?????????????????????{@link ShaderMode}???
     * @return SuperTextView
     */
    public SuperTextView setShaderMode(int shaderMode) {
        this.shaderMode = shaderMode;
        shader = null;
        postInvalidate();
        return this;
    }

    /**
     * @return ??????????????????????????????
     */
    public boolean isShaderEnable() {
        return shaderEnable;
    }

    /**
     * @param shaderEnable true???????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setShaderEnable(boolean shaderEnable) {
        this.shaderEnable = shaderEnable;
        postInvalidate();
        return this;
    }

    /**
     * @return ??????
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * @param frameRate ?????????????????????????????????????????????????????????????????????
     * @return SuperTextView
     */
    public SuperTextView setFrameRate(int frameRate) {
        if (frameRate > 0) {
            this.frameRate = frameRate;
        } else {
            this.frameRate = 60;
        }
        return this;
    }


    /**
     * ???????????????????????????{@link SuperTextView#setAutoAdjust(boolean)}???true???????????????
     */
    public void startAnim() {
        checkWhetherNeedInitInvalidate();
        needRun = true;
        runnable = false;
        if (animThread == null) {
            needRun = true;
            runnable = true;
            animThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (runnable) {
                        synchronized (invalidate){
                            post(invalidate);
                        }
                        try {
                            Thread.sleep(1000 / frameRate);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            runnable = false;
                        }
                        // Log.e("SuperTextView", " -> startAnim: " + Thread.currentThread().getId() + "-> "
                        // + hashCode() + ": It's running!");
                    }
                    animThread = null;
                    if (needRun) {
                        startAnim();
                    }
                }
            });
            animThread.start();
        }
    }

    private void checkWhetherNeedInitInvalidate() {
        if (invalidate == null) {
            invalidate = new Runnable() {
                @Override
                public void run() {
                    postInvalidate();
                }
            };
        }
    }

    /**
     * ???????????????????????????????????????????????????????????????
     */
    public void stopAnim() {
        runnable = false;
        needRun = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (adjuster != null) {
            if (adjuster.onTouch(this, event) && isAutoAdjust()) {
                super.onTouchEvent(event);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != VISIBLE) {
            cacheRunnableState = runnable;
            cacheNeedRunState = needRun;
            stopAnim();
        } else if (cacheRunnableState && cacheNeedRunState) {
            startAnim();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnim();
        super.onDetachedFromWindow();
    }

    /**
     * Adjuster??????????????????SuperTextView???????????????????????????????????????
     * ?????????????????????????????????????????????????????????{@link DefaultAdjuster}???????????????????????????????????????
     * ?????????????????????????????????????????????????????????
     * ???????????????Adjuster??????????????????????????????{@link Adjuster#setOpportunity(Opportunity)}???
     * {@link Opportunity}????????????{@link Opportunity#BEFORE_TEXT}???
     */
    public static abstract class Adjuster {
        private Opportunity opportunity = Opportunity.BEFORE_TEXT;

        /**
         * @param v SuperTextView
         * @param canvas ???????????????Canvas????????????Canvas???????????????????????????????????????????????????????????????
         */
        protected abstract void adjust(SuperTextView v, Canvas canvas);

        /**
         * @param v SuperTextView
         * @param event ????????????????????????????????????
         * @return ????????????false?????????????????????????????????????????????????????????true???????????????????????????{@link MotionEvent#ACTION_DOWN}?????????
         */
        public boolean onTouch(SuperTextView v, MotionEvent event) {
            return false;
        };

        /**
         * @return Adjuster??????????????????
         */
        public Opportunity getOpportunity() {
            return opportunity;
        }

        /**
         * @param opportunity ??????Adjuster??????????????????{@link Opportunity}
         */
        public void setOpportunity(Opportunity opportunity) {
            this.opportunity = opportunity;
        }

        /**
         * Adjuster????????????????????????????????????????????????
         * ???????????????{@link Adjuster#setOpportunity(Opportunity)}?????????Adjuster??????????????????
         * ???SuperTextView??????????????????????????????????????????????????????Drawable???????????????3????????????
         * ??????Opportunity???????????????Adjuster?????????????????????????????????
         */
        public static enum Opportunity {
            /**
             * ????????????Drawable?????????
             */
            BEFORE_DRAWABLE,
            /**
             * Drawable?????????????????????
             */
            BEFORE_TEXT,
            /**
             * ??????
             */
            AT_LAST
        }
    }

    /**
     * ???????????????????????????SuperTextView?????????10????????????????????????????????????????????????????????????
     * ?????????????????????{@link DrawableMode#CENTER}???
     */
    public static enum DrawableMode {
        /**
         * ??????
         */
        LEFT(0),
        /**
         * ??????
         */
        TOP(1),
        /**
         * ??????
         */
        RIGHT(2),
        /**
         * ??????
         */
        BOTTOM(3),
        /**
         * ??????
         */
        CENTER(4),
        /**
         * ??????????????????
         */
        FILL(5),
        /**
         * ??????
         */
        LEFT_TOP(6),
        /**
         * ??????
         */
        RIGHT_TOP(7),
        /**
         * ??????
         */
        LEFT_BOTTOM(8),
        /**
         * ??????
         */
        RIGHT_BOTTOM(9);

        public int code;
        DrawableMode(int code) {
            this.code = code;
        }

        public static DrawableMode valueOf(int code) {
            for (DrawableMode mode : DrawableMode.values()) {
                if (mode.code == code) {
                    return mode;
                }
            }
            return CENTER;
        }
    }

    /**
     * ???????????????
     */
    public static enum ShaderMode {
        /**
         * ????????????
         */
        TOP_TO_BOTTOM(0),
        /**
         * ????????????
         */
        BOTTOM_TO_TOP(1),
        /**
         * ????????????
         */
        LEFT_TO_RIGHT(2),
        /**
         * ????????????
         */
        RIGHT_TO_LEFT(3);

        public int code;

        ShaderMode(int code) {
            this.code = code;
        }

        public static ShaderMode valueOf(int code) {
            for (ShaderMode mode : ShaderMode.values()) {
                if (mode.code == code) {
                    return mode;
                }
            }
            return TOP_TO_BOTTOM;
        }
    }

    public static class DefaultAdjuster extends Adjuster {

        @Override
        public void adjust(SuperTextView v, Canvas canvas) {
            int length = v.length();
            float scale = v.getWidth() / (116.28f * v.getResources().getDisplayMetrics().density);
            float[] textSizes = {
                    37.21f, 37.21f, 24.81f, 27.9f, 24.81f,
                    22.36f, 18.6f,
                    18.6f
            };
            switch (length) {
                case 1:
                    v.setTextSize(textSizes[0] * scale);
                    break;
                case 2:
                    v.setTextSize(textSizes[1] * scale);
                    break;
                case 3:
                    v.setTextSize(textSizes[2] * scale);
                    break;
                case 4:
                    v.setTextSize(textSizes[3] * scale);
                    break;
                case 5:
                case 6:
                    v.setTextSize(textSizes[4] * scale);
                    break;
                case 7:
                case 8:
                case 9:
                    v.setTextSize(textSizes[5] * scale);
                    break;
                case 10:
                case 11:
                case 12:
                    v.setTextSize(textSizes[6] * scale);
                    break;
                case 13:
                case 14:
                case 15:
                case 16:
                    v.setTextSize(textSizes[7] * scale);
                    break;
            }
        }

    }
}
