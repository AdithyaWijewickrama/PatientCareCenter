package com.pcc.PatientCareCenter.Database.Server;

import com.pcc.PatientCareCenter.App;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RunSQLFile {
    public static void runSQLFile(Connection connection) throws SQLException {
        connection.prepareStatement("""
                SET statement_timeout = 0;
                SET lock_timeout = 0;
                SET idle_in_transaction_session_timeout = 0;
                SET transaction_timeout = 0;
                SET client_encoding = 'UTF8';
                SET standard_conforming_strings = on;
                SELECT pg_catalog.set_config('search_path', '', false);
                SET check_function_bodies = false;
                SET xmloption = content;
                SET client_min_messages = warning;
                SET row_security = off;
                
                SET default_tablespace = '';
                SET default_table_access_method = heap;
                
                CREATE TABLE IF NOT EXISTS public.defaults (
                    id text NOT NULL,
                    value text NOT NULL,
                    pp_id integer
                );
                ALTER TABLE public.defaults OWNER TO pcc;
                
                CREATE TABLE IF NOT EXISTS public.doctor (
                    doctor_id integer NOT NULL,
                    user_id integer NOT NULL,
                    name text,
                    occupation text
                );
                ALTER TABLE public.doctor OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.doctor_doctor_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.doctor_doctor_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.doctor_doctor_id_seq OWNED BY public.doctor.doctor_id;
                
                CREATE TABLE IF NOT EXISTS public.doctors_of_patients (
                    patient_id integer NOT NULL,
                    doctor_id integer NOT NULL
                );
                ALTER TABLE public.doctors_of_patients OWNER TO pcc;
                
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
                
                CREATE SEQUENCE IF NOT EXISTS public.medicine_stock_stock_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.medicine_stock_stock_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.medicine_stock_stock_id_seq OWNED BY public.stock_details.stock_id;
                
                CREATE TABLE IF NOT EXISTS public.medicines (
                    medicine_id integer NOT NULL,
                    medicine_name text,
                    medicine_dose integer,
                    medicine_dose_mesurement character varying(10)
                );
                ALTER TABLE public.medicines OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.medicines_medicine_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.medicines_medicine_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.medicines_medicine_id_seq OWNED BY public.medicines.medicine_id;
                
                CREATE TABLE IF NOT EXISTS public.patient_allergies (
                    patient_id integer NOT NULL,
                    allergy text NOT NULL,
                    description text
                );
                ALTER TABLE public.patient_allergies OWNER TO pcc;
                
                CREATE TABLE IF NOT EXISTS public.patient_chronic_conditions (
                    patient_id integer NOT NULL,
                    disease_type integer NOT NULL,
                    disease text NOT NULL,
                    description text
                );
                ALTER TABLE public.patient_chronic_conditions OWNER TO pcc;
                
                CREATE TABLE IF NOT EXISTS public.patient_contact_details (
                    patient_id integer NOT NULL,
                    mobile_number character varying(20),
                    whatsapp_number character varying(20),
                    lan_number character varying(20),
                    street_address text,
                    country text,
                    province text,
                    city text,
                    postal_code text
                );
                ALTER TABLE public.patient_contact_details OWNER TO pcc;
                
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
                
                CREATE SEQUENCE IF NOT EXISTS public.patient_demographics_patient_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.patient_demographics_patient_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.patient_demographics_patient_id_seq OWNED BY public.patient_demographics.patient_id;
                
                CREATE TABLE IF NOT EXISTS public.patient_surgeries (
                    surgery_id integer NOT NULL,
                    patient_id integer,
                    surgery_type text NOT NULL,
                    surgery_date date NOT NULL,
                    surgeon_name text,
                    anesthesia_type text,
                    surgery_description text,
                    complications text,
                    outcome text,
                    follow_up_date date
                );
                ALTER TABLE public.patient_surgeries OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.patient_surgeries_surgery_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.patient_surgeries_surgery_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.patient_surgeries_surgery_id_seq OWNED BY public.patient_surgeries.surgery_id;
                
                CREATE TABLE IF NOT EXISTS public.pp_details (
                    pp_id integer NOT NULL,
                    name character varying(200),
                    address text,
                    email text,
                    telephone text,
                    doctor_id integer
                );
                ALTER TABLE public.pp_details OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.pp_details_pp_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.pp_details_pp_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.pp_details_pp_id_seq OWNED BY public.pp_details.pp_id;
                
                CREATE TABLE IF NOT EXISTS public.prescription (
                    id integer NOT NULL,
                    description text,
                    patient_id integer,
                    date date
                );
                ALTER TABLE public.prescription OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.prescription_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.prescription_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.prescription_id_seq OWNED BY public.prescription.id;
                
                CREATE TABLE IF NOT EXISTS public.search_config (
                    id TEXT NOT NULL UNIQUE,
                    value boolean
                );
                ALTER TABLE public.search_config ADD CONSTRAINT search_config_id_unique UNIQUE (id);
                ALTER TABLE public.search_config OWNER TO pcc;
                
                CREATE TABLE IF NOT EXISTS public.surgeries (
                    surgery_id integer NOT NULL,
                    surgery_type text NOT NULL,
                    surgery text NOT NULL
                );
                ALTER TABLE public.surgeries OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.surgeries_surgery_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.surgeries_surgery_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.surgeries_surgery_id_seq OWNED BY public.surgeries.surgery_id;
                
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
                
                CREATE TABLE IF NOT EXISTS public.user_contact_details (
                    user_id integer NOT NULL,
                    mobile_number character varying(20),
                    whatsapp_number character varying(20),
                    lan_number character varying(20),
                    street_address text,
                    country text,
                    province text,
                    city text,
                    postal_code text
                );
                ALTER TABLE public.user_contact_details OWNER TO pcc;
                
                CREATE TABLE IF NOT EXISTS public.user_images (
                    user_id integer NOT NULL,
                    profile_image bytea
                );
                ALTER TABLE public.user_images OWNER TO pcc;
                
                CREATE SEQUENCE IF NOT EXISTS public.users_user_id_seq
                    AS integer
                    START WITH 1
                    INCREMENT BY 1
                    NO MINVALUE
                    NO MAXVALUE
                    CACHE 1;
                ALTER SEQUENCE public.users_user_id_seq OWNER TO pcc;
                ALTER SEQUENCE public.users_user_id_seq OWNED BY public."user".user_id;
                
                ALTER TABLE ONLY public.doctor ALTER COLUMN doctor_id SET DEFAULT nextval('public.doctor_doctor_id_seq'::regclass);
                ALTER TABLE ONLY public.medicines ALTER COLUMN medicine_id SET DEFAULT nextval('public.medicines_medicine_id_seq'::regclass);
                ALTER TABLE ONLY public.patient_demographics ALTER COLUMN patient_id SET DEFAULT nextval('public.patient_demographics_patient_id_seq'::regclass);
                ALTER TABLE ONLY public.patient_surgeries ALTER COLUMN surgery_id SET DEFAULT nextval('public.patient_surgeries_surgery_id_seq'::regclass);
                ALTER TABLE ONLY public.pp_details ALTER COLUMN pp_id SET DEFAULT nextval('public.pp_details_pp_id_seq'::regclass);
                ALTER TABLE ONLY public.prescription ALTER COLUMN id SET DEFAULT nextval('public.prescription_id_seq'::regclass);
                ALTER TABLE ONLY public.stock_details ALTER COLUMN stock_id SET DEFAULT nextval('public.medicine_stock_stock_id_seq'::regclass);
                ALTER TABLE ONLY public.surgeries ALTER COLUMN surgery_id SET DEFAULT nextval('public.surgeries_surgery_id_seq'::regclass);
                ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);
                
                -- Insert initial data into search_config table
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM public.search_config WHERE id = 'id') THEN
                        INSERT INTO public.search_config(id, value)
                        VALUES
                        ('id', 'true'),
                        ('name', 'true'),
                        ('dateOfBirth', 'true'),
                        ('mobileNumber', 'true'),
                        ('whatsappNumber', 'true'),
                        ('address', 'false'),
                        ('gender', 'true'),
                        ('maritalStatus', 'true'),
                        ('nationality', 'true'),
                        ('languagePreference', 'true'),
                        ('city', 'true');
                    END IF;
                END $$;
                -- Database setup complete!
                """.replaceAll("OWNER TO pcc", "OWNER TO " + App.DB_USERNAME)).execute();
    }
}
