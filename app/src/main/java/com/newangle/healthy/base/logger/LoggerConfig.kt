package com.newangle.healthy.base.logger

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class LoggerConfig(context: Context) {
    init {
        initLogger(context)
    }
    private fun  initLogger(context: Context) {
        val prettyFormatStrategy = PrettyFormatStrategy
            .newBuilder()
            .showThreadInfo(true)
            .methodCount(METHOD_COUNT)
            .tag(context.packageName)
            .build()


        Logger.addLogAdapter(AndroidLogAdapter(prettyFormatStrategy))

        val strategy = FileFormatStrategy.newBuilder().filePath(createFilePath(context)).build()
        Logger.addLogAdapter(DiskLogAdapter(strategy))
    }
    private fun createFilePath(context: Context) : String {
        val externalFilesDir = context.getExternalFilesDir("")
        val date = Date()
        val format = SimpleDateFormat(PATTERN, Locale.UK).format(date)
        return externalFilesDir?.path +File.separatorChar + FILE_NAME + File.separatorChar + format
    }

    fun i (message:String, vararg : Any? = ""){
        Logger.i(message, vararg)
    }

    companion object {
        private const val FILE_NAME = "logs"
        private const val METHOD_COUNT = 3
        private const val PATTERN = "yyyyMMdd"
    }
}
