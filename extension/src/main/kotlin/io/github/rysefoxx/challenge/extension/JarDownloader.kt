package io.github.rysefoxx.challenge.extension

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import java.io.File
import java.io.FileOutputStream
import java.net.URL

object JarDownloader {

    fun download(fileName: String): File {
        runBlocking {
            withContext(Dispatchers.IO) {
                val url = URL("https://cdn.ryseinventory.de/Downloads/$fileName.jar")
                val connection = url.openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                val file = File("plugins/", "$fileName.jar")
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                outputStream.close()
            }
        }
        return File("plugins/", "$fileName.jar")
    }

    fun loadAndActive(file: File) {
        val plugin = Bukkit.getPluginManager().loadPlugin(file)
        plugin?.let { Bukkit.getPluginManager().enablePlugin(it) }
    }
}