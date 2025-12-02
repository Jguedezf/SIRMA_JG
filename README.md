<div align="center">

# 🚗 SIRMA JG
### Sistema Inteligente de Registro de Mantenimiento Automotriz

**Universidad Nacional Experimental de Guayana (UNEG)** *Técnicas de Programación III - Lapso 2025-2*

![Java](https://img.shields.io/badge/Java-JDK_25-orange?style=for-the-badge&logo=java)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-blue?style=for-the-badge&logo=intellij-idea)
![Status](https://img.shields.io/badge/Estado-Finalizado-success?style=for-the-badge)

</div>

---

## 📋 Descripción del Proyecto

**SIRMA JG** es una solución de software de ingeniería desarrollada bajo el patrón de arquitectura **MVC (Modelo-Vista-Controlador)**.

Su misión es gestionar integralmente el ciclo de vida del mantenimiento automotriz, permitiendo:
* 🚘 **Gestión de Flota:** Registro y control de vehículos.
* 🔧 **Control Técnico:** Administración de mecánicos y especialidades.
* 📝 **Bitácora Operativa:** Órdenes de servicio y seguimiento de estatus.
* 📊 **Reportes:** Generación de informes en formatos HTML y TXT.

---

## 🛠️ Tecnologías y Requisitos

Para ejecutar este proyecto, su entorno debe cumplir con:

* **Sistema Operativo:** Windows 10/11, macOS o Linux.
* **Java Development Kit (JDK):** Versión **25** (Oracle OpenJDK 25.0.1+).
* **Entorno de Desarrollo:** IntelliJ IDEA.
* **Dependencias:** Librería `jcalendar-1.4.jar` (Incluida en el proyecto).

---

## ⚙️ Manual de Instalación y Configuración

Siga estos pasos para desplegar el sistema en local:

### 1. Clonar el Repositorio
Abra su terminal y ejecute:
```bash
git clone [https://github.com/Jguedezf/SIRMA_JG.git](https://github.com/Jguedezf/SIRMA_JG.git)

2. Importar en IntelliJ IDEA
Abra IntelliJ IDEA.

Seleccione Open y busque la carpeta descargada.

Espere a que el IDE indexe los archivos.

3. Configuración del SDK
Vaya a: File > Project Structure > Project.

SDK: Seleccione 25.

Language Level: Seleccione SDK Default.

4. Solución de Librerías (JCalendar)
Si el código muestra errores en las fechas:

Vaya a File > Project Structure > Modules > Dependencies.

Haga clic en + -> JARs or Directories.

Seleccione jcalendar-1.4.jar (ubicado dentro de la carpeta del proyecto).

Clic en Apply.

5. Ejecución
Ubique el archivo src/Main.java, haga clic derecho y seleccione Run 'Main.main()'.

🔐 Credenciales de Acceso (Data Seeding)
El sistema viene precargado con datos de prueba. Utilice estas credenciales para entrar:

Usuario: admin

Contraseña: 1234

🧠 Principios de Ingeniería Aplicados
Este software implementa rigurosamente los pilares de la Programación Orientada a Objetos:

[x] Encapsulamiento: Protección de estado en clases del modelo (Vehiculo, Mecanico) mediante modificadores de acceso y Getters/Setters.

[x] Herencia: Extensión de componentes Swing para la interfaz gráfica (Ej: PanelGestionVehiculos extends JPanel).

[x] Polimorfismo: Sobreescritura de métodos para renderizado personalizado (RenderizadorEstadoOrden) y diseño visual (BotonFuturista).

[x] Persistencia: Almacenamiento de datos mediante Serialización de Objetos (.dat) y Streams.

[x] Concurrencia: Manejo seguro del Event Dispatch Thread (EDT) mediante SwingUtilities.invokeLater.

<div align="center">

👩‍💻 Autora
Johanna Guédez C.I: V-14.089.807

Ingeniería en Informática - UNEG

</div>