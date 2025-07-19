package com.newangle.healthy.base.logger

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.text.format.DateFormat
import com.orhanobut.logger.Logger
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong

object LogUtils {
    private const val TAG = "LogUtils"
    private const val LOG_DIR_NAME = "logs"
    private val MAX_LOG_SIZE = 2 * 1024 * 1024 // 2MB
    private const val MAX_LOG_FILES = 5

    private var logDir: File? = null
    private var currentLogFile: File? = null
    private val writer: BufferedWriter? = null
    private var logHandler: Handler? = null
    private val logSize = AtomicLong(0)

    fun init(context: Context) {
        // 创建日志目录
        logDir = File(context.getExternalFilesDir(null), LOG_DIR_NAME)
        if (!logDir!!.exists() && !logDir!!.mkdirs()) {
            Logger.e(TAG, "Failed to create log directory")
            return
        }

        // 创建带时间戳的子目录
        val timestamp = DateFormat.format("yyyyMMdd_HHmmss", Date()).toString()
        val sessionDir = File(logDir, timestamp)
        if (!sessionDir.exists() && !sessionDir.mkdirs()) {
            Logger.e(TAG, "Failed to create session log directory")
            return
        }

        // 创建日志文件
        val filename = "log_" + timestamp + ".log"
        currentLogFile = File(sessionDir, filename)

        // 初始化后台写入线程
        val handlerThread = HandlerThread("LogWriter", Process.THREAD_PRIORITY_BACKGROUND)
        handlerThread.start()
        logHandler = Handler(handlerThread.getLooper())

        // 设置未捕获异常处理器
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler { thread: Thread?, ex: Throwable? ->
            logCrash(ex!!)
            if (defaultHandler != null) {
                defaultHandler.uncaughtException(thread, ex)
            } else {
                Process.killProcess(Process.myPid())
                System.exit(10)
            }
        })
        LoggerConfig(context)
        Logger.i(TAG, "Logging initialized. Path: " + currentLogFile!!.getAbsolutePath())
    }

    fun v(msg: String) {
        v("VERB: ", msg)
    }

    fun v(tag: String, msg: String) {
        Logger.v("$tag%s", msg)
        writeToFile("V", tag, msg)
    }

    fun d(msg: String) {
        d("DEBUG: ", msg)
    }

    fun d(tag: String, msg: String) {
        Logger.d("$tag%s", msg)
        writeToFile("D", tag, msg)
    }

    fun i(msg: String) {
        i("INFO: ", msg)
    }

    fun i(tag: String, msg: String) {
        Logger.i("$tag%s", msg)
        writeToFile("I", tag, msg)
    }

    fun w(msg: String) {
        w("WARN: ", msg)
    }

    fun w(tag: String, msg: String) {
        Logger.w("$tag%s", msg)
        writeToFile("W", tag, msg)
    }

    fun e(msg: String) {
        e("ERROR: ", msg)
    }

    fun e(tag: String, msg: String) {
        Logger.e("$tag%s", msg)
        writeToFile("E", tag, msg)
    }

    fun e(tag: String, msg: String?, tr: Throwable) {
        Logger.e("$tag%s", msg, tr)
        writeToFile("E", tag, msg + "\n" + getStackTraceString(tr))
    }

    private fun writeToFile(level: String?, tag: String?, msg: String?) {
        if (logHandler == null || currentLogFile == null) return

        logHandler!!.post(Runnable {
            try {
                // 检查是否需要轮转日志
                if (logSize.get() > MAX_LOG_SIZE) {
                    rotateLogFiles()
                }

                // 格式化日志条目
                val timestamp = DateFormat.format("yyyy-MM-dd HH:mm:ss.SSS", Date()).toString()
                val logEntry = String.format(
                    Locale.US, "%s %s/%s: %s\n",
                    timestamp, level, tag, msg
                )

                FileWriter(currentLogFile, true).use { fw ->
                    BufferedWriter(fw).use { bw ->
                        bw.write(logEntry)
                        logSize.addAndGet(logEntry.length.toLong())
                    }
                }
            } catch (e: IOException) {
                Logger.e(TAG, "Failed to write log to file", e)
            }
        })
    }

    private fun logCrash(ex: Throwable) {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        ex.printStackTrace(pw)
        val stackTrace = sw.toString()

        writeToFile("E", "CRASH", "!!! APPLICATION CRASH !!!")
        writeToFile("E", "CRASH", stackTrace)
        writeToFile("E", "CRASH", "*** DEVICE INFO ***")
        writeToFile("E", "CRASH", "Model: " + Build.MODEL)
        writeToFile("E", "CRASH", "SDK: " + Build.VERSION.SDK_INT)
        writeToFile("E", "CRASH", "Version: " + Build.VERSION.RELEASE)
    }

    @Synchronized
    private fun rotateLogFiles() {
        try {
            writer!!.close()
            logSize.set(0)

            // 归档当前日志文件
            val timestamp = DateFormat.format("yyyyMMdd_HHmmss", Date()).toString()
            val archivedFile = File(currentLogFile!!.getParent(), "log_" + timestamp + ".log")
            currentLogFile!!.renameTo(archivedFile)

            // 创建新日志文件
            currentLogFile = File(currentLogFile!!.getParent(), "log_current.log")
            currentLogFile!!.createNewFile()

            // 清理旧日志
            val files = logDir!!.listFiles()
            if (files != null && files.size > MAX_LOG_FILES) {
                // 按修改时间排序后删除最旧的文件
                // 实现略（可根据需要添加）
            }
        } catch (e: IOException) {
            Logger.e(TAG, "Log rotation failed", e)
        }
    }

    private fun getStackTraceString(tr: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
}