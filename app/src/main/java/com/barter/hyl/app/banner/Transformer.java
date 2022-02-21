package com.barter.hyl.app.banner;

import androidx.viewpager.widget.ViewPager.PageTransformer;

import com.barter.hyl.app.banner.transformer.AccordionTransformer;
import com.barter.hyl.app.banner.transformer.BackgroundToForegroundTransformer;
import com.barter.hyl.app.banner.transformer.CubeInTransformer;
import com.barter.hyl.app.banner.transformer.CubeOutTransformer;
import com.barter.hyl.app.banner.transformer.DefaultTransformer;
import com.barter.hyl.app.banner.transformer.DepthPageTransformer;
import com.barter.hyl.app.banner.transformer.FlipHorizontalTransformer;
import com.barter.hyl.app.banner.transformer.FlipVerticalTransformer;
import com.barter.hyl.app.banner.transformer.ForegroundToBackgroundTransformer;
import com.barter.hyl.app.banner.transformer.RotateDownTransformer;
import com.barter.hyl.app.banner.transformer.RotateUpTransformer;
import com.barter.hyl.app.banner.transformer.ScaleInOutTransformer;
import com.barter.hyl.app.banner.transformer.StackTransformer;
import com.barter.hyl.app.banner.transformer.TabletTransformer;
import com.barter.hyl.app.banner.transformer.ZoomInTransformer;
import com.barter.hyl.app.banner.transformer.ZoomOutSlideTransformer;
import com.barter.hyl.app.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
