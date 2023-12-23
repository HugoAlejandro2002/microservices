# Nombre del Proyecto

Una breve descripción de lo que hace tu proyecto de microservicios.

## Tabla de Contenidos
1. [Instalación](#instalación)
2. [Uso](#uso)
3. [Descripción de Servicios](#descripción-de-servicios)
4. [Docker Compose](#docker-compose)
5. [Scripts de Despliegue](#scripts-de-despliegue)
6. [Contribuciones](#contribuciones)
7. [Autores](#autores)
8. [Licencia](#licencia)

## Instalación

- Detalles sobre cómo clonar el repositorio y configurar el entorno local para el desarrollo.

```bash
git clone https://github.com/HugoAlejandro2002/microservices.git
```

- Una vez clonado el repositorio, debes tener instalado Docker y Docker Compose en tu máquina. Para instalar Docker. Se recomienda utilizar debian, puedes instalar docker con los [siguientes comandos](Installation/Docker.md).

## Uso

- Instrucciones sobre cómo ejecutar el proyecto en un entorno local. Por ejemplo:

```bash
docker-compose up
```

- Para ver los servicios puedes revsar la pagina de [Eureka](http://localhost:8761/). Ahi encontraras todos los servicios que se estan ejecutando.

- Ademas tambien esta lambda, la cual se ejeuta despuesde correr el docker-compose y ejecutar
```bash
./deploy.sh
```

- Lo que hace es reaizar un init.sql 
```sql
USE reservations_db;

CREATE TABLE IF NOT EXISTS DINING_TABLE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_number BIGINT,
    capacity BIGINT,
    available BOOLEAN,
    available_time TIMESTAMP
);

INSERT INTO DINING_TABLE (table_number, capacity, available, available_time) VALUES
(1, 4, TRUE, '2023-01-01 12:00:00'),
(2, 6, FALSE, '2023-01-02 13:00:00'),
(3, 2, TRUE, '2023-01-03 18:00:00'),
(4, 8, TRUE, '2023-01-04 19:00:00'),
(5, 4, FALSE, '2023-01-05 20:00:00');
```
en la DB de mysql que se encuentra deployada en AWS.

- Puedes cambiar tus datos de la db en el archivo [deploy.sh](deploy.sh) en la linea 99 
```bash
--environment "Variables={DB_HOST=localhost,DB_PORT=3306,DB_USER=root,DB_PASS=root,DB_NAME=reservations_db}"
```

## Descripción de Servicios

Breve descripción de cada microservicio incluido en el proyecto. Por ejemplo:

- **Service Registry:** Registro de servicios utilizando Eureka Server.
- **Cloud Gateway:** Puerta de enlace para el manejo de solicitudes a diferentes microservicios.
- **Admin Service:** Servicio para la administración.
- **Auth Service:** Servicio para la autenticación de usuarios.
- **Notification Service:** Servicio para el envío de notificaciones.
- **Reservation Service:** Servicio para la gestión de reservas.

## Docker Compose

Detalles sobre el archivo `docker-compose.yml`, incluyendo la configuración de cada servicio.

### Ejecución

```bash
docker-compose up
```


## Scripts de Despliegue

### `deploy.sh`

`deploy.sh` es un script que permite desplegar el archivo `app.py` en AWS Lambda. Para ello, se debe tener instalado el CLI de AWS y configurar las credenciales de acceso. Para más información, revisar la [documentación oficial](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html).

- Tambien prepara datos iniiales en nuestra DB de mysql en AWS para poder ser utilizadas mas adelante por el servicio de reservaciones.

### `mvn_y_docker.sh`

`mvn_y_docker.sh` es un script que permite utilizar los comandos de mvn y docker de manera mas rapida. Tiene una interfaz que permite seleccionar el comando que se desea ejecutar y a que servicio se desea ejecutar, puedes escoger entre todos los servicios o uno en especifico.

- Para ejecutar el script debes correr el siguiente comando:
```bash
./mvn_y_docker.sh
```
- `INICIO` es la interfaz que te permite seleccionar el comando y el servicio a ejecutar.
```bash
Selecciona el servicio a procesar:
1. admin-service
2. auth-service
3. cloud-gateway
4. eureka-server-registry
5. notification-service
6. reservation-service
7. Todos
8. Salir
Ingresa tu elección (1-8): 
```

- `SERVICIOS` es la interfaz que te permite seleccionar el servicio a procesar.
```bash
Ingresa tu elección (1-8): 2
Qué comandos quieres ejecutar para auth-service?
1. Maven (mvn)
2. Docker (docker)
3. Todos (mvn y docker)
4. Volver atrás
Elige una opción (1-4): 
```

## Contribuciones

¡Las contribuciones son bienvenidas y muy apreciadas! Aquí tienes una guía paso a paso para contribuir al proyecto:

1. **Fork del Repositorio**
   - Crea un 'fork' del repositorio para trabajar en tu propia copia. Puedes hacerlo visitando [microservices repo](https://github.com/HugoAlejandro2002/microservices.git) y haciendo clic en el botón "Fork" en la esquina superior derecha.

   ```bash
   # Clona tu fork en tu máquina local
   git clone https://github.com/tu-usuario/microservices.git
   cd microservices
   ```

2. **Crear una Rama**
   - Crea una rama para tu característica o corrección de errores. Utiliza un formato de nombre como `feature/nombre-de-tu-funcionalidad`.

   ```bash
   # Crea una nueva rama y cambia a ella
   git checkout -b feature/nombre-de-tu-funcionalidad
   ```

3. **Hacer Cambios**
   - Realiza los cambios necesarios en tu rama. Asegúrate de mantener el código limpio y bien documentado.

4. **Testear Cambios**
   - Antes de realizar cualquier commit, prueba tu código y asegúrate de que no introduces errores.

5. **Commit y Push**
   - Una vez que estés satisfecho con tus cambios, realiza un commit con una descripción clara de los cambios.

   ```bash
   git add .
   git commit -m "Descripción detallada de los cambios"
   git push origin feature/nombre-de-tu-funcionalidad
   ```

6. **Crear un Pull Request**
   - En GitHub, ve a tu fork y haz clic en "New pull request". Asegúrate de seleccionar tu rama y proporcionar una descripción detallada de los cambios y por qué se deben incorporar.

7. **Revisión**
   - Espera a que el propietario del repositorio original revise tu pull request. Puede que te pidan realizar cambios adicionales o sugerencias.

8. **Merge**
   - Una vez que tu pull request sea aprobado y fusionado, habrás contribuido exitosamente al proyecto.

### Buenas Prácticas para Contribuir

- Antes de empezar, revisa los 'issues' abiertos para ver si alguien ya está trabajando en lo que quieres hacer.
- Mantén tus commits pequeños y enfocados; un pull request debe resolver un solo problema o añadir una sola característica.
- Sigue las convenciones de código y estilo del proyecto.
- Incluye pruebas que cubran los cambios realizados si es posible.
- Actualiza la documentación si introduces cambios en la API o en características importantes.

## Autores

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/ElJoamy">
        <img src="https://avatars.githubusercontent.com/u/68487005?v=4" width="50" alt="ElJoamy"/>
        <br />
        <sub><b>Joseph Anthony Meneses Salguero</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/HugoAlejandro2002">
        <img src="https://avatars.githubusercontent.com/u/97768733?v=4" width="50" alt="HugoAlejandro2002"/>
        <br />
        <sub><b>Hugo Alejandro Apaza Huaycho</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/iandeimpaler">
        <img src="https://avatars.githubusercontent.com/u/11223344?v=4" width="50" alt="iandeimpaler"/>
        <br />
        <sub><b>Ian Marcus Terceros Villegas</b></sub>
      </a>
    </td>
  </tr>
</table>


## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
