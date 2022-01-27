# Rmm Services Server App

Backend task for NinjaRMM implementing a REST API with data persistence. This project aims 
to cover the requirements for a Remote Monitoring and Management company that provides services for monitoring and managing
devices.





## Tech Stack

**Client:** Any client capable of executing REST queries, such as Postman.

**Server:** Java, Spring Framework and H2 Database Engine.


## Installation

From the IDE (Used IntelliJ for development) Select the project to run in the Projects window.

Choose Run > Run Project (F6).

Unit testing also provided

## Features

- Functionality for customers to get, add, update and delete devices.
- Functionality for customers to add, delete and view services on their accounts.
- Functionality to calculate total monthly cost depending on the selected services and number of devices.
- Used Basic Authentication built-in Spring-boot-security, *user*: user *password*: password



## API Reference

### Customers

#### Get all Customers

```http
  GET /customers
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| n/a | n/a | **Returns**. List of Customer |

#### Get Customer by id

```http
  GET /customers/{customerId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

#### Create new Customer

```http
  POST /customers
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `n/a`      | `n/a` | n/a |


Request Body:
```json
{
  "name" : "YourName"
}
```

#### Edit an existing Customer by id

```http
  PUT /customers/{customerId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

Request Body:

```json
{
  "name" : "YourName"
}
```


#### Delete an existing customer

```http
  DELETE /customers/{customerId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |


### Devices

#### Get all devices assigned to a Customer

```http
  GET /customers/{customerId}/devices
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. customerId of item to fetch |

#### Create a Device and assign to a Customer

```http
  POST /customers/{customerId}/devices
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`      | `long` | **Required**. customerId of item to fetch |

Request Body:

```json
{
  "systemName": "SRV-0011",
  "deviceType": "Windows 2016 Server",
  "deviceCost" : 4
}
```


#### Update an existing device by id

```http
  PUT /customers/{customerId}/devices/{deviceId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. deviceId of item to fetch |
| `id`      | `long` | **Required**. customerId of item to fetch |

Request Body:

```json
{
  "systemName": "SRV-0011",
  "deviceType": "Windows 2016 Server",
  "deviceCost" : 4
}
```

#### Delete an existing device

```http
  DELETE /customers/{customerId}/devices/{deviceId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. deviceId of item to fetch |
| `id`      | `long` | **Required**. customerId of item to fetch |

### Services

#### Get all services assigned to a customer

```http
  GET /customers/{customerId}/services
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. customerId of item to fetch |

#### Create a service and assign to a customer

```http
  POST /customers/{customerId}/services
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`      | `long` | **Required**. customerId of item to fetch |

Request Body:

```json
{
  "serviceName" : "Antivirus-Windows",
  "serviceDescription": "To have antivirus in their devices.",
  "serviceCost": 7
}
```


#### Update an existing service by id

```http
  PUT /customers/{customerId}/services/{serviceId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. serviceId of item to fetch |
| `id`      | `long` | **Required**. customerId of item to fetch |

Request Body:

```json
{
  "serviceName": "Antivirus-Macintosh",
  "serviceDescription": "To have antivirus in their devices.",
  "serviceCost": 11
}
```

#### Delete an existing service

```http
  DELETE /customers/{customerId}/services/{serviceId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. serviceId of item to fetch |
| `id`      | `long` | **Required**. customerId of item to fetch |


### Costs

#### Get monthly cost by a customer

```http
  GET /customers/{customerId}/cost
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. customerId of item to fetch |




## Authors

- [@eddiemartinezjoffre](https://github.com/eddiemartinezjoffre/rmm-services-server-app)


