package com.alfred.study.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alfred.study.R;
import com.alfred.study.ui.base.BaseActivity;

/**
 * 属性动画
 * <p/>
 * alpha 渐变 scaleX scaleY 伸缩 translationX translationY 移动 rotate 旋转
 * Created by Alfred on 16/7/19.
 */
public class AnimationActivity extends BaseActivity {

    //    @Bind(R.id.textView)
    TextView textView;

    private static final String TAG = "AnimationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
//        ButterKnife.bind(this);

    }

    private void studyValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.i("TAG", "current value is  " + (float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
        //设置动画播放次数
        valueAnimator.setRepeatCount(3);
        //设置动画的循环模式 RESTART 重新播放 REVERSE 倒序播放
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        //设置动画的延时播放时间
        valueAnimator.setStartDelay(1000);

        // TypeEvaluator(为什么没有效果)
//        AnimationPoint startPoint = new AnimationPoint(0, 0);
//        AnimationPoint endPoint = new AnimationPoint(1000, 1000);
//        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
//        animator.setDuration(5000);
//        animator.setTarget(textView);
//        animator.start();
    }

    private void studyObjectAnimator() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
        objectAnimator.setDuration(3000);
        objectAnimator.setStartDelay(3000);
        objectAnimator.setRepeatCount(3);
        objectAnimator.start();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        objectAnimator1.setDuration(3000);
        objectAnimator1.start();

        float curTranslationX = textView.getTranslationX();
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textView, "translation", curTranslationX, -550f, curTranslationX);
        objectAnimator2.setDuration(3000);
        objectAnimator2.start();

    }

    private void studyAnimatorSet() {

        //after(Animator anim) 将现有的动画插入到传入动画之后执行
        //after(long delay) 将现有动画延迟制定毫秒后执行
        //before(Animator anim) 将现有的动画插入到传入的动画之前执行
        //with(Animator anim) 将现有动画和传入的动画同时执行

        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textView, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        //先执行moveIn,然后同时执行rotate和alpha
        animatorSet.play(rotate).with(alpha).after(moveIn);
        animatorSet.setDuration(5000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i(TAG, "onAnimationEnd");

            }

            @Override
            public void onAnimationCancel(Animator animator) {
                Log.i(TAG, "onAnimationCancel");

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.i(TAG, "onAnimationRepeat");

            }
        });
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.start();
    }

    private void studyXMLAnimator() {
        //<animator> 对应代码中的ValueAnimator
        //<ObjectAnimtor> 对应代码中的ObjectAnimtor
        //<set> 对应代码中的AnimtorSet

        //加载xml布局动画
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_move_rotation);
        animator.setTarget(textView);
        animator.start();
        studyInterpolator();
    }

    /**
     * Interpolator 差值器
     * TimeInterpolator 是属性动画新增的接口,这个接口是用于兼容之前的Interpolator的,这使得所有过去的Interpolator实现类都可以直接拿过来用放到属性动画中使用
     * </p>
     * {# TimeInterpolator}
     */
    private void studyInterpolator() {

    }

    /**
     * 整个ViewPropertyAnimator的功能都是建立在View类新增的animate()方法之上的，这个方法会创建并返回一个ViewPropertyAnimator的实例，之后的调用的所有方法，设置的所有属性都是通过这个实例完成的。
     * 大家注意到，在使用ViewPropertyAnimator时，我们自始至终没有调用过start()方法，这是因为新的接口中使用了隐式启动动画的功能，只要我们将动画定义完成之后，动画就会自动启动。并且这个机制对于组合动画也同样有效，只要我们不断地连缀新的方法，那么动画就不会立刻执行，等到所有在ViewPropertyAnimator上设置的方法都执行完毕后，动画就会自动启动。当然如果不想使用这一默认机制的话，我们也可以显式地调用start()方法来启动动画。
     * ViewPropertyAnimator的所有接口都是使用连缀的语法来设计的，每个方法的返回值都是它自身的实例，因此调用完一个方法之后可以直接连缀调用它的另一个方法，这样把所有的功能都串接起来，我们甚至可以仅通过一行代码就完成任意复杂度的动画功能。
     */
    private void studyViewPropertyAnimator() {
        textView.animate().alpha(0f);
        textView.animate().x(500).y(500);
        textView.animate().x(500).y(500).setDuration(5000);

    }


}
