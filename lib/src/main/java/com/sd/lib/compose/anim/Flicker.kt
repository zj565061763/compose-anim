package com.sd.lib.compose.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/**
 * 闪烁
 */
fun Modifier.fFlicker(
   /** 是否闪烁 */
   flicker: Boolean,
   /** 重复次数 */
   repeatCount: Int = 2,
   /** 初始透明度 */
   initialAlpha: Float = 1f,
   /** 重复回调 */
   onRepeat: suspend Animatable<Float, AnimationVector1D>.(Int) -> Unit = {
      animateTo(0f)
      animateTo(1f)
   },
   /** 结束回调（协程可能已经被取消） */
   onFinish: suspend Animatable<Float, AnimationVector1D>.() -> Unit = {},
): Modifier = if (flicker) {
   composed {
      val animatable = remember {
         // initialAlpha不作为remember的key，只允许初始化的时候设置一次
         Animatable(initialAlpha)
      }
      animatable.fRepeat(
         count = repeatCount,
         onRepeat = onRepeat,
         onFinish = onFinish,
      )
      graphicsLayer {
         this.alpha = animatable.value
      }
   }
} else {
   this
}