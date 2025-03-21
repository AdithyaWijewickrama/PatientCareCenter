--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-03-21 23:29:15

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

--
-- TOC entry 242 (class 1259 OID 24613)
-- Name: defaults; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.defaults (
    id text NOT NULL,
    value text NOT NULL,
    pp_id integer
);


ALTER TABLE public.defaults OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17031)
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    doctor_id integer NOT NULL,
    user_id integer NOT NULL,
    name text,
    occupation text
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17030)
-- Name: doctor_doctor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.doctor_doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.doctor_doctor_id_seq OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 219
-- Name: doctor_doctor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.doctor_doctor_id_seq OWNED BY public.doctor.doctor_id;


--
-- TOC entry 223 (class 1259 OID 17058)
-- Name: doctors_of_patients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctors_of_patients (
    patient_id integer NOT NULL,
    doctor_id integer NOT NULL
);


ALTER TABLE public.doctors_of_patients OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17148)
-- Name: stock_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock_details (
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


ALTER TABLE public.stock_details OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 17147)
-- Name: medicine_stock_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicine_stock_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medicine_stock_stock_id_seq OWNER TO postgres;

--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 232
-- Name: medicine_stock_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicine_stock_stock_id_seq OWNED BY public.stock_details.stock_id;


--
-- TOC entry 235 (class 1259 OID 17155)
-- Name: medicines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicines (
    medicine_id integer NOT NULL,
    medicine_name text,
    medicine_dose integer,
    medicine_dose_mesurement character varying(10)
);


ALTER TABLE public.medicines OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 17154)
-- Name: medicines_medicine_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicines_medicine_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medicines_medicine_id_seq OWNER TO postgres;

--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 234
-- Name: medicines_medicine_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicines_medicine_id_seq OWNED BY public.medicines.medicine_id;


--
-- TOC entry 224 (class 1259 OID 17071)
-- Name: patient_allergies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_allergies (
    patient_id integer NOT NULL,
    allergy text NOT NULL,
    description text
);


ALTER TABLE public.patient_allergies OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17081)
-- Name: patient_chronic_conditions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_chronic_conditions (
    patient_id integer NOT NULL,
    disease_type integer NOT NULL,
    disease text NOT NULL,
    description text
);


ALTER TABLE public.patient_chronic_conditions OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 17171)
-- Name: patient_contact_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_contact_details (
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


ALTER TABLE public.patient_contact_details OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17045)
-- Name: patient_demographics; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_demographics (
    patient_id integer NOT NULL,
    name text NOT NULL,
    date_of_birth date NOT NULL,
    gender text NOT NULL,
    marital_status text NOT NULL,
    nationality text,
    language_preference text,
    status character varying(10) DEFAULT 'Active'::character varying
);


ALTER TABLE public.patient_demographics OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17044)
-- Name: patient_demographics_patient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.patient_demographics_patient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.patient_demographics_patient_id_seq OWNER TO postgres;

--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 221
-- Name: patient_demographics_patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.patient_demographics_patient_id_seq OWNED BY public.patient_demographics.patient_id;


--
-- TOC entry 227 (class 1259 OID 17092)
-- Name: patient_surgeries; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient_surgeries (
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


ALTER TABLE public.patient_surgeries OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17091)
-- Name: patient_surgeries_surgery_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.patient_surgeries_surgery_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.patient_surgeries_surgery_id_seq OWNER TO postgres;

--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 226
-- Name: patient_surgeries_surgery_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.patient_surgeries_surgery_id_seq OWNED BY public.patient_surgeries.surgery_id;


--
-- TOC entry 239 (class 1259 OID 24577)
-- Name: pp_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pp_details (
    pp_id integer NOT NULL,
    name character varying(200),
    address text,
    email text,
    telephone text,
    doctor_id integer
);


ALTER TABLE public.pp_details OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 24576)
-- Name: pp_details_pp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pp_details_pp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pp_details_pp_id_seq OWNER TO postgres;

--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 238
-- Name: pp_details_pp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pp_details_pp_id_seq OWNED BY public.pp_details.pp_id;


--
-- TOC entry 241 (class 1259 OID 24605)
-- Name: prescription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescription (
    id integer NOT NULL,
    description text,
    patient_id integer,
    date date
);


