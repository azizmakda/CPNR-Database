DROP TABLE IF EXISTS Thera_Pat;
DROP TABLE IF EXISTS Coverage; 
DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Therapist;
DROP TABLE IF EXISTS Locations;


CREATE TABLE Locations
(
	L_zipCode INT(5) PRIMARY KEY NOT NULL,
	L_city VARCHAR(35) NOT NULL,
	L_county VARCHAR(30) NOT NULL,
	UNIQUE(L_zipCode)
);


CREATE TABLE Patient
(
	P_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	P_fName VARCHAR(30) NOT NULL,
	P_lName VARCHAR(35) NOT NULL,
	P_diagnosis VARCHAR(60) NOT NULL,
	P_insuranceType VARCHAR(50) NOT NULL,
	P_socialSecurityNumber VARCHAR(11) NOT NULL,
	P_address VARCHAR(40) NOT NULL,
	L_zipCode INT(5) NOT NULL,
	P_phone VARCHAR(14) NOT NULL,
	P_email VARCHAR(40),
	P_totalVisits INT NOT NULL,
	P_attemptedVisits INT NOT NULL,
	P_cancelledVisits INT NOT NULL,
	P_therapyStatus BOOLEAN NOT NULL,
	FOREIGN KEY(L_zipCode) references Locations (L_zipCode),
	UNIQUE(P_socialSecurityNumber)
);

CREATE TABLE Therapist
(
	T_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	T_fName VARCHAR(30) NOT NULL,
	T_lName VARCHAR(35) NOT NULL,
	T_therapistType VARCHAR(40) NOT NULL,
	T_licenseNumber INT NOT NULL,
	T_phone VARCHAR(14) NOT NULL,
	T_email VARCHAR(40) NOT NULL,
	T_address VARCHAR(40) NOT NULL,
	L_zipCode INT(5) NOT NULL,
	T_socialSecurityNumber VARCHAR(11) NOT NULL,
	T_bankRoutingNumber INT(9),
	FOREIGN KEY(L_zipCode) references Locations (L_zipCode),
	UNIQUE(T_socialSecurityNumber)

);

CREATE TABLE Thera_Pat
(
	T_id INT NOT NULL,
    P_id INT NOT NULL,
	FOREIGN KEY(T_id) references Therapist (T_id),
	FOREIGN KEY(P_id) references Patient (P_id),
	PRIMARY KEY(P_id, T_id) 
);

CREATE TABLE Coverage
(
	T_id INT NOT NULL,
    L_zipCode INT NOT NULL,
    C_gasCoverage DECIMAL (10,2),
	FOREIGN KEY(T_id) references Therapist (T_id),
	FOREIGN KEY(L_zipCode) references Locations (L_zipCode),
	PRIMARY KEY(T_id, L_zipCode) 
);

