import mysql.connector
from dotenv import load_dotenv
import os

# Cargar variables de entorno desde .env
load_dotenv()

def run_sql_script(file_path):
    try:
        # Establecer conexión con la base de datos usando variables de entorno
        connection = mysql.connector.connect(
            host=os.getenv("DB_HOST"),
            port=os.getenv("DB_PORT"),
            user=os.getenv("DB_USER"),
            password=os.getenv("DB_PASS"),
            database=os.getenv("DB_NAME")
        )

        # Crear un cursor para ejecutar comandos SQL
        cursor = connection.cursor()

        # Leer el script SQL desde el archivo
        with open(file_path, 'r') as file:
            sql_script = file.read()

        # Ejecutar el script SQL
        for result in cursor.execute(sql_script, multi=True):
            if result.with_rows:
                print("Rows:", result.fetchall())
            else:
                print("Rows affected:", result.rowcount)

        # Confirmar los cambios
        connection.commit()

        print("Script SQL ejecutado con éxito.")
    except mysql.connector.Error as error:
        print(f"Error al ejecutar el script SQL: {error}")
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()
            print("Conexión a la base de datos cerrada.")

# Ruta al archivo SQL
sql_file_path = 'lamda/init.sql'

# Ejecutar el script
run_sql_script(sql_file_path)