ALTER TABLE public.prescription OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 24604)
-- Name: prescription_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prescription_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.prescription_id_seq OWNER TO postgres;

--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 240
-- Name: prescription_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prescription_id_seq OWNED BY public.prescription.id;


--
-- TOC entry 236 (class 1259 OID 17163)
-- Name: search_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.search_config (
    id text,
    value boolean
);


ALTER TABLE public.search_config OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17106)
-- Name: surgeries; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.surgeries (
    surgery_id integer NOT NULL,
    surgery_type text NOT NULL,
    surgery text NOT NULL
);


ALTER TABLE public.surgeries OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17105)
-- Name: surgeries_surgery_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.surgeries_surgery_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.surgeries_surgery_id_seq OWNER TO postgres;

--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 228
-- Name: surgeries_surgery_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.surgeries_surgery_id_seq OWNED BY public.surgeries.surgery_id;


--
-- TOC entry 218 (class 1259 OID 16390)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id integer NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    account_type text NOT NULL,
    date_created timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status text DEFAULT 'Active'::text NOT NULL,
    CONSTRAINT users_status_check CHECK ((status = ANY (ARRAY['Active'::text, 'Inactive'::text, 'Banned'::text])))
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 17114)
-- Name: user_contact_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_contact_details (
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


ALTER TABLE public.user_contact_details OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17124)
-- Name: user_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_images (
    user_id integer NOT NULL,
    profile_image bytea
);


ALTER TABLE public.user_images OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16389)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 4945 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public."user".user_id;


--
-- TOC entry 4716 (class 2604 OID 17034)
-- Name: doctor doctor_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor ALTER COLUMN doctor_id SET DEFAULT nextval('public.doctor_doctor_id_seq'::regclass);


--
-- TOC entry 4722 (class 2604 OID 17158)
-- Name: medicines medicine_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicines ALTER COLUMN medicine_id SET DEFAULT nextval('public.medicines_medicine_id_seq'::regclass);


--
-- TOC entry 4717 (class 2604 OID 17048)
-- Name: patient_demographics patient_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_demographics ALTER COLUMN patient_id SET DEFAULT nextval('public.patient_demographics_patient_id_seq'::regclass);


--
-- TOC entry 4719 (class 2604 OID 17095)
-- Name: patient_surgeries surgery_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_surgeries ALTER COLUMN surgery_id SET DEFAULT nextval('public.patient_surgeries_surgery_id_seq'::regclass);


