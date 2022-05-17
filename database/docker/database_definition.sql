-- SCHEMA DEFINITION
CREATE SCHEMA IF NOT EXISTS A2B;
SET SEARCH_PATH TO A2B;

-- USERS TABLE DEFINITION
CREATE TABLE users
(
    id                    bigserial             NOT NULL,
    first_name            varchar(50)           NOT NULL,
    last_name             varchar(50)           NOT NULL,
    email                 varchar(50)           NOT NULL,
    phone                 varchar(50)           NOT NULL,

    CONSTRAINT "pk_domain_user.user" PRIMARY KEY (id)
);

CREATE UNIQUE INDEX "domain_user.email_index" ON users (email);
CREATE UNIQUE INDEX "domain_user.phone_index" ON users (email);

-- COUNTRIES TABLE DEFINITION
CREATE TABLE countries
(
    id                    bigserial             NOT NULL,
    name                  varchar(50)           NOT NULL,
    code                  varchar(10)           NOT NULL,

    CONSTRAINT "pk_domain_country.country" PRIMARY KEY (id)
);

-- CITIES TABLE DEFINITION
CREATE TABLE cities
(
    id                    bigserial             NOT NULL,
    country_id            bigint                NOT NULL,
    name                  varchar(100)          NOT NULL,
    code                  varchar(10)           NOT NULL,

    CONSTRAINT "pk_domain_city.city" PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE
);

-- LOCATIONS TABLE DEFINITION
CREATE TABLE locations
(
    id                    bigserial             NOT NULL,
    city_id               bigint                NOT NULL,
    location              jsonb                 NOT NULL,

    CONSTRAINT "pk_domain_location.location" PRIMARY KEY (id),
    FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE
);

-- TRANSFERS TABLE DEFINITION
CREATE TABLE transfers
(
    id                   bigserial             NOT NULL,
    origin               bigint                NOT NULL,
    destination          bigint                NOT NULL,
    capacity             smallint              NOT NULL,
    date                 date                  NOT NULL,
    duration             tsrange               NOT NULL,
    price                money                 NOT NULL,
    description          text                  NOT NULL,

    CONSTRAINT "pk_domain_transfer.transfer" PRIMARY KEY (id),
    FOREIGN KEY (origin) REFERENCES locations(id) ON DELETE CASCADE,
    FOREIGN KEY (destination) REFERENCES locations(id) ON DELETE CASCADE
);

-- USERS_TRANSFERS TABLE DEFINITION
CREATE TABLE users_transfers
(
    user_id              bigint                NOT NULL,
    transfer_id          bigint                NOT NULL,
    state                varchar(10)           NOT NULL,
    description          text                  NOT NULL,

    CONSTRAINT "pk_domain_users_transfers.users_transfers" PRIMARY KEY (user_id, transfer_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (transfer_id) REFERENCES transfers(id) ON DELETE CASCADE
);