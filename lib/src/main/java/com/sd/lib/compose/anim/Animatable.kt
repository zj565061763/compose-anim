package com.sd.lib.compose.anim

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

/**
 * 重复动画
 */
@SuppressLint("ComposableNaming")
@Composable
fun <T, V : AnimationVector> Animatable<T, V>.fRepeat(
   /** 重复次数 */
   count: Int,
   /** 结束回调（协程可能已经被取消） */
   onFinish: suspend Animatable<T, V>.() -> Unit = {},
   /** 重复回调 */
   onRepeat: suspend Animatable<T, V>.(Int) -> Unit,
) {
   require(count > 0)

   val countUpdated by rememberUpdatedState(count)
   val onFinishUpdated by rememberUpdatedState(onFinish)
   val onRepeatUpdated by rememberUpdatedState(onRepeat)

   LaunchedEffect(this) {
      try {
         var current = 0
         while (true) {
            current++
            onRepeatUpdated(current)
            if (current >= countUpdated) break
         }
      } finally {
         onFinishUpdated()
      }
   }
}