INSERT INTO Locations (L_zipCode, L_city, L_county) 
VALUES
(60004, 'Arlington Heights', 'Cook'),
(60005, 'Arlington Heights', 'Cook'),
(60006, 'Arlington Heights', 'Cook'),
(60007, 'Elk Grove Village', 'Cook'),
(60008, 'Rolling Meadows', 'Cook'),
(60009, 'Elk Grove Village', 'Cook'),
(60016, 'Des Plaines', 'Cook'),
(60017, 'Des Plaines', 'Cook'),
(60018, 'Des Plaines', 'Cook'),
(60019, 'Des Plaines', 'Cook'),
(60022, 'Glencoe', 'Cook'),
(60025, 'Glenview', 'Cook'),
(60026, 'Glenview', 'Cook'),
(60029, 'Golf', 'Cook'),
(60038, 'Palatine', 'Cook'),
(60043, 'Kenilworth', 'Cook'),
(60053, 'Morton Grove', 'Cook'),
(60055, 'Palatine', 'Cook'),
(60056, 'Mount Prospect', 'Cook'),
(60062, 'Northbrook', 'Cook'),
(60065, 'Northbrook', 'Cook'),
(60067, 'Palatine', 'Cook'),
(60068, 'Park Ridge', 'Cook'),
(60070, 'Prospect Heights', 'Cook'),
(60074, 'Palatine', 'Cook'),
(60076, 'Skokie', 'Cook'),
(60077, 'Skokie', 'Cook'),
(60078, 'Palatine', 'Cook'),
(60082, 'Techny', 'Cook'),
(60090, 'Wheeling', 'Cook'),
(60091, 'Wilmette', 'Cook'),
(60093, 'Winnetka', 'Cook'),
(60094, 'Palatine', 'Cook'),
(60095, 'Palatine', 'Cook'),
(60104, 'Bellwood', 'Cook'),
(60107, 'Streamwood', 'Cook'),
(60130, 'Forest Park', 'Cook'),
(60131, 'Franklin Park', 'Cook'),
(60133, 'Hanover Park', 'Cook'),
(60141, 'Hines', 'Cook'),
(60153, 'Maywood', 'Cook'),
(60154, 'Westchester', 'Cook'),
(60155, 'Broadview', 'Cook'),
(60159, 'Schaumburg', 'Cook'),
(60160, 'Melrose Park', 'Cook'),
(60161, 'Melrose Park', 'Cook'),
(60162, 'Hillside', 'Cook'),
(60163, 'Berkeley', 'Cook'),
(60164, 'Melrose Park', 'Cook'),
(60165, 'Stone Park', 'Cook'),
(60168, 'Schaumburg', 'Cook'),
(60169, 'Hoffman Estates', 'Cook'),
(60171, 'River Grove', 'Cook'),
(60173, 'Schaumburg', 'Cook'),
(60176, 'Schiller Park', 'Cook'),
(60179, 'Hoffman Estates', 'Cook'),
(60192, 'Hoffman Estates', 'Cook'),
(60193, 'Schaumburg', 'Cook'),
(60194, 'Schaumburg', 'Cook'),
(60195, 'Schaumburg', 'Cook'),
(60196, 'Schaumburg', 'Cook'),
(60201, 'Evanston', 'Cook'),
(60202, 'Evanston', 'Cook'),
(60203, 'Evanston', 'Cook'),
(60204, 'Evanston', 'Cook'),
(60208, 'Evanston', 'Cook'),
(60301, 'Oak Park', 'Cook'),
(60302, 'Oak Park', 'Cook'),
(60303, 'Oak Park', 'Cook'),
(60304, 'Oak Park', 'Cook'),
(60305, 'River Forest', 'Cook'),
(60402, 'Berwyn', 'Cook'),
(60406, 'Blue Island', 'Cook'),
(60409, 'Calumet City', 'Cook'),
(60411, 'Chicago Heights', 'Cook'),
(60412, 'Chicago Heights', 'Cook'),
(60415, 'Chicago Ridge', 'Cook'),
(60418, 'Crestwood', 'Cook'),
(60419, 'Dolton', 'Cook'),
(60422, 'Flossmoor', 'Cook'),
(60425, 'Glenwood', 'Cook'),
(60426, 'Harvey', 'Cook'),
(60428, 'Markham', 'Cook'),
(60429, 'Hazel Crest', 'Cook'),
(60430, 'Homewood', 'Cook'),
(60438, 'Lansing', 'Cook'),
(60439, 'Lemont', 'Cook'),
(60443, 'Matteson', 'Cook'),
(60445, 'Midlothian', 'Cook'),
(60452, 'Oak Forest', 'Cook'),
(60453, 'Oak Lawn', 'Cook'),
(60454, 'Oak Lawn', 'Cook'),
(60455, 'Bridgeview', 'Cook'),
(60456, 'Hometown', 'Cook'),
(60457, 'Hickory Hills', 'Cook'),
(60458, 'Justice', 'Cook'),
(60459, 'Burbank', 'Cook'),
(60461, 'Olympia Fields', 'Cook'),
(60462, 'Orland Park', 'Cook'),
(60463, 'Palos Heights', 'Cook'),
(60464, 'Palos Park', 'Cook'),
(60465, 'Palos Hills', 'Cook'),
(60466, 'Park Forest', 'Cook'),
(60467, 'Orland Park', 'Cook'),
(60469, 'Posen', 'Cook'),
(60471, 'Richton Park', 'Cook'),
(60472, 'Robbins', 'Cook'),
(60473, 'South Holland', 'Cook'),
(60475, 'Steger', 'Cook'),
(60476, 'Thornton', 'Cook'),
(60477, 'Tinley Park', 'Cook'),
(60478, 'Country Club Hills', 'Cook'),
(60480, 'Willow Springs', 'Cook'),
(60482, 'Worth', 'Cook'),
(60487, 'Tinley Park', 'Cook'),
(60499, 'Bedford Park', 'Cook'),
(60501, 'Summit Argo', 'Cook'),
(60513, 'Brookfield', 'Cook'),
(60525, 'La Grange', 'Cook'),
(60526, 'La Grange Park', 'Cook'),
(60534, 'Lyons', 'Cook'),
(60546, 'Riverside', 'Cook'),
(60558, 'Western Springs', 'Cook'),
(60601, 'Chicago', 'Cook'),
(60602, 'Chicago', 'Cook'),
(60603, 'Chicago', 'Cook'),
(60604, 'Chicago', 'Cook'),
(60605, 'Chicago', 'Cook'),
(60606, 'Chicago', 'Cook'),
(60607, 'Chicago', 'Cook'),
(60608, 'Chicago', 'Cook'),
(60609, 'Chicago', 'Cook'),
(60610, 'Chicago', 'Cook'),
(60611, 'Chicago', 'Cook'),
(60612, 'Chicago', 'Cook'),
(60613, 'Chicago', 'Cook'),
(60614, 'Chicago', 'Cook'),
(60615, 'Chicago', 'Cook'),
(60616, 'Chicago', 'Cook'),
(60617, 'Chicago', 'Cook'),
(60618, 'Chicago', 'Cook'),
(60619, 'Chicago', 'Cook'),
(60620, 'Chicago', 'Cook'),
(60621, 'Chicago', 'Cook'),
(60622, 'Chicago', 'Cook'),
(60623, 'Chicago', 'Cook'),
(60624, 'Chicago', 'Cook'),
(60625, 'Chicago', 'Cook'),
(60626, 'Chicago', 'Cook'),
(60628, 'Chicago', 'Cook'),
(60629, 'Chicago', 'Cook'),
(60630, 'Chicago', 'Cook'),
(60631, 'Chicago', 'Cook'),
(60632, 'Chicago', 'Cook'),
(60633, 'Chicago', 'Cook'),
(60634, 'Chicago', 'Cook'),
(60636, 'Chicago', 'Cook'),
(60637, 'Chicago', 'Cook'),
(60638, 'Chicago', 'Cook'),
(60639, 'Chicago', 'Cook'),
(60640, 'Chicago', 'Cook'),
(60641, 'Chicago', 'Cook'),
(60642, 'Chicago', 'Cook'),
(60643, 'Chicago', 'Cook'),
(60644, 'Chicago', 'Cook'),
(60645, 'Chicago', 'Cook'),
(60646, 'Chicago', 'Cook'),
(60647, 'Chicago', 'Cook'),
(60649, 'Chicago', 'Cook'),
(60651, 'Chicago', 'Cook'),
(60652, 'Chicago', 'Cook'),
(60653, 'Chicago', 'Cook'),
(60654, 'Chicago', 'Cook'),
(60655, 'Chicago', 'Cook'),
(60656, 'Chicago', 'Cook'),
(60657, 'Chicago', 'Cook'),
(60659, 'Chicago', 'Cook'),
(60660, 'Chicago', 'Cook'),
(60661, 'Chicago', 'Cook'),
(60664, 'Chicago', 'Cook'),
(60666, 'Chicago', 'Cook'),
(60668, 'Chicago', 'Cook'),
(60669, 'Chicago', 'Cook'),
(60670, 'Chicago', 'Cook'),
(60673, 'Chicago', 'Cook'),
(60674, 'Chicago', 'Cook'),
(60675, 'Chicago', 'Cook'),
(60677, 'Chicago', 'Cook'),
(60678, 'Chicago', 'Cook'),
(60680, 'Chicago', 'Cook'),
(60681, 'Chicago', 'Cook'),
(60682, 'Chicago', 'Cook'),
(60684, 'Chicago', 'Cook'),
(60685, 'Chicago', 'Cook'),
(60686, 'Chicago', 'Cook'),
(60687, 'Chicago', 'Cook'),
(60688, 'Chicago', 'Cook'),
(60689, 'Chicago', 'Cook'),
(60690, 'Chicago', 'Cook'),
(60691, 'Chicago', 'Cook'),
(60693, 'Chicago', 'Cook'),
(60694, 'Chicago', 'Cook'),
(60695, 'Chicago', 'Cook'),
(60696, 'Chicago', 'Cook'),
(60697, 'Chicago', 'Cook'),
(60699, 'Chicago', 'Cook'),
(60701, 'Chicago', 'Cook'),
(60706, 'Harwood Heights', 'Cook'),
(60707, 'Elmwood Park', 'Cook'),
(60712, 'Lincolnwood', 'Cook');

