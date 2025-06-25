package com.newangle.healthy.base.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor() {
    fun switchLanguage(locale: Locale) {
        if (locale == currentLocale) {
            return
        }
        val compat = LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        AppCompatDelegate.setApplicationLocales(compat)
    }

    private val currentLocale: Locale?
        get() = AppCompatDelegate.getApplicationLocales()[0]
}
