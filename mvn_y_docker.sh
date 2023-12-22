#!/bin/bash

# Definir las carpetas de los servicios y sus respectivas imágenes Docker
declare -A services_images=( ["admin-service"]="eljoamy/adminservice:0.0.1"
                             ["auth-service"]="eljoamy/authservice:0.0.1"
                             ["cloud-gateway"]="eljoamy/cloudgateway:0.0.1"
                             ["eureka-server-registry"]="eljoamy/serviceregistry:0.0.1"
                             ["notification-service"]="eljoamy/notificationservice:0.0.1"
                             ["reservation-service"]="eljoamy/reservationservice:0.0.1" )

# Función para procesar Maven en un servicio
process_maven() {
    service=$1
    cd "$service" || { echo "Error al entrar en la carpeta $service"; return 1; }

    # Ejecutar Maven Clean y Maven Install
    if ! mvn clean || ! mvn install; then
        echo "Error en mvn para $service"
        cd ..
        return 1
    fi

    cd ..
    return 0
}

# Función para procesar Docker en un servicio
process_docker() {
    service=$1
    image=${services_images[$service]}
    cd "$service" || { echo "Error al entrar en la carpeta $service"; return 1; }

    # Eliminar la imagen de Docker si existe y construir la nueva
    docker rmi "$image"
    if ! docker build -t "$image" .; then
        echo "Error al construir la imagen de Docker para $service"
        cd ..
        return 1
    fi

    cd ..
    return 0
}

# Función para procesar un servicio con opciones personalizadas
process_service() {
    service=$1

    # Mostrar opciones y obtener la elección del usuario
    echo "Qué comandos quieres ejecutar para $service?"
    echo "1. Maven (mvn)"
    echo "2. Docker (docker)"
    echo "3. Todos (mvn y docker)"
    echo "4. Volver atrás"
    read -p "Elige una opción (1-4): " command_choice

    # Confirmar la elección
    read -p "Estás seguro? (y/n): " confirm
    if [ "$confirm" != "y" ]; then
        echo "Cancelado el procesamiento de $service"
        return 0
    fi

    case $command_choice in
        1) process_maven "$service" ;;
        2) process_docker "$service" ;;
        3) process_maven "$service" && process_docker "$service" ;;
        4) return 0 ;;
        *) echo "Opción no válida." ;;
    esac
}

# Función para mostrar el menú principal y obtener la elección del servicio
show_menu() {
    echo "Selecciona el servicio a procesar:"
    echo "1. admin-service"
    echo "2. auth-service"
    echo "3. cloud-gateway"
    echo "4. eureka-server-registry"
    echo "5. notification-service"
    echo "6. reservation-service"
    echo "7. Todos"
    echo "8. Salir"
    read -p "Ingresa tu elección (1-8): " choice
    return $choice
}

# Bucle principal
while true; do
    show_menu
    choice=$?

    case $choice in
        1) process_service "admin-service" ;;
        2) process_service "auth-service" ;;
        3) process_service "cloud-gateway" ;;
        4) process_service "eureka-server-registry" ;;
        5) process_service "notification-service" ;;
        6) process_service "reservation-service" ;;
        7) for service in "${!services_images[@]}"; do
               if ! process_service "$service"; then
                   echo "El proceso falló para $service"
                   exit 1
               fi
           done ;;
        8) echo "Saliendo..."
           break ;;
        *) echo "Opción no válida. Por favor, intenta de nuevo." ;;
    esac
done