INSERT INTO Therapist (T_id, T_fName, T_lName, T_therapistType, T_licenseNumber, T_phone, T_email, T_address, L_zipCode, T_socialSecurityNumber, T_bankRoutingNumber) 
VALUES
(1, 'Aziz', 'Makda', 'Physical Therapist Assistant ', 1609901, '(773) 616-9873', 'aziz@gmail.com', '8518 N Major Ave', 60053, '123-12-1234', 123456789),
(2, 'Raj', 'Kumar', 'Occupational Therapist Assistant ', 1721201, '(847) 456-5852', 'raj@gmail.com', '6716 N Kimball Ave', 60712, '111-11-1111', 987654321),
(3, 'Faizan', 'Jami', 'Physical Therapist Assistant ', 1987921, '(224) 458-8223', 'faizan@gmail.com', '102 N Waterman Ave', 60004, '222-22-2222', 147896321),
(4, 'Mohmed', 'Forgot ', 'Speech Therapist', 1923401, '(773) 658-4123', 'mohmed@gmail.com', '560 Webford Ave', 60016, '333-33-3333', 123698741),
(5, 'Bhavin ', 'Patel', 'Physical Therapist Assistant ', 1329801, '(224) 235-8563', 'bhavin@gmail.com', '1517 N Chicago Ave', 60038, '444-44-4444', 147852369),
(6, 'Imran', 'Memon', 'Occupational Therapist Assistant ', 1007901, '(847) 568-8123', 'imran@gmail.com', '1338 High Point Lane', 60062, '555-55-5555', 963258741),
(7, 'Solomon', 'Farid', 'Speech Therapist', 1951231, '(847) 235-8651', 'solomon@gmail.com', '1405 Lahon Street', 60068, '666-66-6666', 159753258),
(8, 'Abraham ', 'Memon', 'Occupational Therapist', 1795901, '(773) 132-8932', 'abraham@gmail.com', '4918 Kirk Street', 60077, '777-77-7777', 951753258),
(9, 'Chrissie', 'Chris', 'Physical Therapist ', 1112985, '(224) 489-9872', 'chrissie@gmail.com', '108 Villa Road', 60107, '888-88-8888', 456951753),
(10, 'Shannon ', 'Flay ', 'Physical Therapist ', 1782301, '(847) 456-9871', 'shannon@gmail.com', '2304 S 24th Ave', 60155, '999-99-9999', 298745369);

