package io.github.rysefoxx.challenge.core.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import java.io.File
import java.io.FileOutputStream
import java.net.URL

object JarDownloader {

    fun download(fileName: String) = runBlocking {
        val job = async {
            withContext(Dispatchers.IO) {
                val url = URL("https://cdn.ryseinventory.de/Downloads/$fileName.jar")
                val connection = url.openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                val file = File("plugins/", "$fileName.jar")
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                outputStream.close()
                file

            }
        }
        val file = job.await()
    }

    fun loadAndActive(file: File) {
        val plugin = Bukkit.getPluginManager().loadPlugin(file)
        plugin?.let { Bukkit.getPluginManager().enablePlugin(it) }
    }
}