#!/bin/bash

# Invocar la función Lambda en LocalStack
aws --endpoint-url=http://localhost:4566 lambda invoke \
    --function-name miFuncionLambda \
    response.json

# Imprimir la respuesta (opcional)
cat response.json