INSERT INTO Patient (P_id, P_fName, P_lName, P_diagnosis, P_insuranceType, P_socialSecurityNumber, P_address, L_zipCode, P_phone, P_email, P_totalVisits, P_attemptedVisits, P_cancelledVisits, P_therapyStatus)
VALUES
( 1, 'Danny', 'Bob', 'Knee Replacement', 'Medicaid', '878-87-8787', '49 Briar Road', 60029, '(313) 565-8568', 'danny@gmail.com', 10, 2, 0, 1 ),
( 2, 'Ted', 'Hazel', 'Dementia', 'Humana', '979-97-9797', '527 La Salle Street', 60016, '(224) 589-9820', 'ted@gmail.com', 5, 0, 2, 1 ),
( 3, 'John', 'Smith', 'Parkinson', 'Medicare', '545-54-5454', '304 Melrose Ave', 60043, '(847) 871-9825', 'john@gmail.com', 8, 1, 2, 1 ),
( 4, 'Jennifer', 'Jenny', 'Alzheimer', 'BlueCross BlueShield', '656-65-6565', '801 Tonne Road', 60009, '(312) 878-9821', 'jennifer@gmail.com', 9, 0, 1, 1 ),
( 5, 'Sarah', 'Aniston', 'Weakness', 'Aetna', '211-21-2121', '2547 Atlantic Street', 60131, '(224) 658-8589', 'sarah@gmail.com', 14, 3, 0, 1 ),
( 6, 'Nicole', 'James', 'CVA', 'United HealthCare', '911-97-2431', '20072 Preston Lane', 60411, '(773) 698-1236', 'nicole@gmail.com', 12, 3, 2, 1 ),
( 7, 'Jackie', 'Rodriguez', 'Hip Replacement', 'Anthem', '911-28-8118', '423 E Benton Place', 60601, '(987) 123-6598', 'jackie@gmail.com', 0, 0, 0, 0 ),
( 8, 'Jessie', 'Bond', 'Multiple Sclerosis', 'AARP', '121-85-9861', '7905 Cressett Drive', 60707, '(224) 129-8731', 'jessie@gmail.com', 8, 0, 0, 1 ),
( 9, 'Todd', 'Tod', 'Ankle Sprain', 'Cigna', '987-25-6950', '5311 N Lakewood Ave', 60640, '(847) 197-3197', 'todd@gmail.com', 0, 0, 0, 0 ),
( 10, 'Carl', 'Theo', 'Rheumatoid Arthritis', 'Providence', '123-58-9872', '6455 N Sauganash Ave', 60712, '(224) 215-8421', 'carl@gmail.com', 0, 0, 0, 0 );

