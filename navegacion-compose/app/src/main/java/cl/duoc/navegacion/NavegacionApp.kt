package cl.duoc.navegacion

import android.app.Application
import cl.duoc.navegacion.leccion10.ContenedorDependencias
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase Application: se crea UNA vez, antes que cualquier Activity, y vive
 * mientras viva el proceso. Por eso es el lugar natural para guardar dependencias
 * compartidas por toda la app.
 *
 * - Lección 10 (DI manual): guardamos aquí nuestro [ContenedorDependencias].
 * - Lección 11 (Hilt): @HiltAndroidApp le indica a Hilt que genere aquí el
 *   contenedor raíz (SingletonComponent).
 *
 * Se registra en AndroidManifest.xml con android:name=".NavegacionApp".
 */
@HiltAndroidApp
class NavegacionApp : Application() {

    /** Contenedor de dependencias manual (Lección 10). `lazy` = se crea la primera vez que se usa. */
    val contenedor: ContenedorDependencias by lazy { ContenedorDependencias() }
}
