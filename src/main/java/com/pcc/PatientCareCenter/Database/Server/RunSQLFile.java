package com.pcc.PatientCareCenter.Database.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RunSQLFile {
    public static void runSQLFile(Connection connection, String sqlFilePath) throws IOException, SQLException {
        File file = new File(sqlFilePath);
        StringBuilder sql = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
        }
        connection.prepareStatement("""
                -- Create the database if it doesn't exist
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_catalog.pg_database WHERE datname = 'pcc') THEN
                        CREATE DATABASE pcc
                            WITH
                            OWNER = postgres
                            ENCODING = 'UTF8'
                            LC_COLLATE = 'C'
                            LC_CTYPE = 'C'
                            LOCALE_PROVIDER = 'libc'
                            TABLESPACE = pg_default
                            CONNECTION LIMIT = -1
                            IS_TEMPLATE = False;
                    END IF;
                END $$;
                
                -- Switch to the created database
                
                -- Table Creation
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'defaults') THEN
                        CREATE TABLE public.defaults (
                            id SERIAL PRIMARY KEY,
                            column_name VARCHAR(255) NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'doctor') THEN
                        CREATE TABLE public.doctor (
                            doctor_id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'stock_details') THEN
                        CREATE TABLE public.stock_details (
                            stock_id SERIAL PRIMARY KEY,
                            medicine_id INT NOT NULL,
                            quantity INT NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'medicines') THEN
                        CREATE TABLE public.medicines (
                            medicine_id SERIAL PRIMARY KEY,
                            medicine_name VARCHAR(255) NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'patient_demographics') THEN
                        CREATE TABLE public.patient_demographics (
                            patient_id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'patient_surgeries') THEN
                        CREATE TABLE public.patient_surgeries (
                            surgery_id SERIAL PRIMARY KEY,
                            patient_id INT,
                            surgery_details VARCHAR(255)
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'pp_details') THEN
                        CREATE TABLE public.pp_details (
                            pp_id SERIAL PRIMARY KEY,
                            name VARCHAR(255) UNIQUE NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'prescription') THEN
                        CREATE TABLE public.prescription (
                            id SERIAL PRIMARY KEY,
                            medicine_id INT NOT NULL,
                            patient_id INT NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'surgeries') THEN
                        CREATE TABLE public.surgeries (
                            surgery_id SERIAL PRIMARY KEY,
                            surgery_name VARCHAR(255) NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'user_images') THEN
                        CREATE TABLE public.user_images (
                            user_id SERIAL PRIMARY KEY,
                            image_path VARCHAR(255) NOT NULL
                        );
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'user') THEN
                        CREATE TABLE public."user" (
                            user_id SERIAL PRIMARY KEY,
                            email VARCHAR(255) UNIQUE NOT NULL,
                            account_type VARCHAR(50) CHECK(account_type IN ('Doctor', 'Patient'))
                        );
                    END IF;
                END $$;
                
                -- Foreign Key Constraints
                -- Primary key constraints
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'defaults_pkey') THEN
                        ALTER TABLE ONLY public.defaults
                            ADD CONSTRAINT defaults_pkey PRIMARY KEY (id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'doctor_pkey') THEN
                        ALTER TABLE ONLY public.doctor
                            ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'medicine_stock_pkey') THEN
                        ALTER TABLE ONLY public.stock_details
                            ADD CONSTRAINT medicine_stock_pkey PRIMARY KEY (stock_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'medicines_pkey') THEN
                        ALTER TABLE ONLY public.medicines
                            ADD CONSTRAINT medicines_pkey PRIMARY KEY (medicine_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'patient_demographics_pkey') THEN
                        ALTER TABLE ONLY public.patient_demographics
                            ADD CONSTRAINT patient_demographics_pkey PRIMARY KEY (patient_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'patient_surgeries_pkey') THEN
                        ALTER TABLE ONLY public.patient_surgeries
                            ADD CONSTRAINT patient_surgeries_pkey PRIMARY KEY (surgery_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'pp_details_pkey') THEN
                        ALTER TABLE ONLY public.pp_details
                            ADD CONSTRAINT pp_details_pkey PRIMARY KEY (pp_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'prescription_pkey') THEN
                        ALTER TABLE ONLY public.prescription
                            ADD CONSTRAINT prescription_pkey PRIMARY KEY (id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'surgeries_pkey') THEN
                        ALTER TABLE ONLY public.surgeries
                            ADD CONSTRAINT surgeries_pkey PRIMARY KEY (surgery_id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'user_images_pkey') THEN
                        ALTER TABLE ONLY public.user_images
                            ADD CONSTRAINT user_images_pkey PRIMARY KEY (user_id);
                    END IF;
                END $$;
                
                -- Unique constraints
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'search_config_column_key') THEN
                        ALTER TABLE ONLY public.search_config
                            ADD CONSTRAINT search_config_column_key UNIQUE (id);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'unique_name') THEN
                        ALTER TABLE ONLY public.pp_details
                            ADD CONSTRAINT unique_name UNIQUE (name);
                    END IF;
                END $$;
                
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'users_email_key') THEN
                        ALTER TABLE ONLY public."user"
                            ADD CONSTRAINT users_email_key UNIQUE (email);
                    END IF;
                END $$;
                
                -- Check constraints
                DO $$
                BEGIN
                    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'user_type_check') THEN
                        ALTER TABLE public."user"
                            ADD CONSTRAINT user_type_check CHECK ((account_type = ANY (ARRAY['Doctor'::text, 'Patient'::text]))) NOT VALID;
                    END IF;
                END $$;
                
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
                
                """).executeUpdate();
    }
}
