import mysql.connector
import json
import os

def run_sql_script(file_path, connection):
    try:
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

def lambda_handler(event, context):
    try:
        # Conexión a la base de datos y ejecución del script
        connection = mysql.connector.connect(
            host=os.environ("DB_HOST"),
            port=os.environ("DB_PORT"),
            user=os.environ("DB_USER"),
            password=os.environ("DB_PASS"),
            database=os.environ("DB_NAME")
        )
        sql_file_path = 'lambda/init.sql'
        run_sql_script(sql_file_path, connection)

        # Respuesta exitosa
        return {
            'statusCode': 200,
            'headers': {
                'Content-Type': 'application/json',
            },
            'body': json.dumps({'message': 'Script SQL ejecutado con éxito'})
        }
    except Exception as e:
        # Respuesta en caso de error
        return {
            'statusCode': 500,
            'headers': {
                'Content-Type': 'application/json',
            },
            'body': json.dumps({'error': str(e)})
        }