--
-- TOC entry 4723 (class 2604 OID 24580)
-- Name: pp_details pp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pp_details ALTER COLUMN pp_id SET DEFAULT nextval('public.pp_details_pp_id_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 24608)
-- Name: prescription id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription ALTER COLUMN id SET DEFAULT nextval('public.prescription_id_seq'::regclass);


--
-- TOC entry 4721 (class 2604 OID 17151)
-- Name: stock_details stock_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_details ALTER COLUMN stock_id SET DEFAULT nextval('public.medicine_stock_stock_id_seq'::regclass);


--
-- TOC entry 4720 (class 2604 OID 17109)
-- Name: surgeries surgery_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.surgeries ALTER COLUMN surgery_id SET DEFAULT nextval('public.surgeries_surgery_id_seq'::regclass);


--
-- TOC entry 4713 (class 2604 OID 16393)
-- Name: user user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 4931 (class 0 OID 24613)
-- Dependencies: 242
-- Data for Name: defaults; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.defaults (id, value, pp_id) FROM stdin;
DB_URL	jdbc:postgresql://localhost:5432/pcc	1
DB_USERNAME	postgre	1
DB_PASSWORD	Password	1
WEBHOOK_URL	https://discord.com/api/webhooks/1352480180035125340/F2-nPfg77KuUcWzkelJu7T3FWxGhcfjh7Tbel26vTygblymCB4TYrkb6sx8idLXBEpDf	1
\.


--
-- TOC entry 4909 (class 0 OID 17031)
-- Dependencies: 220
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor (doctor_id, user_id, name, occupation) FROM stdin;
1	1	Dr. Ananda Madawalage	Doctor(MBBS)
2	2	Adithya wijewickrama	Doctor
\.


--
-- TOC entry 4912 (class 0 OID 17058)
-- Dependencies: 223
-- Data for Name: doctors_of_patients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctors_of_patients (patient_id, doctor_id) FROM stdin;
2	1
3	1
4	1
5	1
7	1
9	1
19	1
10	1
15	1
35	1
\.


--
-- TOC entry 4924 (class 0 OID 17155)
-- Dependencies: 235
-- Data for Name: medicines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicines (medicine_id, medicine_name, medicine_dose, medicine_dose_mesurement) FROM stdin;
\.


--
-- TOC entry 4913 (class 0 OID 17071)
-- Dependencies: 224
-- Data for Name: patient_allergies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient_allergies (patient_id, allergy, description) FROM stdin;
\.


--
-- TOC entry 4914 (class 0 OID 17081)
-- Dependencies: 225
-- Data for Name: patient_chronic_conditions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient_chronic_conditions (patient_id, disease_type, disease, description) FROM stdin;
\.


--
-- TOC entry 4926 (class 0 OID 17171)
-- Dependencies: 237
-- Data for Name: patient_contact_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient_contact_details (patient_id, mobile_number, whatsapp_number, lan_number, street_address, country, province, city, postal_code) FROM stdin;
1	771234567	771234567	112345678	123 Galle Road	Sri Lanka	Western	Colombo	00100
3	773456789	773456789	112345680	789 Negombo Road	Sri Lanka	Western	Negombo	11500
4	774567890	774567890	112345681	321 Matara Road	Sri Lanka	Southern	Matara	81000
5	771234567	771234567	112345678	123 Galle Road	Sri Lanka	Western	Colombo	00100
6	776789012	776789012	112345683	987 Anuradhapura Road	Sri Lanka	North Central	Anuradhapura	50000
7	777890123	777890123	112345684	147 Gampaha Road	Sri Lanka	Western	Gampaha	11000
8	778901234	778901234	112345685	258 Kurunegala Road	Sri Lanka	North Western	Kurunegala	60000
9	779012345	779012345	112345686	369 Batticaloa Road	Sri Lanka	Eastern	Batticaloa	30000
10	770123456	770123456	112345687	753 Ratnapura Road	Sri Lanka	Sabaragamuwa	Ratnapura	70000
11	771234567	771234567	112345688	852 Badulla Road	Sri Lanka	Uva	Badulla	90000
12	772345678	772345678	112345689	963 Trincomalee Road	Sri Lanka	Eastern	Trincomalee	31000
13	773456789	773456789	112345690	159 Nuwara Eliya Road	Sri Lanka	Central	Nuwara Eliya	22200
14	774567890	774567890	112345691	753 Polonnaruwa Road	Sri Lanka	North Central	Polonnaruwa	51000
15	775678901	775678901	112345692	852 Hambantota Road	Sri Lanka	Southern	Hambantota	82000
16	776789012	776789012	112345693	963 Puttalam Road	Sri Lanka	North Western	Puttalam	61300
17	777890123	777890123	112345694	159 Kalutara Road	Sri Lanka	Western	Kalutara	12000
18	778901234	778901234	112345695	753 Monaragala Road	Sri Lanka	Uva	Monaragala	91000
19	779012345	779012345	112345696	852 Ampara Road	Sri Lanka	Eastern	Ampara	32000
20	770123456	770123456	112345697	963 Kegalle Road	Sri Lanka	Sabaragamuwa	Kegalle	71000
25				dgf	\N	\N	\N	
26	?	?	?	?	?	?	?	?
27	?	?	?	?	?	?	?	?
28	?	?	?	?	?	?	?	?
29					\N	\N	\N	
30	?	?	?	?	?	?	?	?
30	?	?	?	?	?	?	?	?
31					\N	\N	\N	
32					\N	\N	\N	
33					\N	\N	\N	
34	safsadf		safdsaf	fasdfaa	Sri lanka	Eastern Province	Trincomalee	asfd
35					\N	\N	\N	
2	772345678	772345678	112345679	456 Kandy Roa	Sri Lanka	Central	Kandy	20000
\.


--
-- TOC entry 4911 (class 0 OID 17045)
-- Dependencies: 222
-- Data for Name: patient_demographics; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient_demographics (patient_id, name, date_of_birth, gender, marital_status, nationality, language_preference, status) FROM stdin;
6	Dinesh Gamage	1988-12-10	Male	Married	Sri Lankan	Sinhala	Active
8	Ranjith Herath	1975-08-14	Male	Married	Sri Lankan	Sinhala	Active
9	Chamari Athapaththu	1989-02-28	Female	Single	Sri Lankan	Sinhala	Active
11	Nayana Kumari	1993-10-07	Female	Single	Sri Lankan	Sinhala	Active
12	Asela Gunaratne	1986-01-20	Male	Married	Sri Lankan	Sinhala	Active
13	Dilini Perera	1991-03-03	Female	Single	Sri Lankan	English	Active
14	Roshan Mahanama	1979-07-19	Male	Married	Sri Lankan	Sinhala	Active
15	Tharindu Mendis	1994-09-08	Male	Single	Sri Lankan	Sinhala	Active
16	Ishara Fernando	1987-04-22	Female	Married	Sri Lankan	Tamil	Active
17	Chathurika Jayasuriya	1996-12-15	Female	Single	Sri Lankan	Sinhala	Active
18	Nuwan Kulasekara	1983-06-30	Male	Married	Sri Lankan	Sinhala	Active
19	Sanduni Abeywickrama	1997-08-25	Female	Single	Sri Lankan	English	Active
20	Lasith Malinga	1981-11-28	Male	Married	Sri Lankan	Sinhala	Active
1	Ananda Madawalage	1990-05-15	Male	Single	Sri Lankan	English	Active
3	Sunil Fernand	1978-11-05	Male	Unmarried	Sri Lankan	Sinhala	Active
10	Lakmal Weerasinghe	1980-05-12	Male	Married	Sri Lankan	Tamil	Active
21		2000-02-03					Active
4	Anoma Rajapaksa	1982-09-30	Female	Divorced	Sri Lankan	Sinhala	Active
5	John Do	1990-05-15	Male	Unmarried	Sri Lankan	English	Inactive
7	Samantha Jayawarde	1992-06-25	Male	Unmarried	\N	\N	Inactive
25	dfghfdh	2025-03-21	Male	Unmarried	Sri Lankan	Sinhala	Active
26	?	2020-02-03	?	?	?	?	Active
27	?	2020-02-03	?	?	?	?	Active
28	?	2020-02-03	?	?	?	?	Active
29	sfa	2025-03-21	Male	Unmarried	\N	\N	Active
30	?	2020-02-03	?	?	?	?	Active
31	wrewer	2025-03-21	Male	Unmarried	\N	\N	Active
32	wr	2025-03-21	Male	Unmarried	\N	\N	Active
33	satfsf	2025-03-21	Male	Unmarried	\N	\N	Active
34	satfsf	2025-03-21	Male	Unmarried	Sri Lankan	Sinhala	Active
2	Adithya Wijewickrama	2004-06-03	Male	Unmarried	\N	\N	Inactive
35	fdsfs	2025-03-21	Male	Unmarried	\N	\N	Inactive
\.


--
-- TOC entry 4916 (class 0 OID 17092)
-- Dependencies: 227
-- Data for Name: patient_surgeries; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient_surgeries (surgery_id, patient_id, surgery_type, surgery_date, surgeon_name, anesthesia_type, surgery_description, complications, outcome, follow_up_date) FROM stdin;
\.


--
-- TOC entry 4928 (class 0 OID 24577)
-- Dependencies: 239
-- Data for Name: pp_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pp_details (pp_id, name, address, email, telephone, doctor_id) FROM stdin;
1	PRAVEEN PATIENT CARE CENTER	No.1080/1, Trincomalee stret, Aluwiharaya, Matale	anandamadawalage@gmail.com	0777236818/0718290821	1
4	ADITHYA PATIENT CARE CENTER	adithyawije34@gmail.com	\N	\N	4
\.


--
-- TOC entry 4930 (class 0 OID 24605)
-- Dependencies: 241
-- Data for Name: prescription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prescription (id, description, patient_id, date) FROM stdin;
1	"Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\n\nAmoxline 2mg\tFrequency: 2\t- Days: 6 |\nAmoxicillin 250mg\tFrequency: 4\t- Weeks: 1 |\nTotal:\tRs. 781.00"\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 2\t- Days: 6 |\nAmoxicillin 250mg\tFrequency: 4\t- Weeks: 1 |\nDescription\nThis patient is sick	3	2025-03-21
2	"Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\n\nAmoxline 2mg\tFrequency: 2\t- Days: 6 |\nAmoxicillin 250mg\tFrequency: 4\t- Weeks: 1 |\nTotal:\tRs. 781.00"\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 2\t- Days: 6 |\nAmoxicillin 250mg\tFrequency: 4\t- Weeks: 1 |\nDescription\nThis patient is sick	3	2025-03-21
3	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\n\nAmoxline 2mg\tFrequency: 3\t- Days: 3 |\nTotal:\tRs. 45.00\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 3\t- Days: 3 |\nDescription\n	3	2025-03-21
4	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\n\nTotal:\tRs. 0.00\nUnavailable medicines:\n\nDescription\n	3	2025-03-21
5	Name:\tAnoma Rajapaksa\nAge:\t42 years\nAdd from our stock:\n\nAmoxline 2mg\tFrequency: 3\t- Days: 3 |\nTotal:\tRs. 45.00\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 3\t- Days: 3 |\nDescription\n	4	2025-03-21
6	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\n\nAmoxline 2mg\tFrequency: 5\t- Days: 4 |\nTotal:\tRs. 100.00\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 5\t- Days: 4 |\nDescription\n	3	2025-03-21
7	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\nAmoxline 2mg\tFrequency: 3\t- Days: 3 |\nTotal:\tRs. 45.00\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 3\t- Days: 3 |\nDescription\n	3	2025-03-21
8	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:\nAmoxline 2mg\tFrequency: 3\tDays: 3\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 90.00\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 3\tDays: 3\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription\n	3	2025-03-21
9	Name:\tAnoma Rajapaksa\nAge:\t42 years\nAdd from our stock:\nAmoxline 2mg\tFrequency: 3\tDays: 3\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 90.00\nUnavailable medicines:\n\nAmoxline 2mg\tFrequency: 3\tDays: 3\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription\n	4	2025-03-21
10	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	3	2025-03-21
11	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	3	2025-03-21
12	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	3	2025-03-21
13	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
14	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
15	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
16	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
17	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
18	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
19	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
20	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
21	Name:\tAnoma Rajapaksa\nAge:\t42 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	4	2025-03-21
22	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
23	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
24	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
25	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
26	Name:\tAnoma Rajapaksa\nAge:\t42 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	4	2025-03-21
27	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
28	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
29	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:-----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
30	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:-----------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
31	Name:\tChamari Athapaththu\nAge:\t36 years\nAdd from our stock:-----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	9	2025-03-21
32	Name:\tChamari Athapaththu\nAge:\t36 years\nAdd from our stock:-----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	9	2025-03-21
33	Name:\tChamari Athapaththu\nAge:\t36 years\nAdd from our stock:-----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	9	2025-03-21
34	Name:\tTharindu Mendis\nAge:\t30 years\nAdd from our stock:-----------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	15	2025-03-21
35	Name:\tLakmal Weerasinghe\nAge:\t44 years\nAdd from our stock:-------------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	10	2025-03-21
36	Name:\tSunil Fernand\nAge:\t46 years\nAdd from our stock:-------------------------------\nTotal:\tRs. 0.00\nUnavailable medicines:-------------------------\nDescription------------------------------------	3	2025-03-21
37	Name:\tLakmal Weerasinghe\nAge:\t44 years\nAdd from our stock:-------------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	10	2025-03-21
38	Name:\tLakmal Weerasinghe\nAge:\t44 years\nAdd from our stock:-------------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nTotal:\tRs. 45.00\nUnavailable medicines:-------------------------\nAmoxline 2mg\tFrequency: 3\tDays: 3\nDescription------------------------------------	10	2025-03-21
39	-------------------------------------------Name:\tLakmal Weerasinghe\nAge:\t44 years\nAdd from our stock:-------------------------------\nAmoxline 2mg\t1\tbd for \nAmoxicillin 250mg\t1\tbd for \t1 weeks\nTotal:\tRs. 360.50-------------------------------------------\nUnavailable medicines:-------------------------\nAmoxline 2mg\t1\tbd for \nAmoxicillin 250mg\t1\tbd for \t1 weeks\nDescription------------------------------------	10	2025-03-21
40	-------------------------------------------\nName:\tSanduni Abeywickrama\nAge:\t27 years\nAdd from our stock:-------------------------------\nAmoxline 2mg\tbd for \t5 days\nAmoxicillin 250mg\tbd for \t6 daysPrice for medicines:\tRs. 359.00\nConsultant fee:\t0.00\n-------------------------------------------\nUnavailable medicines:-------------------------\nAmoxline 2mg\tbd for \t5 days\nAmoxicillin 250mg\tbd for \t6 days\nDescription------------------------------------	19	2025-03-21
41	-----------------------------------------------\nName:\tSanduni Abeywickrama\nAge:\t27 years\nAdd from our stock:-------------------------------\nAmoxline 2mg\tbd for \t6 days\nAmoxicillin 250mg\tbd for \t5 days\nPrice for medicines:\tRs. 317.50\nConsultant fee:\t0.00\nTotal:\tRs. 317.50\n-----------------------------------------------\nUnavailable medicines:-------------------------\nAmoxline 2mg\tbd for \t6 days\nAmoxicillin 250mg\tbd for \t5 days\nDescription------------------------------------	19	2025-03-21
42	-----------------------------------------------\nName:\tChamari Athapaththu\nAge:\t36 years\nAdd from our stock:-------------------------------\nAmoxicillin 250mg\tbd for \t2 months\nPrice for medicines:\tRs. 3090.00\nConsultant fee:\t0.00\nTotal:\tRs. 3090.00\n-----------------------------------------------\nUnavailable medicines:-------------------------\nAmoxicillin 250mg\tbd for \t2 months\nDescription------------------------------------	9	2025-03-21
43	-----------------------------------------------\nName:\tChamari Athapaththu\nAge:\t36 years\nAdd from our stock:-------------------------------\nAmoxicillin 250mg\tbd for \t2 months\nPrice for medicines:\tRs. 3090.00\nConsultant fee:\t0.00\nTotal:\tRs. 3090.00\n-----------------------------------------------\nUnavailable medicines:-------------------------\nAmoxicillin 250mg\tbd for \t2 months\nDescription------------------------------------	9	2025-03-21
44	-----------------------------------------------\nName:\tTharindu Mendis\nAge:\t30 years\nAdd from our stock:-------------------------------\nAmoxicillin 250mg\tbd for \t4 months\nPrice for medicines:\tRs. 6180.00\nConsultant fee:\t0.00\nTotal:\tRs. 6180.00\n-----------------------------------------------\nUnavailable medicines:-------------------------\nAmoxicillin 250mg\tbd for \t4 months\nDescription------------------------------------	15	2025-03-21
45	-----------------------------------------------\nName:\tAnoma Rajapaksa\nAge:\t42 years\nAdd from our stock:----------------------------\nAmoxicillin 250mg\tbd for \t4 days\nPrice for medicines:\tRs. 206.00\nConsultant fee:\t0.00\nTotal:\tRs. 206.00\n-----------------------------------------------\nUnavailable medicines:-------------------------\nAmoxicillin 250mg\tbd for \t4 days\nDescription------------------------------------	4	2025-03-21
\.


--
-- TOC entry 4925 (class 0 OID 17163)
-- Dependencies: 236
-- Data for Name: search_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.search_config (id, value) FROM stdin;
id	t
name	t
dateOfBirth	t
mobileNumber	f
whatsappNumber	f
address	f
gender	t
maritalStatus	t
nationality	t
languagePreference	f
city	f
\.


--
-- TOC entry 4922 (class 0 OID 17148)
-- Dependencies: 233
-- Data for Name: stock_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stock_details (stock_id, stock_quantity, stock_expire_date, medicine_name, medicine_strength, medicine_unit, price_per_medicine, medicine_type, pp_id) FROM stdin;
9	-562	2024-08-12	Aspirin	81	mg	7	Tablet	1
2	438	2025-06-30	Amoxicillin	250	mg	25.75	Tablet	1
4	-552	2025-03-20	Metformin	500	mg	15	Tablet	1
6	438	2025-03-20	Penadol	5	mg	5	Tablet	1
7	-557	2024-09-25	Atorvastatin	10	mg	30	Tablet	1
3	-341	2026-03-12	Amoxline	2	mg	5	Tablet	1
1	976	2024-12-31	Paracetamol	500	mg	10.5	Tablet	1
10	4000	2029-03-21	Amoxicillin	67	mg	0.24	Tablet	1
\.


--
-- TOC entry 4918 (class 0 OID 17106)
-- Dependencies: 229
-- Data for Name: surgeries; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.surgeries (surgery_id, surgery_type, surgery) FROM stdin;
1	Cardiovascular	Coronary Artery Bypass Grafting (CABG)
2	Cardiovascular	Heart Valve Repair/Replacement
3	Cardiovascular	Angioplasty
4	Cardiovascular	Pacemaker Implantation
5	Cardiovascular	Aneurysm Repair
6	Orthopedic	Hip Replacement
7	Orthopedic	Knee Replacement
8	Orthopedic	Spinal Fusion
9	Orthopedic	Arthroscopy
10	Orthopedic	Fracture Repair
11	Neurological	Craniotomy
12	Neurological	Brain Tumor Removal
13	Neurological	Deep Brain Stimulation
14	Neurological	Spinal Cord Surgery
15	Neurological	Shunt Placement
16	Gastrointestinal	Appendectomy
17	Gastrointestinal	Cholecystectomy (Gallbladder Removal)
18	Gastrointestinal	Hernia Repair
19	Gastrointestinal	Gastric Bypass
20	Gastrointestinal	Colectomy
21	Urological	Prostatectomy
22	Urological	Nephrectomy (Kidney Removal)
23	Urological	Cystectomy (Bladder Removal)
24	Urological	Ureteral Stent Placement
25	Urological	Vasectomy
26	Gynecological	Hysterectomy
27	Gynecological	Cesarean Section (C-Section)
28	Gynecological	Oophorectomy (Ovary Removal)
29	Gynecological	Tubal Ligation
30	Gynecological	Endometrial Ablation
31	ENT	Tonsillectomy
32	ENT	Adenoidectomy
33	ENT	Rhinoplasty (Nose Surgery)
34	ENT	Septoplasty
35	ENT	Tympanoplasty (Eardrum Repair)
36	Ophthalmic	Cataract Surgery
37	Ophthalmic	LASIK (Laser Eye Surgery)
38	Ophthalmic	Glaucoma Surgery
39	Ophthalmic	Retinal Detachment Repair
40	Ophthalmic	Corneal Transplant
41	Plastic and Reconstructive	Breast Augmentation
42	Plastic and Reconstructive	Liposuction
43	Plastic and Reconstructive	Rhinoplasty
44	Plastic and Reconstructive	Facelift
45	Plastic and Reconstructive	Skin Grafting
46	Transplant	Kidney Transplant
47	Transplant	Liver Transplant
48	Transplant	Heart Transplant
49	Transplant	Lung Transplant
50	Transplant	Bone Marrow Transplant
51	Emergency	Appendectomy (for ruptured appendix)
52	Emergency	Laparotomy (for abdominal trauma)
53	Emergency	Craniotomy (for brain hemorrhage)
54	Emergency	Splenectomy (for ruptured spleen)
55	Emergency	Exploratory Surgery (for internal bleeding)
\.


--
-- TOC entry 4907 (class 0 OID 16390)
-- Dependencies: 218
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (user_id, email, password, account_type, date_created, status) FROM stdin;
1	amadawalage@gmail.com	1234	Doctor	2020-01-15 10:00:00	Active
2	adithyawije34@gmail.com	1234	Doctor	2025-03-21 00:00:00	Active
\.


--
-- TOC entry 4919 (class 0 OID 17114)
-- Dependencies: 230
-- Data for Name: user_contact_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_contact_details (user_id, mobile_number, whatsapp_number, lan_number, street_address, country, province, city, postal_code) FROM stdin;
1	771234567	771234567	112345678	123 Galle Road	Sri Lanka	Western	Colombo	00100
\.


--
-- TOC entry 4920 (class 0 OID 17124)
-- Dependencies: 231
-- Data for Name: user_images; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_images (user_id, profile_image) FROM stdin;
\.


--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 219
-- Name: doctor_doctor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctor_doctor_id_seq', 4, true);


--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 232
-- Name: medicine_stock_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicine_stock_stock_id_seq', 10, true);


--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 234
-- Name: medicines_medicine_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicines_medicine_id_seq', 1, false);


--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 221
-- Name: patient_demographics_patient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.patient_demographics_patient_id_seq', 35, true);


--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 226
-- Name: patient_surgeries_surgery_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.patient_surgeries_surgery_id_seq', 1, false);


--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 238
-- Name: pp_details_pp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pp_details_pp_id_seq', 4, true);


--
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 240
-- Name: prescription_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prescription_id_seq', 45, true);


--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 228
-- Name: surgeries_surgery_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.surgeries_surgery_id_seq', 1, false);


--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 26, true);


