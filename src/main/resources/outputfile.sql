--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4 (Debian 16.4-1.pgdg120+1)
-- Dumped by pg_dump version 16.4 (Debian 16.4-1.pgdg120+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bill (
    bill_id integer NOT NULL,
    bill_date date NOT NULL,
    total_price numeric(10,2) NOT NULL,
    discount_amount numeric(10,2) DEFAULT 0.0,
    tax_amount numeric(10,2) DEFAULT 0.0,
    final_price numeric(10,2) NOT NULL,
    cash_tendered numeric(10,2) NOT NULL,
    change_amount numeric(10,2) NOT NULL,
    customer_id integer
);


ALTER TABLE public.bill OWNER TO postgres;

--
-- Name: bill_bill_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bill_bill_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bill_bill_id_seq OWNER TO postgres;

--
-- Name: bill_bill_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bill_bill_id_seq OWNED BY public.bill.bill_id;


--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    category_id integer NOT NULL,
    category_name character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: category_category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.category_category_id_seq OWNER TO postgres;

--
-- Name: category_category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_category_id_seq OWNED BY public.category.category_id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_id integer NOT NULL,
    name character varying(255) NOT NULL,
    phone_number character varying(10),
    email character varying(255),
    loyalty_points integer DEFAULT 0,
    total_spent numeric(10,2) DEFAULT 0.0,
    last_purchase_date date
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_customer_id_seq OWNER TO postgres;

--
-- Name: customer_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_customer_id_seq OWNED BY public.customer.customer_id;


--
-- Name: customertransactionhistory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customertransactionhistory (
    history_id integer NOT NULL,
    customer_id integer,
    total_purchases integer,
    total_spent numeric(10,2),
    avg_spent_per_purchase numeric(10,2),
    purchase_frequency integer,
    last_purchase_date date,
    data_as_of date NOT NULL
);


ALTER TABLE public.customertransactionhistory OWNER TO postgres;

--
-- Name: customertransactionhistory_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customertransactionhistory_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customertransactionhistory_history_id_seq OWNER TO postgres;

--
-- Name: customertransactionhistory_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customertransactionhistory_history_id_seq OWNED BY public.customertransactionhistory.history_id;


--
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    item_id integer NOT NULL,
    item_code character varying(10) NOT NULL,
    item_name character varying(255) NOT NULL,
    item_price numeric(10,2) NOT NULL,
    category_id integer
);


ALTER TABLE public.item OWNER TO postgres;

--
-- Name: item_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.item_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.item_item_id_seq OWNER TO postgres;

--
-- Name: item_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.item_item_id_seq OWNED BY public.item.item_id;


--
-- Name: loyaltyprogram; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loyaltyprogram (
    loyalty_id integer NOT NULL,
    customer_id integer,
    points_earned integer NOT NULL,
    points_redeemed integer DEFAULT 0,
    date date NOT NULL
);


ALTER TABLE public.loyaltyprogram OWNER TO postgres;

--
-- Name: loyaltyprogram_loyalty_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.loyaltyprogram_loyalty_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.loyaltyprogram_loyalty_id_seq OWNER TO postgres;

--
-- Name: loyaltyprogram_loyalty_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.loyaltyprogram_loyalty_id_seq OWNED BY public.loyaltyprogram.loyalty_id;


--
-- Name: onlineinventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.onlineinventory (
    stock_id integer,
    quantity_in_stock integer NOT NULL,
    last_updated_date date
);


ALTER TABLE public.onlineinventory OWNER TO postgres;

--
-- Name: shelf; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shelf (
    shelf_id integer NOT NULL,
    item_code character varying(10),
    quantity integer NOT NULL,
    moved_date date NOT NULL,
    expiry_date date,
    batch_code character varying(50) NOT NULL
);


ALTER TABLE public.shelf OWNER TO postgres;

--
-- Name: shelf_shelf_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.shelf_shelf_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.shelf_shelf_id_seq OWNER TO postgres;

