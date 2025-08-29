# Banking System

Sistema bancario desarrollado en Spring Boot que permite gestionar clientes y cuentas bancarias.

### Diagrama de Clases - Domain Models

![Domain Models UML](uml/uml.png)

## Funcionalidades

### Gestión de Clientes
- Registro de nuevos clientes
- Consulta de todos los clientes

### Gestión de Cuentas Bancarias
- Apertura de cuentas (SAVINGS/CHECKING)
- Depósitos de dinero
- Retiros con validación de límites
- Consulta de saldos
- Listado de cuentas por cliente

## Tipos de Cuenta

- **SAVINGS**: Cuenta de ahorros (saldo mínimo: $0)
- **CHECKING**: Cuenta corriente (sobregiro permitido hasta: -$500)

## API Endpoints

### 📖 Documentación Completa
**[Ver documentación interactiva en Postman](https://documenter.getpostman.com/view/24255217/2sB3HeuPhV)**

### Clientes
- `POST /api/clients/register` - Registrar cliente
- `GET /api/clients` - Obtener todos los clientes

### Cuentas
- `POST /api/accounts/open` - Abrir cuenta
- `POST /api/accounts/{accountNumber}/deposit` - Depositar
- `POST /api/accounts/{accountNumber}/withdraw` - Retirar
- `GET /api/accounts/{accountNumber}/balance` - Consultar saldo
- `GET /api/accounts/client/{clientId}` - Cuentas por cliente

## Tecnologías

- **Spring Boot** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **MySQL** - Base de datos
- **Maven** - Gestión de dependencias

## 👥 Autores

- **Jessica Chanco**
- **Karen Duran Villa**
- **Tatiana Paucar**

