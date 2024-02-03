package ap.service.happytohelp.utilUi

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ap.service.happytohelp.ui.theme.GreenOriginal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Usage
// PingPongSwitch(
//     textFirst = "Ping",
//     textSecond = "Pong"
// )

@Composable
fun PingPongSwitch(
    state: PingPongSwitchState? = rememberPingPongSwitchState(),
    textFirst: String,
    textSecond: String,
) {
    Box(
        modifier = Modifier
            .pingPongSwitchPointer(state!!)
            .graphicsLayer {
                cameraDistance = 16f
                rotationY = state!!.tiltProgress.value * 16
            },
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
        ) {
            Row(
                Modifier
                    .width(300.dp)
                    .border(1.dp, MaterialTheme.colors.GreenOriginal, RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .weight(1f), text = textFirst, color = MaterialTheme.colors.GreenOriginal, textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .weight(1f),
                    text = textSecond, color = MaterialTheme.colors.GreenOriginal, textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier
                .graphicsLayer {
                    cameraDistance = 16f
                    transformOrigin = TransformOrigin(1f, 0.5f)
                    rotationY = state.flipRotation.value
                }
                .background(MaterialTheme.colors.GreenOriginal, RoundedCornerShape(CornerSize(50), CornerSize(0), CornerSize(0), CornerSize(50)))
                .fillMaxWidth(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .graphicsLayer {
                            rotationY = if (state.flipRotation.value <= 90f) 0f else 180f
                        },
                    text = if (state.flipRotation.value <= 90f) textFirst else textSecond,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun Modifier.pingPongSwitchPointer(state: PingPongSwitchState) = pointerInput(Unit) {
    forEachGesture {
        awaitPointerEventScope {
            do {
                val event = awaitPointerEvent()
                val xRelative = event.changes.first().position.x / size.width
                state.setTouchTarget(if (xRelative <= 0.5f) PingPongSwitchState.TouchTarget.FIRST else PingPongSwitchState.TouchTarget.SECOND)
            } while (event.type != PointerEventType.Release)
            state.setTouchTarget(PingPongSwitchState.TouchTarget.NONE)
        }
    }
}

@Composable
fun rememberPingPongSwitchState(
    initialSelection: PingPongSwitchState.Selection = PingPongSwitchState.Selection.FIRST,
): PingPongSwitchState {
    val scope = rememberCoroutineScope()
    return remember(initialSelection) { PingPongSwitchState(initialSelection, scope) }
}

class PingPongSwitchState(
    initialSelection: Selection,
    private val scope: CoroutineScope
) {
    val selection = mutableStateOf(initialSelection)
    val tiltProgress = Animatable(0f)
    val flipRotation = Animatable(0f)

    init {
        scope.launch {
            setSelection(initialSelection, false)
        }
    }

    fun setSelection(selection: Selection, animate: Boolean) {
        this.selection.value = selection
        val targetRotation = if (selection == Selection.FIRST) 0f else 180f
        scope.launch {
            if (animate) {
                flipRotation.animateTo(
                    targetRotation,
                    spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow)
                )
            } else {
                flipRotation.snapTo(targetRotation)
            }
        }
    }

    fun setTouchTarget(touchTarget: TouchTarget) {
        scope.launch {
            tiltProgress.animateTo(
                when (touchTarget) {
                    TouchTarget.FIRST -> -1f
                    TouchTarget.SECOND -> 1f
                    TouchTarget.NONE -> 0f
                },
                spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
            )
        }

        when (touchTarget) {
            TouchTarget.FIRST -> setSelection(Selection.FIRST, true)
            TouchTarget.SECOND -> setSelection(Selection.SECOND, true)
            TouchTarget.NONE -> {}
        }
    }

    enum class Selection {
        FIRST, SECOND
    }

    enum class TouchTarget {
        FIRST, SECOND, NONE
    }
}

@Preview
@Composable
fun ViewScreen(){
    PingPongSwitch(null,"1","2")
}