--
-- Name: shelf_shelf_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.shelf_shelf_id_seq OWNED BY public.shelf.shelf_id;


--
-- Name: stock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock (
    stock_id integer NOT NULL,
    batch_code character varying(50) NOT NULL,
    item_code character varying(10) NOT NULL,
    quantity_in_stock integer NOT NULL,
    reshelf_quantity integer NOT NULL,
    shelf_capacity integer NOT NULL,
    reorder_level integer NOT NULL,
    date_of_purchase date NOT NULL,
    expiry_date date,
    stock_location character varying(255) NOT NULL
);


ALTER TABLE public.stock OWNER TO postgres;

--
-- Name: stock_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.stock_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stock_stock_id_seq OWNER TO postgres;

--
-- Name: stock_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.stock_stock_id_seq OWNED BY public.stock.stock_id;


--
-- Name: storeinventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storeinventory (
    stock_id integer NOT NULL,
    item_code character varying(10),
    quantity_in_stock integer NOT NULL,
    date_of_purchase date NOT NULL,
    expiry_date date
);


ALTER TABLE public.storeinventory OWNER TO postgres;

--
-- Name: storeinventory_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storeinventory_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.storeinventory_stock_id_seq OWNER TO postgres;

--
-- Name: storeinventory_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storeinventory_stock_id_seq OWNED BY public.storeinventory.stock_id;


--
-- Name: transaction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transaction (
    transaction_id integer NOT NULL,
    bill_id integer,
    item_id integer,
    quantity integer NOT NULL,
    total_price numeric(10,2) NOT NULL,
    transaction_date date DEFAULT CURRENT_DATE,
    transaction_type character varying(50) NOT NULL
);


ALTER TABLE public.transaction OWNER TO postgres;

--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transaction_transaction_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.transaction_transaction_id_seq OWNER TO postgres;

--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transaction_transaction_id_seq OWNED BY public.transaction.transaction_id;


--
-- Name: bill bill_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bill ALTER COLUMN bill_id SET DEFAULT nextval('public.bill_bill_id_seq'::regclass);


--
-- Name: category category_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN category_id SET DEFAULT nextval('public.category_category_id_seq'::regclass);


--
-- Name: customer customer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN customer_id SET DEFAULT nextval('public.customer_customer_id_seq'::regclass);


--
-- Name: customertransactionhistory history_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customertransactionhistory ALTER COLUMN history_id SET DEFAULT nextval('public.customertransactionhistory_history_id_seq'::regclass);


--
-- Name: item item_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item ALTER COLUMN item_id SET DEFAULT nextval('public.item_item_id_seq'::regclass);


--
-- Name: loyaltyprogram loyalty_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loyaltyprogram ALTER COLUMN loyalty_id SET DEFAULT nextval('public.loyaltyprogram_loyalty_id_seq'::regclass);


--
-- Name: shelf shelf_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shelf ALTER COLUMN shelf_id SET DEFAULT nextval('public.shelf_shelf_id_seq'::regclass);


--
-- Name: stock stock_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock ALTER COLUMN stock_id SET DEFAULT nextval('public.stock_stock_id_seq'::regclass);


--
-- Name: storeinventory stock_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storeinventory ALTER COLUMN stock_id SET DEFAULT nextval('public.storeinventory_stock_id_seq'::regclass);


--
-- Name: transaction transaction_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction ALTER COLUMN transaction_id SET DEFAULT nextval('public.transaction_transaction_id_seq'::regclass);


