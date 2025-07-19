# Farmacia Jireh - Sistema de Gestión

Sistema completo de gestión para la farmacia "Jireh" desarrollado en Java con NetBeans y utilizando MySQL como base de datos. Este proyecto facilita la administración de diversos aspectos de una farmacia.

---

## 🚀 Características Principales

-   **Gestión de Datos**: Permite administrar información crucial para el funcionamiento de la farmacia.
-   **Conexión MySQL**: Utiliza una base de datos relacional para la persistencia de los datos.
-   **Interfaz de Usuario**: Desarrollado en NetBeans, ofreciendo una interfaz de escritorio para su uso.

---

## 📋 Clases de Dominio (Ejemplos Potenciales)

Aunque no se especificaron las clases de dominio, un sistema de farmacia típicamente gestionaría entidades como:

1.  **Productos** - Medicamentos y otros artículos a la venta.
2.  **Ventas** - Registro de transacciones y pedidos.
3.  **Clientes** - Información de los clientes de la farmacia.
4.  **Empleados** - Gestión del personal.
5.  **Proveedores** - Datos de los distribuidores de productos.
6.  **Inventario** - Control de stock de productos.

---

## 🛠️ Instalación y Configuración

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

-   **NetBeans IDE**: Entorno de Desarrollo Integrado (versión 8.2 o superior recomendada).
-   **JDK (Java Development Kit)**: (versión 8 o superior).
-   **MySQL Server**: Base de datos relacional.
-   **MySQL Workbench** o algún otro cliente MySQL para gestionar la base de datos.

### Pasos de Instalación

1.  **Clonar el Repositorio**
    Primero, descarga el proyecto de GitHub. Abre tu terminal o línea de comandos y ejecuta el siguiente comando:

    ```bash
    git clone [https://github.com/Fab2207/Project-Integrador-JIREH.git](https://github.com/Fab2207/Project-Integrador-JIREH.git)
    ```

    Una vez completado, navega al directorio del proyecto:

    ```bash
    cd Project-Integrador-JIREH
    ```

2.  **Configurar la Base de Datos MySQL**
    Este proyecto utiliza una base de datos MySQL. Sigue estos pasos para configurarla:

    #### a. Crear la Base de Datos
    Abre **MySQL Workbench**  o tu cliente MySQL preferido y crea una nueva base de datos llamada `farmacia_jireh`. Puedes hacerlo con el siguiente comando SQL:

    ```sql
    CREATE DATABASE farmacia_jireh;
    ```

    #### b. Ejecutar el Script de la Base de Datos
    Dentro del repositorio clonado, deberías encontrar un archivo SQL con la estructura y los datos de la base de datos (por ejemplo, `script_farmacia_jireh.sql` o un nombre similar). Abre este archivo en tu cliente MySQL y ejecútalo en la base de datos `farmacia_jireh` que acabas de crear. Esto poblará la base de datos con las tablas y datos necesarios para el funcionamiento del sistema.

3.  **Abrir el Proyecto en NetBeans**
    Abre **NetBeans IDE**.  Luego, ve a `File > Open Project...` y selecciona la carpeta raíz del proyecto `Project-Integrador-JIREH` que clonaste.

4.  **Configurar las Credenciales de la Base de Datos en NetBeans**
    Dentro de NetBeans, navega a la clase responsable de la conexión a la base de datos. Generalmente, esta clase se llama `ConexionMySQL` y se encuentra en una ruta similar a `src/main/java/tu_paquete/conexion/ConexionMySQL.java`.

    Busca las líneas donde se definen el usuario y la contraseña de la base de datos. Deberían ser similares a esto:

    ```java
    // Ejemplo (la estructura exacta puede variar en tu código)
    private static final String USUARIO = "root";          // <-- Cambia esto
    private static final String CONTRASENA = "tu_contraseña"; // <-- Cambia esto
    private static final String URL = "jdbc:mysql://localhost:3306/farmacia_jireh";
    ```

    **Cambia el valor de `USUARIO` y `CONTRASENA`** con las credenciales predeterminadas de tu instalación de MySQL. Usualmente, el usuario es `root` y la contraseña puede estar vacía o ser la que configuraste durante la instalación de MySQL.

5.  **Verificar la Conexión y las Tablas**
    Una vez que hayas realizado los cambios en la clase de conexión, **ejecuta el proyecto desde NetBeans** (`Run > Run Project` o presionando F6). Si la configuración de la base de datos es correcta y las credenciales coinciden, el proyecto debería iniciarse sin errores de conexión. Puedes probar las funcionalidades de la aplicación para confirmar que los datos se muestran, guardan y actualizan correctamente, lo que indicaría que las tablas y la conexión están funcionando.

---

## 📝 Comandos / Pasos Adicionales

Dado que es un proyecto de NetBeans, los comandos se gestionan principalmente desde el IDE. Sin embargo, puedes considerar:

```bash
# Navegar al directorio del proyecto después de clonar
cd Project-Integrador-JIREH

# Para ejecutar el script SQL manualmente desde la línea de comandos (opcional)
mysql -u tu_usuario -p farmacia_jireh < ruta/al/script_farmacia_jireh.sql
