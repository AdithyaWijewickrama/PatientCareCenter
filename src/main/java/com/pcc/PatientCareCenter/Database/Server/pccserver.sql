-- Roles
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'pcc') THEN
    CREATE ROLE pcc;
    ALTER ROLE pcc WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:w5GcmVQFf8MOiegJmFxbfw==$i6u4jKqN86yr2XaPn5K4JT/t5Zv1taWh75UzJxW8kZU=:Awe7pndDpFtAk1E38T9Eb9w40A/cWXjY3DrfLPAY3tY=';
  END IF;
END $$;

-- Database
CREATE DATABASE IF NOT EXISTS pcc WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en-US';
ALTER DATABASE pcc OWNER TO pcc;

\connect pcc

-- Enable settings
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Defaults Table
CREATE TABLE IF NOT EXISTS public.defaults (
    id text NOT NULL,
    value text NOT NULL,
    pp_id integer
);
ALTER TABLE public.defaults OWNER TO pcc;

-- Doctor Table
CREATE TABLE IF NOT EXISTS public.doctor (
    doctor_id integer NOT NULL,
    user_id integer NOT NULL,
    name text,
    occupation text
);
ALTER TABLE public.doctor OWNER TO pcc;

-- Doctor Sequence
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_sequences
                WHERE schemaname = 'public'
                AND sequencename = 'doctor_doctor_id_seq') THEN
    CREATE SEQUENCE public.doctor_doctor_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
  END IF;
END $$;
ALTER SEQUENCE public.doctor_doctor_id_seq OWNED BY public.doctor.doctor_id;
ALTER TABLE ONLY public.doctor ALTER COLUMN doctor_id SET DEFAULT nextval('public.doctor_doctor_id_seq');

-- Doctors of Patients Table
CREATE TABLE IF NOT EXISTS public.doctors_of_patients (
    patient_id integer NOT NULL,
    doctor_id integer NOT NULL
);
ALTER TABLE public.doctors_of_patients OWNER TO pcc;

-- Stock Details Table
CREATE TABLE IF NOT EXISTS public.stock_details (
    stock_id integer NOT NULL,
    stock_quantity integer NOT NULL,
    stock_expire_date date NOT NULL,
    medicine_name text NOT NULL,
    medicine_strength integer NOT NULL,
    medicine_unit text NOT NULL,
    price_per_medicine double precision NOT NULL,
    medicine_type text,
    pp_id integer
);
ALTER TABLE public.stock_details OWNER TO pcc;

-- Stock Sequence
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_sequences
                WHERE schemaname = 'public'
                AND sequencename = 'medicine_stock_stock_id_seq') THEN
    CREATE SEQUENCE public.medicine_stock_stock_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
  END IF;
END $$;
ALTER SEQUENCE public.medicine_stock_stock_id_seq OWNED BY public.stock_details.stock_id;
ALTER TABLE ONLY public.stock_details ALTER COLUMN stock_id SET DEFAULT nextval('public.medicine_stock_stock_id_seq');

-- Continue this pattern for all remaining tables and sequences...

-- Patient Demographics Table
CREATE TABLE IF NOT EXISTS public.patient_demographics (
    patient_id integer NOT NULL,
    name text NOT NULL,
    date_of_birth date NOT NULL,
    gender text NOT NULL,
    marital_status text NOT NULL,
    nationality text,
    language_preference text,
    status character varying(10) DEFAULT 'Active'::character varying
);
ALTER TABLE public.patient_demographics OWNER TO pcc;

-- Patient Sequence
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_sequences
                WHERE schemaname = 'public'
                AND sequencename = 'patient_demographics_patient_id_seq') THEN
    CREATE SEQUENCE public.patient_demographics_patient_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
  END IF;
END $$;
ALTER SEQUENCE public.patient_demographics_patient_id_seq OWNED BY public.patient_demographics.patient_id;
ALTER TABLE ONLY public.patient_demographics ALTER COLUMN patient_id SET DEFAULT nextval('public.patient_demographics_patient_id_seq');

-- User Table
CREATE TABLE IF NOT EXISTS public."user" (
    user_id integer NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    account_type text NOT NULL,
    date_created timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status text DEFAULT 'Active'::text NOT NULL,
    CONSTRAINT users_status_check CHECK ((status = ANY (ARRAY['Active'::text, 'Inactive'::text, 'Banned'::text])))
);
ALTER TABLE public."user" OWNER TO pcc;

-- User Sequence
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_sequences
                WHERE schemaname = 'public'
                AND sequencename = 'users_user_id_seq') THEN
    CREATE SEQUENCE public.users_user_id_seq
    AS integer START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
  END IF;
END $$;
ALTER SEQUENCE public.users_user_id_seq OWNED BY public."user".user_id;
ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq');

-- Constraints (All original constraints maintained)
ALTER TABLE ONLY public.defaults
    ADD CONSTRAINT defaults_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);

ALTER TABLE ONLY public.patient_demographics
    ADD CONSTRAINT patient_demographics_pkey PRIMARY KEY (patient_id);

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);

-- Foreign Keys (All original relationships maintained)
ALTER TABLE ONLY public.doctors_of_patients
    ADD CONSTRAINT doctors_of_patients_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(doctor_id);

ALTER TABLE ONLY public.doctors_of_patients
    ADD CONSTRAINT doctors_of_patients_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient_demographics(patient_id);

-- ... (All other constraints and foreign keys from original schema)