--
-- Data for Name: bill; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bill (bill_id, bill_date, total_price, discount_amount, tax_amount, final_price, cash_tendered, change_amount, customer_id) FROM stdin;
24	2024-10-03	1000.00	0.00	100.00	1100.00	1000.00	-100.00	1
1	2024-10-02	1800.00	90.00	0.00	1710.00	2000.00	290.00	1
2	2024-10-02	600.00	0.00	0.00	600.00	1000.00	400.00	6
25	2024-10-03	1000.00	0.00	20.00	1020.00	1000.00	-20.00	3
3	2024-10-02	1200.00	12.00	0.00	1188.00	1500.00	312.00	6
4	2024-10-02	2450.00	0.00	0.00	2450.00	2500.00	50.00	8
5	2024-10-02	0.00	0.00	0.00	0.00	0.00	0.00	3
6	2024-10-02	2100.00	0.00	0.00	2100.00	25000.00	22900.00	1
26	2024-10-03	1350.00	0.00	27.00	1377.00	1500.00	123.00	5
48	2024-10-03	2000.00	100.00	40.00	1940.00	2000.00	60.00	3
7	2024-10-02	3100.00	155.00	0.00	2945.00	5000.00	2055.00	9
8	2024-10-02	300.00	0.00	0.00	300.00	200.00	-100.00	9
27	2024-10-03	2300.00	0.00	46.00	2346.00	2500.00	154.00	4
9	2024-10-02	1300.00	0.00	0.00	1300.00	1500.00	200.00	9
10	2024-10-02	1000.00	0.00	0.00	1000.00	1000.00	0.00	7
28	2024-10-03	700.00	0.00	14.00	714.00	1000.00	286.00	3
11	2024-10-02	2500.00	0.00	0.00	2500.00	2500.00	0.00	5
12	2024-10-02	700.00	0.00	0.00	700.00	1000.00	300.00	9
29	2024-10-03	2400.00	0.00	48.00	2448.00	2500.00	52.00	4
13	2024-10-02	1050.00	42.00	0.00	1008.00	1500.00	492.00	9
49	2024-10-03	11250.00	562.50	225.00	10912.50	15000.00	4087.50	4
30	2024-10-03	500.00	0.00	10.00	510.00	520.00	10.00	6
14	2024-10-02	7450.00	149.00	0.00	7301.00	7500.00	199.00	9
15	2024-10-02	200.00	0.00	0.00	0.00	0.00	0.00	9
16	2024-10-02	400.00	0.00	0.00	400.00	500.00	100.00	9
31	2024-10-03	750.00	0.00	15.00	765.00	700.00	-65.00	3
17	2024-10-02	200.00	0.00	0.00	180.00	500.00	320.00	9
18	2024-10-02	1100.00	0.00	0.00	800.00	1000.00	200.00	4
19	2024-10-03	800.00	0.00	80.00	880.00	1000.00	120.00	6
32	2024-10-03	750.00	0.00	15.00	765.00	800.00	35.00	3
20	2024-10-03	1200.00	0.00	120.00	1320.00	2000.00	680.00	8
33	2024-10-03	0.00	0.00	0.00	0.00	0.00	0.00	3
21	2024-10-03	1200.00	0.00	120.00	1320.00	1100.00	-220.00	8
34	2024-10-03	700.00	0.00	14.00	714.00	500.00	-214.00	4
22	2024-10-03	1300.00	6500.00	130.00	0.00	1500.00	1500.00	5
23	2024-10-03	200.00	0.00	20.00	220.00	250.00	30.00	6
50	2024-10-03	6000.00	300.00	120.00	5820.00	10000.00	4180.00	1
35	2024-10-03	500.00	5000.00	10.00	0.00	500.00	500.00	4
36	2024-10-03	100.00	500.00	2.00	0.00	110.00	110.00	4
51	2024-10-05	1100.00	0.00	22.00	1122.00	1100.00	-22.00	13
37	2024-10-03	350.00	1750.00	7.00	0.00	500.00	500.00	4
52	2024-10-05	800.00	0.00	16.00	705.00	800.00	95.00	13
38	2024-10-03	700.00	3500.00	14.00	0.00	1000.00	1000.00	4
39	2024-10-03	100.00	0.00	2.00	102.00	105.00	3.00	4
53	2024-10-05	1600.00	0.00	32.00	1557.00	1600.00	43.00	13
40	2024-10-03	700.00	3500.00	14.00	0.00	1000.00	1000.00	4
41	2024-10-03	350.00	0.00	7.00	357.00	500.00	143.00	4
54	2024-10-05	800.00	0.00	16.00	659.00	800.00	141.00	13
55	2024-10-05	0.00	0.00	0.00	0.00	0.00	0.00	13
42	2024-10-03	700.00	0.00	14.00	714.00	1000.00	286.00	4
43	2024-10-03	0.00	0.00	0.00	0.00	0.00	0.00	4
44	2024-10-03	700.00	3500.00	14.00	0.00	1000.00	1000.00	4
56	2024-10-05	1500.00	0.00	30.00	1458.00	0.00	-1458.00	13
57	2024-10-05	0.00	0.00	0.00	0.00	0.00	0.00	14
45	2024-10-03	350.00	17.50	7.00	339.50	500.00	160.50	4
46	2024-10-03	1000.00	20.00	20.00	1000.00	1500.00	500.00	12
58	2024-10-05	1600.00	0.00	32.00	1561.00	1600.00	39.00	6
47	2024-10-03	9000.00	450.00	180.00	8730.00	10000.00	1270.00	12
59	2024-10-05	1200.00	0.00	24.00	352.00	0.00	-352.00	12
60	2024-10-05	600.00	0.00	12.00	612.00	600.00	-12.00	11
61	2024-10-05	1500.00	60.00	30.00	1470.00	2000.00	530.00	14
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (category_id, category_name) FROM stdin;
1	Fresh Vegetables
2	Fresh Fruits
3	Canned Foods
4	Sauces
5	Various Groceries
6	Spices & Herbs
7	Oils/Vinegars
8	Refrigerated Items
9	Dairy
10	Cheese
11	Frozen
12	Meat
13	Seafood
14	Baked Goods
15	Baking
16	Snacks
17	Personal Care
18	Medicine
19	Kitchen
20	Cleaning Products
21	Other Stuff
22	Pets
23	Baby
24	Office Supplies
25	Alcohol
26	Themed Meals
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (customer_id, name, phone_number, email, loyalty_points, total_spent, last_purchase_date) FROM stdin;
12	Kamal Addara	4567890123	ka@kamail.ol	77	1200.00	2024-10-05
11	0987654321	0987654321	kasun@gmail	60	600.00	2024-10-05
10	Poli Saman	0997893242	saman@hotmail.io	0	0.00	2024-10-02
3	Kamal Silva	0789876543	amal.silva@example.com	194	3750.25	2024-10-03
14	kamal indika	8615768930	kalam@gmail.com	146	0.00	2024-10-05
7	Nimal	0987654321	nima@gmail	50	1000.00	2024-10-02
4	Nimal Perera	0774567890	nimal.perera@example.com	1090	6250.30	2024-10-03
8	nimal	0987654321	nimal@gmail.com2	66	2450.00	2024-10-02
1	John Doe	0711234567	john.doe@example.com	582	5100.50	2024-10-02
5	Kamal Fernando	0761239876	kamal.fernando@example.com	201	4400.00	2024-10-03
13	sumuditha	0713463026	sumuditha@gmail.com	147	5800.00	2024-10-05
9	kasun ka	1234567890	kasun@kasu	9	14700.00	2024-10-02
6	Nimal	0713456789		158	4400.00	2024-10-05
\.


