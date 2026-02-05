# Commercial Visit Assistant - PoC

Microservicio de generación de informes ejecutivos para visitas comerciales con integración de IA Generativa.

## Arquitectura

Esta PoC sigue una **Arquitectura Hexagonal Pura (Ports & Adapters)** al estilo BBVA APX:

- **domain**: Modelo de dominio puro sin anotaciones de Spring
- **application**: Casos de uso y puertos (interfaces)
- **infrastructure**: Adaptadores de entrada (controllers) y salida (repositories, servicios externos)

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Lombok** para boilerplate
- **MapStruct** para mapeo DTO-Dominio
- **H2** como base de datos simulada
- **Mockito** para tests unitarios
- **OpenAPI 3.0** con SpringDoc

## Endpoints

### GET /v1/visits/briefing/{clientId}

Genera un briefing de visita comercial basado en el perfil financiero del cliente.

**Ejemplo de uso:**

```bash
curl -X GET "http://localhost:8080/commercial-visit-assistant/v1/visits/briefing/12345678-1234-1234-1234-123456789012"
```

**Clientes de prueba:**
- `12345678-1234-1234-1234-123456789012` (Juan Pérez - Perfil Moderado)
- `87654321-4321-4321-4321-210987654321` (María Rodríguez - Perfil Conservador)

## Flujo de Procesamiento

1. **Controller**: Recibe la petición GET con el clientId
2. **Use Case**: Orquesta el flujo de negocio
3. **Repository**: Recupera el perfil financiero del cliente (simulado)
4. **Anonymizer**: Anonimiza datos sensibles (PII)
5. **IA Adapter**: Genera resumen ejecutivo (simulado)
6. **Response**: Devuelve el briefing al gestor comercial

## Ejecutar la Aplicación

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080/commercial-visit-assistant`

## Documentación API

Una vez iniciada la aplicación, accede a la documentación Swagger:
- **Swagger UI**: http://localhost:8080/commercial-visit-assistant/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/commercial-visit-assistant/v3/api-docs

## Tests

Ejecutar tests unitarios:
```bash
mvn test
```

## Estructura de Paquetes

```
com.bbva.apx.commercialvisit/
├── domain/                     # Modelo de dominio puro
│   ├── model/                  # Entidades de dominio
│   └── service/                # Servicios de dominio
├── application/                # Capa de aplicación
│   ├── ports/                  # Interfaces de entrada/salida
│   └── usecases/               # Implementación de casos de uso
├── infrastructure/             # Adaptadores
│   ├── inbound/                # Controllers REST
│   └── outbound/               # Repositories y servicios externos
└── config/                     # Configuración Spring
```

## Notas de Implementación

- **Fake AI Adapter**: Simula respuestas de GPT-4 sin requerir API keys
- **Mock Repository**: Simula acceso a base de datos Oracle con datos de prueba
- **Data Anonymization**: Implementa hash básico para PII en entorno de producción
- **Contract First**: API definida primero en OpenAPI 3.0# PoC_Commercial-Visit-Assistant
