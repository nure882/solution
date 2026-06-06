CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(32) NOT NULL DEFAULT 'CUSTOMER',
    is_banned BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

CREATE TABLE stations (
    station_id UUID PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    work_time VARCHAR(255),
    description VARCHAR(1000),
    equipment_list VARCHAR(1000),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

CREATE INDEX idx_stations_city ON stations(city);

CREATE TABLE gas_types (
    gas_type_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);

CREATE TABLE pumps (
    pump_id UUID PRIMARY KEY,
    number INT NOT NULL,
    gas_count DOUBLE PRECISION NOT NULL DEFAULT 0,
    cost DOUBLE PRECISION NOT NULL DEFAULT 0,
    station_id UUID NOT NULL,
    gas_type_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    CONSTRAINT fk_pumps_station FOREIGN KEY (station_id) REFERENCES stations (station_id) ON DELETE CASCADE,
    CONSTRAINT fk_pumps_gas_type FOREIGN KEY (gas_type_id) REFERENCES gas_types (gas_type_id) ON DELETE RESTRICT
);

CREATE INDEX idx_pumps_station_id ON pumps(station_id);
CREATE INDEX idx_pumps_gas_type_id ON pumps(gas_type_id);

CREATE TABLE coupons (
    coupon_id UUID PRIMARY KEY,
    customer_id UUID,
    gas_type_id UUID NOT NULL,
    liters DOUBLE PRECISION NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT true,
    expiration_date TIMESTAMP WITH TIME ZONE,
    use_date TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    CONSTRAINT fk_coupons_customer FOREIGN KEY (customer_id) REFERENCES users (user_id) ON DELETE SET NULL,
    CONSTRAINT fk_coupons_gas_type FOREIGN KEY (gas_type_id) REFERENCES gas_types (gas_type_id) ON DELETE RESTRICT
);

CREATE INDEX idx_coupons_customer_id ON coupons(customer_id);