--
-- Data for Name: customertransactionhistory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customertransactionhistory (history_id, customer_id, total_purchases, total_spent, avg_spent_per_purchase, purchase_frequency, last_purchase_date, data_as_of) FROM stdin;
3	8	6	4800.00	800.00	6	2024-10-03	2024-10-03
8	13	17	12300.00	723.53	17	2024-10-05	2024-10-05
2	6	12	6200.00	516.67	12	2024-10-05	2024-10-05
7	12	11	32400.00	2945.45	11	2024-10-05	2024-10-05
4	5	8	7100.00	887.50	8	2024-10-03	2024-10-03
10	11	3	1200.00	400.00	3	2024-10-05	2024-10-05
9	14	5	4500.00	900.00	5	2024-10-05	2024-10-05
6	3	17	12400.00	729.41	17	2024-10-03	2024-10-03
1	4	57	60600.00	1063.16	57	2024-10-03	2024-10-03
5	1	7	20000.00	2857.14	7	2024-10-03	2024-10-03
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.item (item_id, item_code, item_name, item_price, category_id) FROM stdin;
1	FV001	Asparagus	1000.00	1
2	FV002	Beets	700.00	1
3	FV003	Broccoli	550.00	1
4	FV004	Carrots	250.00	1
6	FF001	Apples	300.00	2
7	FF002	Avocado	400.00	2
8	FF003	Bananas	150.00	2
9	FF004	Berries	650.00	2
10	FF005	Cherries	800.00	2
11	CF001	Applesauce	500.00	3
12	CF002	Baked Beans	350.00	3
13	CF003	Beans	250.00	3
14	CF004	Carrots	300.00	3
15	CF005	Corn	300.00	3
16	SC001	BBQ Sauce	500.00	4
17	SC002	Hot Sauce	350.00	4
18	SC003	Salsa	500.00	4
19	SC004	Soy Sauce	300.00	4
20	SC005	Steak Sauce	600.00	4
21	VG001	Bottled Water	200.00	5
22	VG002	Cereal	400.00	5
23	VG003	Coffee	1200.00	5
24	VG004	Honey	500.00	5
25	VG005	Peanut Butter	450.00	5
26	SH001	Basil	200.00	6
27	SH002	Black Pepper	400.00	6
28	SH003	Cinnamon	300.00	6
29	SH004	Garlic	250.00	6
30	SH005	Oregano	350.00	6
31	OV001	Apple Cider Vinegar	600.00	7
32	OV002	Olive Oil	1200.00	7
33	OV003	Vegetable Oil	800.00	7
34	OV004	White Vinegar	500.00	7
35	OV005	Balsamic Vinegar	900.00	7
36	RI001	Chip Dip	400.00	8
37	RI002	Eggs	250.00	8
38	RI003	Juice	350.00	8
39	RI004	Tofu	500.00	8
40	RI005	Tortillas	200.00	8
41	DR001	Butter	500.00	9
42	DR002	Milk	100.00	9
43	DR003	Sour Cream	400.00	9
44	DR004	Yogurt	300.00	9
45	DR005	Whipped Cream	350.00	9
46	CH001	Cheddar	800.00	10
47	CH002	Cottage Cheese	600.00	10
48	CH003	Cream Cheese	550.00	10
49	CH004	Mozzarella	750.00	10
50	CH005	Swiss	850.00	10
51	FR001	Burritos	300.00	11
52	FR002	Ice Cream	500.00	11
53	FR003	Frozen Pizza	700.00	11
54	FR004	Popsicles	250.00	11
55	FR005	Veggie Burgers	600.00	11
56	MT001	Bacon	900.00	12
57	MT002	Chicken	750.00	12
58	MT003	Beef	1200.00	12
59	MT004	Sausage	650.00	12
60	MT005	Ham	800.00	12
61	SF001	Salmon	1500.00	13
62	SF002	Shrimp	1300.00	13
63	SF003	Tilapia	900.00	13
64	SF004	Tuna	1100.00	13
65	SF005	Crab	1700.00	13
66	BG001	Bagels	300.00	14
67	BG002	Buns	200.00	14
68	BG003	Cookies	250.00	14
69	BG004	Fresh Bread	150.00	14
70	BG005	Pastries	350.00	14
71	BK001	Baking Powder	200.00	15
72	BK002	Brown Sugar	250.00	15
73	BK003	Cake Icing	300.00	15
74	BK004	Cocoa	400.00	15
75	BK005	Flour	200.00	15
76	SN001	Potato Chips	200.00	16
77	SN002	Popcorn	150.00	16
78	SN003	Candy	250.00	16
79	SN004	Pretzels	300.00	16
80	SN005	Granola Bars	400.00	16
81	PC001	Bath Soap	150.00	17
82	PC002	Shampoo	200.00	17
83	PC003	Toothpaste	120.00	17
84	PC004	Facial Tissue	100.00	17
85	PC005	Moisturizing Lotion	350.00	17
86	MD001	Aspirin	150.00	18
87	MD002	Cold Medicine	250.00	18
88	MD003	Antacid	180.00	18
89	MD004	Band-Aids	100.00	18
90	MD005	Vitamins	300.00	18
91	KT001	Aluminum Foil	150.00	19
92	KT002	Dish Soap	100.00	19
93	KT003	Coffee Filters	50.00	19
94	KT004	Napkins	80.00	19
95	KT005	Sponges	70.00	19
96	CP001	Air Freshener	200.00	20
97	CP002	Bleach	150.00	20
98	CP003	Laundry Detergent	500.00	20
99	CP004	Fabric Softener	300.00	20
100	CP005	Mop Head	250.00	20
101	OS001	Batteries	200.00	21
102	OS002	Candles	150.00	21
103	OS003	Fresh Flowers	500.00	21
104	OS004	Greeting Cards	50.00	21
105	OS005	Insect Repellent	350.00	21
106	PT001	Dog Food	400.00	22
107	PT002	Cat Food	350.00	22
108	PT003	Cat Litter	200.00	22
109	PT004	Dog Treats	150.00	22
110	PT005	Pet Shampoo	250.00	22
111	BB001	Diapers	500.00	23
112	BB002	Baby Food	300.00	23
113	BB003	Formula	600.00	23
114	BB004	Baby Wipes	200.00	23
115	BB005	Bottles	150.00	23
116	OSU001	Notepads	100.00	24
117	OSU002	Pens	50.00	24
118	OSU003	Paper	150.00	24
119	OSU004	Envelopes	75.00	24
120	OSU005	Glue	40.00	24
121	AL001	Beer	500.00	25
122	AL002	Champagne	2000.00	25
123	AL003	Red Wine	1500.00	25
124	AL004	Vodka	2500.00	25
125	AL005	Whiskey	3000.00	25
126	TM001	Burger Night Kit	600.00	26
127	TM002	Chili Night Kit	700.00	26
128	TM003	Pizza Night Kit	800.00	26
129	TM004	Spaghetti Night Kit	500.00	26
130	TM005	Taco Night Kit	750.00	26
131	FV005	Celery	310.00	\N
\.


