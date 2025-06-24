package com.newangle.healthy.base.logger

import android.os.Handler
import android.os.HandlerThread
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.LogStrategy
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileFormatStrategy private constructor(builder: Builder) :
    FormatStrategy {
    private val date: Date
    private val dateFormat: SimpleDateFormat
    private val logStrategy: LogStrategy
    private val tag: String?

    init {
        date = builder.date!!
        dateFormat = builder.dateFormat!!
        logStrategy = builder.logStrategy!!
        tag = builder.tag
    }

    override fun log(priority: Int, onceOnlyTag: String?, message: String) {
        val tag = formatTag(onceOnlyTag)

        date.time = System.currentTimeMillis()

        val builder = StringBuilder()
        // machine-readable date/time
        builder.append(LEFT_SEPARATOR)
        builder.append(date.time.toString())
        builder.append(RIGHT_SEPARATOR)
        // human-readable date/time
        builder.append(LEFT_SEPARATOR)
        builder.append(dateFormat.format(date))
        builder.append(RIGHT_SEPARATOR)
        // level
        builder.append(LEFT_SEPARATOR)
        builder.append(logLevel(priority))
        builder.append(RIGHT_SEPARATOR)
        // tag
        builder.append(LEFT_SEPARATOR)
        builder.append(tag)
        builder.append(RIGHT_SEPARATOR)
        builder.append(message)
        // new line
        builder.append(NEW_LINE)

        logStrategy.log(priority, tag, builder.toString())
    }

    private fun logLevel(value: Int): String {
        return when (value) {
            Logger.VERBOSE -> "VERBOSE"
            Logger.DEBUG -> "DEBUG"
            Logger.INFO -> "INFO"
            Logger.WARN -> "WARN"
            Logger.ERROR -> "ERROR"
            Logger.ASSERT -> "ASSERT"
            else -> "UNKNOWN"
        }
    }

    private fun formatTag(tag: String?): String? {
        if (tag != null && tag != this.tag) {
            return this.tag + "-" + tag
        }
        return this.tag
    }

    class Builder {
        var date: Date? = null
        var dateFormat: SimpleDateFormat? = null
        var logStrategy: LogStrategy? = null
        var tag: String = "PRETTY_LOGGER"

        private var path: String = ""

        fun filePath(path: String): Builder {
            this.path = path
            return this
        }

        fun build(): FileFormatStrategy {
            if (date == null) {
                date = Date()
            }
            if (dateFormat == null) {
                dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK)
            }
            if (logStrategy == null) {
                val ht = HandlerThread("AndroidFileLogger.$path")
                ht.start()
                val handler: Handler = FileLogStrategy.WriteHandler(
                    ht.looper,
                    path,
                    MAX_BYTES,
                    SimpleDateFormat(
                        "yyyyMMddHHmmssSSS",
                        Locale.UK
                    ).format(System.currentTimeMillis())
                )
                logStrategy = FileLogStrategy(handler)
            }
            return FileFormatStrategy(this)
        }

        companion object {
            private const val MAX_BYTES = 500 * 1024 // 500K averages to a 4000 lines per file
        }
    }

    companion object {
        private const val NEW_LINE = "\n"
        private const val RIGHT_SEPARATOR = "] "
        private const val LEFT_SEPARATOR = "["

        fun newBuilder(): Builder {
            return Builder()
        }
    }
}