--
-- TOC entry 4754 (class 2606 OID 24619)
-- Name: defaults defaults_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.defaults
    ADD CONSTRAINT defaults_pkey PRIMARY KEY (id);


--
-- TOC entry 4732 (class 2606 OID 17038)
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);


--
-- TOC entry 4742 (class 2606 OID 17153)
-- Name: stock_details medicine_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_details
    ADD CONSTRAINT medicine_stock_pkey PRIMARY KEY (stock_id);


--
-- TOC entry 4744 (class 2606 OID 17162)
-- Name: medicines medicines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicines
    ADD CONSTRAINT medicines_pkey PRIMARY KEY (medicine_id);


--
-- TOC entry 4734 (class 2606 OID 17052)
-- Name: patient_demographics patient_demographics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_demographics
    ADD CONSTRAINT patient_demographics_pkey PRIMARY KEY (patient_id);


--
-- TOC entry 4736 (class 2606 OID 17099)
-- Name: patient_surgeries patient_surgeries_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_surgeries
    ADD CONSTRAINT patient_surgeries_pkey PRIMARY KEY (surgery_id);


--
-- TOC entry 4748 (class 2606 OID 24584)
-- Name: pp_details pp_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pp_details
    ADD CONSTRAINT pp_details_pkey PRIMARY KEY (pp_id);