--
-- Data for Name: loyaltyprogram; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loyaltyprogram (loyalty_id, customer_id, points_earned, points_redeemed, date) FROM stdin;
\.


--
-- Data for Name: onlineinventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.onlineinventory (stock_id, quantity_in_stock, last_updated_date) FROM stdin;
1	50	2024-01-02
2	100	2024-01-06
3	150	2024-01-12
4	25	2024-01-17
5	200	2024-01-22
\.


--
-- Data for Name: shelf; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.shelf (shelf_id, item_code, quantity, moved_date, expiry_date, batch_code) FROM stdin;
3	CF003	20	2024-01-11	2024-07-01	BCH003
4	SC004	5	2024-01-16	2024-06-15	BCH004
1	FV001	8	2024-01-02	2024-06-01	BCH001
5	VG005	0	2024-01-21	2024-07-20	BCH005
2	FF002	0	2024-01-06	2024-06-05	BCH002
\.


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stock (stock_id, batch_code, item_code, quantity_in_stock, reshelf_quantity, shelf_capacity, reorder_level, date_of_purchase, expiry_date, stock_location) FROM stdin;
1	BCH001	FV001	100	10	200	50	2024-01-01	2024-06-01	Aisle 1
2	BCH002	FF002	150	15	200	75	2024-01-05	2024-06-05	Aisle 2
3	BCH003	CF003	200	20	300	100	2024-01-10	2024-07-01	Aisle 3
4	BCH004	SC004	50	5	100	25	2024-01-15	2024-06-15	Aisle 4
5	BCH005	VG005	250	25	400	150	2024-01-20	2024-07-20	Aisle 5
\.


