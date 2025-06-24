package com.newangle.healthy.base.logger

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.orhanobut.logger.LogStrategy
import java.io.File
import java.io.FileWriter
import java.io.IOException


class FileLogStrategy(private val handler: Handler) : LogStrategy {
    override fun log(level: Int, tag: String?, message: String) {
        // do nothing on the calling thread, simply pass the tag/msg to the background thread

        handler.sendMessage(handler.obtainMessage(level, message))
    }

    internal class WriteHandler(
        looper: Looper,
        private val folder: String,
        private val maxFileSize: Int,
        private val mFileName: String
    ) :
        Handler(looper) {
        override fun handleMessage(msg: Message) {
            val content = msg.obj as String

            var fileWriter: FileWriter? = null
            val logFile = getLogFile(folder, mFileName)

            try {
                fileWriter = FileWriter(logFile, true)

                writeLog(fileWriter, content)

                fileWriter.flush()
                fileWriter.close()
            } catch (e: IOException) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush()
                        fileWriter.close()
                    } catch (e1: IOException) { /* fail silently */
                    }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        @Throws(IOException::class)
        private fun writeLog(fileWriter: FileWriter, content: String) {
            fileWriter.append(content)
        }

        private fun getLogFile(folderName: String, fileName: String): File {
            val folder = File(folderName)
            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                folder.mkdirs()
            }

            var newFileCount = 0
            var newFile: File
            var existingFile: File? = null

            newFile = File(folder, String.format("%s_%s.log", fileName, newFileCount))
            while (newFile.exists()) {
                existingFile = newFile
                newFileCount++
                newFile = File(folder, String.format("%s_%s.log", fileName, newFileCount))
            }

            if (existingFile != null) {
                if (existingFile.length() >= maxFileSize) {
                    return newFile
                }
                return existingFile
            }

            return newFile
        }
    }
}
