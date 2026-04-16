-- ================================================================
--  Script MySQL — examen2_guevara
--
--  Estrategia ORM: JOINED TABLE  (InheritanceType.JOINED)
--  Una tabla por clase → sin repetición de columnas → normalizado.
--
--  Mapeo clase → tabla:
--  ┌────────────────────────────────────────────────────────────┐
--  │  Clase Java               │  Tabla MySQL         │ Relación│
--  ├────────────────────────────────────────────────────────────┤
--  │  Propietario              │  propietario         │   —     │
--  │  Inmueble (abstracta)     │  inmueble            │   —     │
--  │  Apartamento extends      │  inmueble_apartamento│ 1:1 FK  │
--  │  Casa extends             │  inmueble_casa       │ 1:1 FK  │
--  └────────────────────────────────────────────────────────────┘
--
--  Relaciones:
--    inmueble.propietario_id         → propietario.id         (@ManyToOne  * → 1)
--    inmueble_apartamento.numero      → inmueble.numero        (@OneToOne   1 → 1)
--    inmueble_casa.numero             → inmueble.numero        (@OneToOne   1 → 1)
-- ================================================================

CREATE DATABASE IF NOT EXISTS examen2_guevara
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE examen2_guevara;

-- ================================================================
-- TABLA 1 — propietario
-- Clase: Propietario
-- @Entity  @Table(name = "propietario")
-- ================================================================
CREATE TABLE IF NOT EXISTS propietario (

    -- @Id @Column(name = "id")
    id      VARCHAR(50)  NOT NULL  COMMENT '@Id',

    -- @Column(name = "nombre")
    nombre  VARCHAR(100) NOT NULL  COMMENT '@Column',

    PRIMARY KEY (id)

) ENGINE = InnoDB
  COMMENT = '@Entity Propietario';

-- ================================================================
-- TABLA 2 — inmueble
-- Clase: Inmueble (abstracta — @MappedSuperclass con @Inheritance JOINED)
-- Almacena TODOS los campos comunes de la jerarquía (sin repetirlos).
-- @Entity  @Inheritance(strategy = InheritanceType.JOINED)
-- @Table(name = "inmueble")
-- ================================================================
CREATE TABLE IF NOT EXISTS inmueble (

    -- @Id @Column(name = "numero")
    numero          VARCHAR(20)  NOT NULL  COMMENT '@Id — PK compartida con subclases',

    -- @Column(name = "fecha_compra")
    fecha_compra    VARCHAR(20)  NOT NULL  COMMENT '@Column heredado — formato DD/MM/AAAA',

    -- @Column(name = "estado")  true=activo | false=inactivo
    estado          TINYINT(1)   NOT NULL
                        DEFAULT 1 COMMENT '@Column heredado — 1 activo / 0 inactivo',

    -- @ManyToOne  @JoinColumn(name = "propietario_id")
    -- Cardinalidad: N inmuebles → 1 propietario  (diagrama: * → 1)
    propietario_id  VARCHAR(50)  NOT NULL  COMMENT '@ManyToOne FK → propietario.id',

    PRIMARY KEY (numero),

    CONSTRAINT fk_inmueble_propietario
        FOREIGN KEY (propietario_id)
        REFERENCES propietario (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT

) ENGINE = InnoDB
  COMMENT = '@Entity Inmueble — tabla base de la herencia JOINED';

-- ================================================================
-- TABLA 3 — inmueble_apartamento
-- Clase: Apartamento extends Inmueble
-- Solo almacena el campo PROPIO de la subclase (numero_piso).
-- La FK "numero" es PK y a la vez apunta a inmueble.numero (1:1).
--
-- @Entity  @Table(name = "inmueble_apartamento")
-- @PrimaryKeyJoinColumn(name = "numero")
-- ================================================================
CREATE TABLE IF NOT EXISTS inmueble_apartamento (

    -- @PrimaryKeyJoinColumn(name = "numero")
    -- PK de esta tabla = FK @OneToOne → inmueble.numero
    numero       VARCHAR(20) NOT NULL COMMENT '@PrimaryKeyJoinColumn — FK 1:1 → inmueble.numero',

    -- @Column(name = "numero_piso")  campo propio de Apartamento
    numero_piso  INT         NOT NULL COMMENT '@Column propio Apartamento — número de piso',

    PRIMARY KEY (numero),

    CONSTRAINT fk_apartamento_inmueble
        FOREIGN KEY (numero)
        REFERENCES inmueble (numero)
        ON UPDATE CASCADE
        ON DELETE CASCADE

) ENGINE = InnoDB
  COMMENT = '@Entity Apartamento extends Inmueble — JOINED';

-- ================================================================
-- TABLA 4 — inmueble_casa
-- Clase: Casa extends Inmueble
-- Solo almacena el campo PROPIO de la subclase (cantidad_pisos).
-- La FK "numero" es PK y a la vez apunta a inmueble.numero (1:1).
--
-- @Entity  @Table(name = "inmueble_casa")
-- @PrimaryKeyJoinColumn(name = "numero")
-- ================================================================
CREATE TABLE IF NOT EXISTS inmueble_casa (

    -- @PrimaryKeyJoinColumn(name = "numero")
    numero          VARCHAR(20) NOT NULL COMMENT '@PrimaryKeyJoinColumn — FK 1:1 → inmueble.numero',

    -- @Column(name = "cantidad_pisos")  campo propio de Casa
    cantidad_pisos  INT         NOT NULL COMMENT '@Column propio Casa — cantidad de pisos',

    PRIMARY KEY (numero),

    CONSTRAINT fk_casa_inmueble
        FOREIGN KEY (numero)
        REFERENCES inmueble (numero)
        ON UPDATE CASCADE
        ON DELETE CASCADE

) ENGINE = InnoDB
  COMMENT = '@Entity Casa extends Inmueble — JOINED';

-- ================================================================
-- Datos de prueba (insertar respetando el orden de FKs)
-- ================================================================

INSERT INTO propietario (id, nombre) VALUES
    ('P001', 'Carlos Guevara'),
    ('P002', 'Laura Martinez');

INSERT INTO inmueble (numero, fecha_compra, estado, propietario_id) VALUES
    ('I001', '10/01/2024', 1, 'P001'),
    ('I002', '15/02/2023', 1, 'P001'),
    ('I003', '20/03/2022', 0, 'P002'),
    ('I004', '05/04/2021', 1, 'P002');

INSERT INTO inmueble_apartamento (numero, numero_piso) VALUES
    ('I001', 3),
    ('I003', 8);

INSERT INTO inmueble_casa (numero, cantidad_pisos) VALUES
    ('I002', 2),
    ('I004', 1);