--
-- Data for Name: storeinventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.storeinventory (stock_id, item_code, quantity_in_stock, date_of_purchase, expiry_date) FROM stdin;
1	FV001	100	2024-01-01	2024-06-01
2	FF002	150	2024-01-05	2024-06-05
3	CF003	200	2024-01-10	2024-07-01
4	SC004	50	2024-01-15	2024-06-15
5	VG005	250	2024-01-20	2024-07-20
\.


--
-- Data for Name: transaction; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transaction (transaction_id, bill_id, item_id, quantity, total_price, transaction_date, transaction_type) FROM stdin;
1	1	27	2	800.00	2024-10-02	purchase
2	1	1	1	1000.00	2024-10-02	purchase
3	2	44	2	600.00	2024-10-02	purchase
4	3	32	1	1200.00	2024-10-02	purchase
5	4	30	7	2450.00	2024-10-02	cash
6	6	45	6	2100.00	2024-10-02	cash
7	7	38	2	700.00	2024-10-02	cash
8	7	47	4	2400.00	2024-10-02	cash
9	8	51	1	300.00	2024-10-02	cash
10	9	11	2	1000.00	2024-10-02	cash
11	9	19	1	300.00	2024-10-02	cash
12	10	39	2	1000.00	2024-10-02	cash
13	11	18	5	2500.00	2024-10-02	purchase
14	12	38	2	700.00	2024-10-02	cash
15	13	38	3	1050.00	2024-10-02	cash
16	14	38	3	1050.00	2024-10-02	cash
17	14	46	8	6400.00	2024-10-02	cash
18	15	42	2	200.00	2024-10-02	cash
19	16	42	4	400.00	2024-10-02	cash
20	17	42	2	200.00	2024-10-02	cash
21	18	48	2	1100.00	2024-10-02	cash
22	19	22	2	800.00	2024-10-03	purchase
23	20	31	2	1200.00	2024-10-03	cash
24	21	55	2	1200.00	2024-10-03	cash
25	22	16	1	500.00	2024-10-03	cash
26	22	43	2	800.00	2024-10-03	cash
27	23	42	2	200.00	2024-10-03	cash
28	24	1	1	1000.00	2024-10-03	cash
29	25	24	2	1000.00	2024-10-03	cash
30	26	25	3	1350.00	2024-10-03	cash
31	27	73	1	300.00	2024-10-03	cash
32	27	1	2	2000.00	2024-10-03	cash
33	28	70	2	700.00	2024-10-03	cash
34	29	58	2	2400.00	2024-10-03	cash
35	30	16	1	500.00	2024-10-03	cash
36	31	49	1	750.00	2024-10-03	cash
37	32	49	1	750.00	2024-10-03	cash
38	34	30	2	700.00	2024-10-03	cash
39	35	42	5	500.00	2024-10-03	cash
40	36	42	1	100.00	2024-10-03	cash
41	37	30	1	350.00	2024-10-03	cash
42	38	30	2	700.00	2024-10-03	cash
43	39	42	1	100.00	2024-10-03	cash
44	40	30	2	700.00	2024-10-03	cash
45	41	30	1	350.00	2024-10-03	cash
46	42	30	2	700.00	2024-10-03	cash
47	44	30	2	700.00	2024-10-03	cash
48	45	30	1	350.00	2024-10-03	cash
49	46	24	2	1000.00	2024-10-03	cash
50	47	1	9	9000.00	2024-10-03	cash
51	48	1	2	2000.00	2024-10-03	cash
52	49	25	25	11250.00	2024-10-03	cash
53	50	7	15	6000.00	2024-10-03	cash
54	51	45	2	700.00	2024-10-05	cash
55	51	67	2	400.00	2024-10-05	cash
56	52	36	2	800.00	2024-10-05	cash
57	53	46	2	1600.00	2024-10-05	cash
58	54	46	1	800.00	2024-10-05	cash
59	56	57	2	1500.00	2024-10-05	cash
60	58	60	2	1600.00	2024-10-05	cash
61	59	55	2	1200.00	2024-10-05	cash
62	60	51	2	600.00	2024-10-05	cash
63	61	49	2	1500.00	2024-10-05	cash
\.


