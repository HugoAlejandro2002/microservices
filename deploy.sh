#!/bin/bash

apt-get install -y zip jq

# Verificar si existe app.py en la carpeta lambda y crearlo si no existe
if [ ! -f "lambda/app.py" ]; then
    echo "Creando app.py en la carpeta lambda..."
    mkdir -p lambda
    cat > lambda/app.py <<EOL
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
sql_file_path = 'lambda/init.sql'

# Ejecutar el script
run_sql_script(sql_file_path)
EOL
fi

if [ -f "function.zip" ]; then
    echo "function.zip ya existe. Eliminando el archivo existente..."
    rm function.zip
fi

zip -r function.zip lambda/

# Crear la función Lambda y almacenar el ARN
LAMBDA_ARN=$(aws --endpoint-url=http://localhost:4566 lambda create-function --function-name miFuncionLambda --zip-file fileb://function.zip --handler app.handler --runtime python3.9 --role arn:aws:iam::123456789012:role/irrelevante | jq -r '.FunctionArn')

# Crear API Gateway y almacenar el ID
API_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-rest-api --name 'miApiLocal' | jq -r '.id')

# Obtener el ID del recurso padre y almacenarlo
PARENT_ID=$(aws --endpoint-url=http://localhost:4566 apigateway get-resources --rest-api-id $API_ID | jq -r '.items[0].id')

# Crear un recurso en API Gateway y almacenar su ID
RESOURCE_ID=$(aws --endpoint-url=http://localhost:4566 apigateway create-resource --rest-api-id $API_ID --parent-id $PARENT_ID --path-part miRecurso | jq -r '.id')

# Configurar el método GET en el recurso
aws --endpoint-url=http://localhost:4566 apigateway put-method --rest-api-id $API_ID --resource-id $RESOURCE_ID --http-method GET --authorization-type NONE

# Configurar la integración con la función Lambda
aws --endpoint-url=http://localhost:4566 apigateway put-integration --rest-api-id $API_ID --resource-id $RESOURCE_ID --http-method GET --type AWS_PROXY --integration-http-method POST --uri arn:aws:apigateway:us-east-1:lambda:path/2015-03-31/functions/$LAMBDA_ARN/invocations

# Crear una implementación de la API
aws --endpoint-url=http://localhost:4566 apigateway create-deployment --rest-api-id $API_ID --stage-name test

# Imprimir la URL final para acceso al recurso
echo "URL del recurso: http://localhost:4566/restapis/$API_ID/test/_user_request_/miRecurso"
