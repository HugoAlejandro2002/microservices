import mysql.connector
import json
import os
from datetime import datetime

def check_and_update_data(cursor):
    try:
        # Verificar si hay datos en la tabla
        cursor.execute("SELECT COUNT(*) FROM DINING_TABLE")
        count = cursor.fetchone()[0]

        if count > 0:
            # Si hay datos, actualizarlos
            current_datetime = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            update_query = """
            UPDATE DINING_TABLE 
            SET available = TRUE, 
                available_time = %s
            """
            cursor.execute(update_query, (current_datetime,))
            print("Datos existentes actualizados.")
            return True
    except mysql.connector.Error:
        return False
    return False

def run_sql_script(file_path, connection):
    try:
        cursor = connection.cursor()

        if check_and_update_data(cursor):
            connection.commit()
            return

        with open(file_path, 'r') as file:
            sql_script = file.read()

        current_date = datetime.now().strftime("%Y-%m-%d")
        sql_script = sql_script.replace("{CURRENT_DATE}", current_date)

        for result in cursor.execute(sql_script, multi=True):
            if result.with_rows:
                print("Rows:", result.fetchall())
            else:
                print("Rows affected:", result.rowcount)

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
        connection = mysql.connector.connect(
            host=os.environ.get('DB_HOST'),
            port=os.environ.get('DB_PORT'),
            user=os.environ.get('DB_USER'),
            password=os.environ.get('DB_PASS'),
            database=os.environ.get('DB_NAME')
        )
        sql_file_path = 'init.sql'
        run_sql_script(sql_file_path, connection)

        return {
            "statusCode": 200,
            "headers": {
                "Content-Type": "application/json",
            },
            "body": json.dumps({"message": "Script SQL ejecutado con éxito"})
        }
    except Exception as e:
        return {
            "statusCode": 500,
            "headers": {
                "Content-Type": "application/json",
            },
            "body": json.dumps({"error": str(e)})
        }
