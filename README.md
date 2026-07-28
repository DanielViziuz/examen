# Examen - Servicio de Pagos

API para dar de alta pagos, consultar su estatus, actualizarlo y notificar el cambio a través de RabbitMQ.

## Levantar el proyecto

Todo el entorno (aplicación, mongoDB, RabbitMQ y visor de base de datos) se levanta con un solo comando. No es necesario instalar Java, Maven, MongoDB ni RabbitMQ de forma local.

### Requisito previo
- Tener Docker y Docker Compose instalados.

### Comando

```bash
docker compose up
```

Si se hicieron cambios en el código y se necesita reconstruir la imagen de la aplicación:

```bash
docker compose up --build
```

Para detener y eliminar los contenedores:

```bash
docker compose down
```

### Contenedores que se levantan

| Servicio | Descripción | Puerto |
|---|---|---|
| **app-prueba** | Aplicativo Spring Boot (Prueba de pagos) | `8080` |
| **prueba-mongo** | Base de datos MongoDB | `27017` |
| **mongo-express** | Interfaz web para visualizar la base de datos MongoDB. | `8082` |
| **rabbitmq** | Broker de mensajería (con panel de administración). | `5672` (AMQP) / `15672` (panel admin) |

---

## Ligas del aplicativo

| Descripción | URL |
|---|---|
| API de Pagos | http://localhost:8080/payment |
| **Documentación Swagger** | http://localhost:8080/swagger-ui/index.html |
| **Mongo Express** (visor de base de datos) | http://localhost:8082 (usuario/contraseña: `admin` / `pass`)|
| **Panel de administración RabbitMQ** | http://localhost:15672 (usuario/contraseña: `guest` / `guest`) |

---

## Colección de Postman

En la carpeta [`/postman`](./postman) se incluye la colección `Prueba.postman_collection.json` con ejemplos para probar los 4 endpoints del servicio:

- Dar de alta un pago
- Consultar todos los pagos
- Consultar un pago por id
- Actualizar el status de un pago

Solo hay que importar el archivo en Postman; las peticiones ya están configuradas para apuntar a `http://localhost:8080`.

Al actualizarse el status, el servicio publica automáticamente un evento a RabbitMQ, el cual es procesado por dos consumers independientes.

---

## Definición de Exchanges, Queues y Mensaje (RabbitMQ)

### Exchange

| Propiedad | Valor |
|---|---|
| Nombre | `pagos.exchange` |
| Tipo | `topic` |
| Durable | Sí |

Se eligió un exchange de tipo `topic` para permitir que, en el futuro, se agreguen nuevas colas con distintos patrones de routing key sin modificar el publisher (considerando la solicitud del examen).

### Routing Key

| Routing key | Cuándo se emite |
|---|---|
| `payment.status.changed` | Cada vez que el estatus de un pago es actualizado exitosamente |

### Queues (Consumers)

| Queue | Propósito | Binding (routing key) |
|---|---|---|
| `payments.notification.queue` | Simula el envío de una notificación al usuario informando el cambio de estatus de su pago | `payment.status.changed` |
| `payments.audit.queue` | Registra un log de auditoría con el histórico de cambios de estatus del pago | `payment.status.changed` |

Ambas colas están asociadas al mismo routing key con el mismo exchange `topic`, por lo que cada cola recibe su propia copia del mensaje y lo procesa de forma independiente.

### Definición del mensaje

```json
{
  "paymentId": "64f1a2b3c4d5e6f7a8b9c0d1",
  "previousStatus": "PENDIENTE",
  "newStatus": "COMPLETADO",
  "createdAt": "2026-07-28T10:30:00"
}
```

| Campo | Tipo | Descripción |
|---|---|---|
| `paymentId` | `string` | Identificador del pago (`_id` en MongoDB) cuyo estatus cambió |
| `previousStatus` | `string` | Estatus del pago antes del cambio |
| `newStatus` | `string` | Estatus del pago después del cambio |
| `createdAt` | `string (ISO 8601 date-time)` | Fecha y hora en la que se generó el evento |

---

## Esquema de Base de Datos

El JSON Schema se llama `Payment` a continuación esta es la estructura.

```json
{
  "title": "Payment",
  "type": "object",
  "properties": {
    "id": { "type": "string" },
    "concept": { "type": "string" },
    "productQuantity": { "type": "integer" },
    "origin": { "type": "string" },
    "destiny": { "type": "string" },
    "totalAmount": { "type": "number" },
    "status": { "type": "string", "enum": ["NEW", "IN_PROGRESS", "SUCCESSFUL", "ERROR"] }
  }
}
```

---
