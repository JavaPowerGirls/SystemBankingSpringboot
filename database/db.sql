DROP DATABASE IF EXISTS BankDB;

CREATE DATABASE BankDB;

USE BankDB;

CREATE TABLE clients (
    client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    document_id VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    CONSTRAINT chk_email CHECK (email LIKE '%_@_%._%')
);

CREATE TABLE bank_accounts (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DOUBLE NOT NULL DEFAULT 0.0,
    account_type VARCHAR(20) NOT NULL,
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE
);


INSERT INTO clients (first_name, last_name, document_id, email)
VALUES ('John', 'Suarez', '87654321', 'john@gmail.com');

INSERT INTO clients (first_name, last_name, document_id, email)
VALUES ('Mary', 'Ramirez', '85692314', 'mary@gmail.com');


INSERT INTO bank_accounts (client_id, account_number, account_type)
VALUES (1, 'ACC1001', 'SAVINGS');

INSERT INTO bank_accounts (client_id, account_number, account_type)
VALUES (2, 'ACC2001', 'CHECKING');

-- Un cliente puede tener múltiples cuentas
INSERT INTO bank_accounts (client_id, account_number, account_type)
VALUES (1, 'ACC1002', 'CHECKING');


-- ver todos los clientes
SELECT * FROM clients;

-- ver todas las cuentas
SELECT * FROM bank_accounts;

-- ver clientes y sus cuentas
SELECT c.first_name, c.last_name, c.document_id, c.email,
       b.account_number, b.account_type, b.balance
FROM clients c
JOIN bank_accounts b ON c.client_id = b.client_id;



-- Depositar en la cuenta de un usuario 
UPDATE bank_accounts
SET balance = balance + 500.00
WHERE account_number = 'ACC1001';

-- retirar dinero de la cuenta de un usuario
UPDATE bank_accounts
SET balance = balance - 300.00
WHERE account_number = 'ACC2001';

-- actualizar correo del cliente
UPDATE clients
SET email = 'new.email@email.com'
WHERE document_id = '87654321';


-- ver todos los clientes
SELECT * FROM clients;

-- ver todas las cuentas
SELECT * FROM bank_accounts;

-- ver clientes y sus cuentas
SELECT c.first_name, c.last_name, c.document_id, c.email,
       b.account_number, b.account_type, b.balance
FROM clients c
JOIN bank_accounts b ON c.client_id = b.client_id;



-- Depositar en la cuenta de un usuario 
UPDATE bank_accounts
SET balance = balance + 500.00
WHERE account_number = 'ACC1001';

-- retirar dinero de la cuenta de un usuario
UPDATE bank_accounts
SET balance = balance - 300.00
WHERE account_number = 'ACC2001';

-- actualizar correo del cliente
UPDATE clients
SET email = 'new.email@email.com'
WHERE document_id = '87654321';