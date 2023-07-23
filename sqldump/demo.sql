--
-- PostgreSQL database dump
--

-- Dumped from database version 14.8 (Ubuntu 14.8-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 15.3

-- Started on 2023-07-23 20:07:52

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

DROP DATABASE boobank;
--
-- TOC entry 3333 (class 1262 OID 98304)
-- Name: boobank; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE boobank WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C.UTF-8';


\connect boobank

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

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

-- *not* creating schema, since initdb creates it


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 98306)
-- Name: customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.customers (
    id bigint NOT NULL,
    balance real,
    email character varying(255),
    firstname character varying(255),
    is_active boolean,
    lastname character varying(255),
    password character varying(255),
    username character varying(255)
);


--
-- TOC entry 209 (class 1259 OID 98305)
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3334 (class 0 OID 0)
-- Dependencies: 209
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;


--
-- TOC entry 212 (class 1259 OID 98315)
-- Name: transactions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.transactions (
    id uuid NOT NULL,
    amount real NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    comment text,
    customer_id integer NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 98314)
-- Name: transactions_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.transactions_customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3335 (class 0 OID 0)
-- Dependencies: 211
-- Name: transactions_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.transactions_customer_id_seq OWNED BY public.transactions.customer_id;


--
-- TOC entry 3174 (class 2604 OID 98309)
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);


--
-- TOC entry 3175 (class 2604 OID 98318)
-- Name: transactions customer_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.transactions ALTER COLUMN customer_id SET DEFAULT nextval('public.transactions_customer_id_seq'::regclass);


--
-- TOC entry 3325 (class 0 OID 98306)
-- Dependencies: 210
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.customers (id, balance, email, firstname, is_active, lastname, password, username) VALUES (1, 1000, 'john@doe.com', 'John', true, 'Doe', '1234', 'johndoe');


--
-- TOC entry 3327 (class 0 OID 98315)
-- Dependencies: 212
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b677620000', 100000, '2023-07-23 20:05:20.704', 'Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b677970001', -40, '2023-07-24 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b677b60002', -20, '2023-07-25 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b677cf0003', -100, '2023-07-26 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b677ef0004', 25, '2023-07-27 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b677fd0005', 5000, '2023-07-28 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678280006', 5, '2023-07-29 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b6783a0007', 10, '2023-07-30 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678410008', -10000, '2023-07-31 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b6784c0009', 35, '2023-08-01 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b67854000a', 5000, '2023-08-02 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b6786d000b', -70, '2023-08-03 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b67878000c', 600, '2023-08-04 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b6788b000d', 45, '2023-08-05 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b67893000e', 10, '2023-08-06 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b6789a000f', -8000, '2023-08-07 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b6789e0010', 15, '2023-08-08 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678a30011', 200, '2023-08-09 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678a80012', 80, '2023-08-10 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678ae0013', 30, '2023-08-11 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678b30014', 200, '2023-08-12 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678b80015', 30, '2023-08-13 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678bd0016', -200, '2023-08-14 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678c30017', 400, '2023-08-15 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678c90018', -30, '2023-08-16 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678ce0019', -8000, '2023-08-17 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678d3001a', -30, '2023-08-18 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678d8001b', 70, '2023-08-19 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678dd001c', 45, '2023-08-20 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678e2001d', 10, '2023-08-21 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678e7001e', 45, '2023-08-22 00:00:00', 'Custom Init', 1);
INSERT INTO public.transactions (id, amount, created_at, comment, customer_id) VALUES ('7f000101-8983-166c-8189-83b678ed001f', -9000, '2023-08-23 00:00:00', 'Custom Init', 1);


--
-- TOC entry 3336 (class 0 OID 0)
-- Dependencies: 209
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.customers_id_seq', 1, true);


--
-- TOC entry 3337 (class 0 OID 0)
-- Dependencies: 211
-- Name: transactions_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.transactions_customer_id_seq', 1, false);


--
-- TOC entry 3177 (class 2606 OID 98313)
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- TOC entry 3183 (class 2606 OID 98322)
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);


--
-- TOC entry 3179 (class 2606 OID 98326)
-- Name: customers uk_bepynu3b6l8k2ppuq6b33xfxc; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT uk_bepynu3b6l8k2ppuq6b33xfxc UNIQUE (username);


--
-- TOC entry 3181 (class 2606 OID 98324)
-- Name: customers uk_rfbvkrffamfql7cjmen8v976v; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT uk_rfbvkrffamfql7cjmen8v976v UNIQUE (email);


--
-- TOC entry 3184 (class 2606 OID 98327)
-- Name: transactions fkpnnreq9lpejqyjfct60v7n7x1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT fkpnnreq9lpejqyjfct60v7n7x1 FOREIGN KEY (customer_id) REFERENCES public.customers(id);


-- Completed on 2023-07-23 20:07:52

--
-- PostgreSQL database dump complete
--