--
-- Name: bill_bill_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bill_bill_id_seq', 61, true);


--
-- Name: category_category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_category_id_seq', 26, true);


--
-- Name: customer_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_customer_id_seq', 14, true);


--
-- Name: customertransactionhistory_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customertransactionhistory_history_id_seq', 10, true);


--
-- Name: item_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_item_id_seq', 131, true);


--
-- Name: loyaltyprogram_loyalty_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loyaltyprogram_loyalty_id_seq', 1, false);


--
-- Name: shelf_shelf_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.shelf_shelf_id_seq', 5, true);


--
-- Name: stock_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stock_stock_id_seq', 5, true);


--
-- Name: storeinventory_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.storeinventory_stock_id_seq', 5, true);


--
-- Name: transaction_transaction_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transaction_transaction_id_seq', 63, true);


--
-- Name: bill bill_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bill
    ADD CONSTRAINT bill_pkey PRIMARY KEY (bill_id);


--
-- Name: category category_category_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_category_name_key UNIQUE (category_name);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (category_id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- Name: customertransactionhistory customertransactionhistory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customertransactionhistory
    ADD CONSTRAINT customertransactionhistory_pkey PRIMARY KEY (history_id);


--
-- Name: item item_item_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_item_code_key UNIQUE (item_code);


--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (item_id);


--
-- Name: loyaltyprogram loyaltyprogram_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loyaltyprogram
    ADD CONSTRAINT loyaltyprogram_pkey PRIMARY KEY (loyalty_id);


--
-- Name: shelf shelf_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shelf
    ADD CONSTRAINT shelf_pkey PRIMARY KEY (shelf_id);


--
-- Name: stock stock_batch_code_item_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_batch_code_item_code_key UNIQUE (batch_code, item_code);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (stock_id);


--
-- Name: storeinventory storeinventory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storeinventory
    ADD CONSTRAINT storeinventory_pkey PRIMARY KEY (stock_id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id);


--
-- Name: bill bill_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bill
    ADD CONSTRAINT bill_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- Name: customertransactionhistory customertransactionhistory_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customertransactionhistory
    ADD CONSTRAINT customertransactionhistory_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- Name: item item_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(category_id);


--
-- Name: loyaltyprogram loyaltyprogram_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loyaltyprogram
    ADD CONSTRAINT loyaltyprogram_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- Name: onlineinventory onlineinventory_stock_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.onlineinventory
    ADD CONSTRAINT onlineinventory_stock_id_fkey FOREIGN KEY (stock_id) REFERENCES public.stock(stock_id);


--
-- Name: shelf shelf_item_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shelf
    ADD CONSTRAINT shelf_item_code_fkey FOREIGN KEY (item_code) REFERENCES public.item(item_code);


--
-- Name: stock stock_item_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_item_code_fkey FOREIGN KEY (item_code) REFERENCES public.item(item_code);


--
-- Name: storeinventory storeinventory_item_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storeinventory
    ADD CONSTRAINT storeinventory_item_code_fkey FOREIGN KEY (item_code) REFERENCES public.item(item_code);


--
-- Name: transaction transaction_bill_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_bill_id_fkey FOREIGN KEY (bill_id) REFERENCES public.bill(bill_id);


--
-- Name: transaction transaction_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_item_id_fkey FOREIGN KEY (item_id) REFERENCES public.item(item_id);


--
-- PostgreSQL database dump complete
--