INSERT INTO Thera_Pat (T_id, P_id)
VALUES
( 1, 4 ),
( 1, 9 ),
( 1, 10 ),
( 2, 1 ),
( 2, 2 ),
( 5, 5 ),
( 6, 6 ),
( 6, 7 ),
( 8, 8 ),
( 10, 3 );

INSERT INTO Coverage (T_id, L_zipCode, C_gasCoverage)
VALUES
( 1, 60712, 0 ),
( 1, 60640, 0.5 ),
( 1, 60009, 0 ),
( 1, 60053, 0 ),
( 2, 60029, 1.23 ), 
( 2, 60016, 2.13 ), 
( 3, 60043, 0 ),
( 3, 60016, 0 ),
( 3, 60601, 5.21 ), 
( 4, 60712, 0 ), 
( 4, 60640, 0.6 ),
( 4, 60077, 1 ),
( 5, 60131, 0 ), 
( 6, 60411, 0.23 ),
( 6, 60601, 0.65 ), 
( 7, 60065, 0 ),
( 7, 60022, 1.62 ),
( 8, 60707, 3.65 ), 
( 8, 60477, 0 ),
( 9, 60640, 1.29 ),
( 10, 60712, 0.29 ),
( 10, 60155, 0 ),
( 10, 60160, 0.1);

