package test

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.*
import org.jetbrains.anko.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class)
public class BuildSpannedTest() {
    @Test
    public fun test() {
        val spannable = buildSpanned {
            append("123")
            appendln("456")
        }
        
        val lineSeparator = System.getProperty("line.separator")
        assertEquals("123456" + lineSeparator, spannable.toString())
        
        val spannable2 = buildSpanned {
            append("123", Bold, Italic)
            append("456", Underline, Strikethrough)
            append("789", foregroundColor(Color.RED), backgroundColor(Color.BLUE))
        }
        assertEquals("123456789", spannable2.toString())
        val styleSpans: Array<StyleSpan> = spannable2.getSpans(0, 3, StyleSpan::class.java)
        assertEquals(2, styleSpans.size)
        assertTrue(styleSpans.any { it.style == Typeface.BOLD })
        assertTrue(styleSpans.any { it.style == Typeface.ITALIC })
        
        val underlineSpan: Array<UnderlineSpan> = spannable2.getSpans(3, 6, UnderlineSpan::class.java)
        val strikethroughSpan: Array<StrikethroughSpan> = spannable2.getSpans(3, 6, StrikethroughSpan::class.java)
        assertEquals(1, underlineSpan.size)
        assertEquals(1, strikethroughSpan.size)

        val backgroundColorSpan: Array<BackgroundColorSpan> = spannable2.getSpans(6, 9, BackgroundColorSpan::class.java)
        val foregroundColorSpan: Array<ForegroundColorSpan> = spannable2.getSpans(6, 9, ForegroundColorSpan::class.java)
        assertEquals(1, backgroundColorSpan.size)
        assertEquals(1, foregroundColorSpan.size)
        assertEquals(Color.RED, foregroundColorSpan[0].foregroundColor)
        assertEquals(Color.BLUE, backgroundColorSpan[0].backgroundColor)
        
        println("[COMPLETE]")
    }
}