import net.kyori.adventure.key.Key
import org.bukkit.plugin.java.JavaPlugin
import util.Translation
import java.nio.file.Path
import java.util.*

object TranslationRegistration {

    fun register(plugin: JavaPlugin, key: Key, path: Path, locales: List<Locale>) {
        Translation(plugin, key, path, locales)
    }


}