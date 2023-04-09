package cn.spacexc.wearbili.remake.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State
import coil.compose.AsyncImagePainter.Companion.DefaultTransform
import coil.request.ImageRequest

/**
 * 获取哔哩哔哩图片时带参请求，限制图片宽高达到节流节能的效果
 *
 * @author java30433
 * @date 2023/4/9 10:48
 * @param url: 直接传入图片地址，如：https://i1.hdslb.com/bfs/archive/e5fff1472bad1c0c6bcb3004205f9be23b58ffc0.jpg
 * @link https://socialsisteryi.github.io/bilibili-API-collect/docs/other/picture.html
 */
@Composable
fun BiliImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (State) -> State = DefaultTransform,
    onState: ((State) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
) {
    val size = remember { mutableStateOf(IntSize(0, 0)) }
    Box(modifier = modifier.onSizeChanged {
        size.value = it
    }) {
        if (size.value.width != 0) {
            val realUrl = "${url.replace("http://", "https://")}${
                if (url.contains(".hdslb.com/bfs")) "@${size.value.width}w_${size.value.height}h.webp" else ""
            }"
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(realUrl)
                    .crossfade(true).build(),
                contentDescription = contentDescription,
                modifier = Modifier.matchParentSize(),
                transform = transform,
                onState = onState,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                filterQuality = filterQuality
            )
        }
    }
}