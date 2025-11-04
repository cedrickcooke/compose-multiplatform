package org.jetbrains.compose.resources

import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme
import java.awt.HeadlessException
import java.awt.Toolkit
import java.util.*

internal actual fun getSystemEnvironment(): ResourceEnvironment {
    val locale = Locale.getDefault()
    //FIXME: don't use skiko internals
    val isDarkTheme = currentSystemTheme == SystemTheme.DARK
    val dpi = try {
        Toolkit.getDefaultToolkit().screenResolution
    } catch (_: HeadlessException) {
        // Default to 1x ("unscaled") resources when DPI info not available 
        DensityQualifier.MDPI.dpi
    }
    return ResourceEnvironment(
        language = LanguageQualifier(locale.language),
        region = RegionQualifier(locale.country),
        theme = ThemeQualifier.selectByValue(isDarkTheme),
        density = DensityQualifier.selectByValue(dpi)
    )
}