--
-- TOC entry 4752 (class 2606 OID 24612)
-- Name: prescription prescription_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_pkey PRIMARY KEY (id);


--
-- TOC entry 4746 (class 2606 OID 17169)
-- Name: search_config search_config_column_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_config
    ADD CONSTRAINT search_config_column_key UNIQUE (id);


--
-- TOC entry 4738 (class 2606 OID 17113)
-- Name: surgeries surgeries_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.surgeries
    ADD CONSTRAINT surgeries_pkey PRIMARY KEY (surgery_id);


--
-- TOC entry 4750 (class 2606 OID 24586)
-- Name: pp_details unique_name; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pp_details
    ADD CONSTRAINT unique_name UNIQUE (name);


--
-- TOC entry 4740 (class 2606 OID 17130)
-- Name: user_images user_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_images
    ADD CONSTRAINT user_images_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4725 (class 2606 OID 16918)
-- Name: user user_type_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public."user"
    ADD CONSTRAINT user_type_check CHECK ((account_type = ANY (ARRAY['Doctor'::text, 'Patient'::text]))) NOT VALID;


--
-- TOC entry 4728 (class 2606 OID 16403)
-- Name: user users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4730 (class 2606 OID 16401)
-- Name: user users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4755 (class 2606 OID 17061)
-- Name: doctors_of_patients doctors_of_patients_doctor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors_of_patients
    ADD CONSTRAINT doctors_of_patients_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.doctor(doctor_id);


--
-- TOC entry 4756 (class 2606 OID 17066)
-- Name: doctors_of_patients doctors_of_patients_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctors_of_patients
    ADD CONSTRAINT doctors_of_patients_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient_demographics(patient_id);


--
-- TOC entry 4757 (class 2606 OID 17076)
-- Name: patient_allergies patient_allergies_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_allergies
    ADD CONSTRAINT patient_allergies_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient_demographics(patient_id);


--
-- TOC entry 4758 (class 2606 OID 17086)
-- Name: patient_chronic_conditions patient_chronic_conditions_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_chronic_conditions
    ADD CONSTRAINT patient_chronic_conditions_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient_demographics(patient_id);


--
-- TOC entry 4760 (class 2606 OID 17176)
-- Name: patient_contact_details patient_contact_details_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_contact_details
    ADD CONSTRAINT patient_contact_details_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient_demographics(patient_id);


--
-- TOC entry 4759 (class 2606 OID 17100)
-- Name: patient_surgeries patient_surgeries_patient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient_surgeries
    ADD CONSTRAINT patient_surgeries_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.patient_demographics(patient_id);


-- Completed on 2025-03-21 23:29:16

--
-- PostgreSQL database dump complete
--

