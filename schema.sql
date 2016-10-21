CREATE ROLE test WITH PASSWORD 'test';
ALTER ROLE test WITH LOGIN;

CREATE DATABASE test OWNER test;
CREATE DATABASE AU OWNER test;
CREATE DATABASE DE OWNER test;

\c test

CREATE SCHEMA au;
ALTER SCHEMA au OWNER TO test;
GRANT USAGE ON SCHEMA au TO public;

CREATE SCHEMA de;
ALTER SCHEMA de OWNER TO test;
GRANT USAGE ON SCHEMA de TO public;

SET SCHEMA 'au';

CREATE TABLE address (
    id bigint NOT NULL,
    building character varying(255),
    city character varying(255),
    street character varying(255)
);

ALTER TABLE address OWNER TO test;

CREATE TABLE company (
    id bigint NOT NULL,
    name character varying(255)
);

ALTER TABLE company OWNER TO test;

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE hibernate_sequence OWNER TO test;

CREATE TABLE passport (
    id bigint NOT NULL,
    issuedate date,
    no character varying(255),
    series character varying(255),
    validity bytea
);

ALTER TABLE passport OWNER TO test;

CREATE TABLE person (
    id bigint NOT NULL,
    dob date,
    firstname character varying(255),
    lastname character varying(255),
    passport_id bigint NOT NULL,
    person_id bigint NOT NULL
);

ALTER TABLE person OWNER TO test;

CREATE TABLE person_companies (
    person_id bigint NOT NULL,
    company_id bigint NOT NULL
);

ALTER TABLE person_companies OWNER TO test;

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);

ALTER TABLE ONLY company
    ADD CONSTRAINT company_pkey PRIMARY KEY (id);

ALTER TABLE ONLY passport
    ADD CONSTRAINT passport_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person
    ADD CONSTRAINT uk_33yyiniy3o7irjyb5nbmidt4u UNIQUE (passport_id);

ALTER TABLE ONLY person_companies
    ADD CONSTRAINT fk2xnkexg6vm6l0346lru8r6qaa FOREIGN KEY (company_id) REFERENCES company(id);

ALTER TABLE ONLY person
    ADD CONSTRAINT fk701a8b9b2kw01q32ws1rc42bp FOREIGN KEY (person_id) REFERENCES address(id);

ALTER TABLE ONLY person_companies
    ADD CONSTRAINT fkj1do3s4ycpku0fpmm3pinysau FOREIGN KEY (person_id) REFERENCES person(id);

ALTER TABLE ONLY person
    ADD CONSTRAINT fkn3sbguup9nkxgiqxgfc0sigxj FOREIGN KEY (passport_id) REFERENCES passport(id);

SET SCHEMA 'de';

CREATE TABLE address (
    id bigint NOT NULL,
    building character varying(255),
    city character varying(255),
    street character varying(255)
);

ALTER TABLE address OWNER TO test;

CREATE TABLE company (
    id bigint NOT NULL,
    name character varying(255)
);

ALTER TABLE company OWNER TO test;

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE hibernate_sequence OWNER TO test;

CREATE TABLE passport (
    id bigint NOT NULL,
    issuedate date,
    no character varying(255),
    series character varying(255),
    validity bytea
);

ALTER TABLE passport OWNER TO test;

CREATE TABLE person (
    id bigint NOT NULL,
    dob date,
    firstname character varying(255),
    lastname character varying(255),
    passport_id bigint NOT NULL,
    person_id bigint NOT NULL
);

ALTER TABLE person OWNER TO test;

CREATE TABLE person_companies (
    person_id bigint NOT NULL,
    company_id bigint NOT NULL
);

ALTER TABLE person_companies OWNER TO test;

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);

ALTER TABLE ONLY company
    ADD CONSTRAINT company_pkey PRIMARY KEY (id);

ALTER TABLE ONLY passport
    ADD CONSTRAINT passport_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person
    ADD CONSTRAINT uk_33yyiniy3o7irjyb5nbmidt4u UNIQUE (passport_id);

ALTER TABLE ONLY person_companies
    ADD CONSTRAINT fk2xnkexg6vm6l0346lru8r6qaa FOREIGN KEY (company_id) REFERENCES company(id);

ALTER TABLE ONLY person
    ADD CONSTRAINT fk701a8b9b2kw01q32ws1rc42bp FOREIGN KEY (person_id) REFERENCES address(id);

ALTER TABLE ONLY person_companies
    ADD CONSTRAINT fkj1do3s4ycpku0fpmm3pinysau FOREIGN KEY (person_id) REFERENCES person(id);

ALTER TABLE ONLY person
    ADD CONSTRAINT fkn3sbguup9nkxgiqxgfc0sigxj FOREIGN KEY (passport_id) REFERENCES passport(id);
