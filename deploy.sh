#!/bin/bash

# Instalar paquetes del sistema requeridos
apt-get install -y zip jq

# Navegar al directorio lambda
cd lambda

# Verificar si existe app.py, crearlo si no existe
if [ ! -f "app.py" ]; then
    echo "Creando app.py..."
    cat > app.py <<EOL
import mysql.connector
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

def handler_lambda(event, context):
    # Establecer conexión con la base de datos usando variables de entorno
    connection = mysql.connector.connect(
        host=os.getenv("DB_HOST"),
        port=os.getenv("DB_PORT"),
        user=os.getenv("DB_USER"),
        password=os.getenv("DB_PASS"),
        database=os.getenv("DB_NAME")
    )

    # Ruta al archivo SQL
    sql_file_path = 'lambda/init.sql'

    # Ejecutar el script
    run_sql_script(sql_file_path, connection)

    # Retornar información al finalizar
    return {
        'statusCode': 200,
        'body': 'Script SQL ejecutado con éxito'
    }
EOL
fi

Instalar dependencias de Python desde requirements.txt en el directorio lambda
if [ -f "requirements.txt" ]; then
    pip install -r requirements.txt -t .
fi

# Establecer permisos de ejecución para el script manejador de Lambda
chmod 755 app.py

# Regresar al directorio raíz del proyecto
cd ..

# Eliminar function.zip existente si existe
if [ -f "function.zip" ]; then
    echo "Eliminando function.zip existente..."
    rm function.zip
fi

# Crear un nuevo archivo ZIP con la función Lambda y sus dependencias
echo "Creando nuevo paquete de función Lambda..."
zip -r function.zip lambda/

# Desplegar la función Lambda usando LocalStack
echo "Desplegando en LocalStack..."
LAMBDA_ARN=$(aws --endpoint-url=http://localhost:4566 lambda create-function \
    --function-name miFuncionLambda \
    --zip-file fileb://function.zip \
    --handler lambda/app.lambda_handler \
    --runtime python3.9 \
    --role arn:aws:iam::123456789012:role/irrelevante \
    --environment "Variables={DB_HOST=backend-db.clk840mg4qol.us-east-2.rds.amazonaws.com,DB_PORT=3306,DB_USER=admin,DB_PASS=admin123,DB_NAME=reservations_db}" | jq -r '.FunctionArn')

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

# Imprimir la URL final del recurso
echo "URL del recurso: http://localhost:4566/restapis/$API_ID/test/_user_request_/miRecurso"

# Mensaje final
echo "Despliegue completo."
