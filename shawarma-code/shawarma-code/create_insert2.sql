CREATE TABLE IF NOT EXISTS Band
(
bname char(50) NOT NULL,
bleader char(50),
year char(4),
PRIMARY KEY (bname)
);

CREATE TABLE IF NOT EXISTS Member
(
mname char(50) NOT NULL,
mwork char(50),
msex char(10),
birthdate date,
bname char(50) NOT NULL,
PRIMARY KEY (mname),
FOREIGN KEY (bname) REFERENCES Band(bname)
);

CREATE TABLE IF NOT EXISTS Album
(
aname char(50) NOT NULL,
company char(50),
atime date,
aband char(50) NOT NULL,
PRIMARY KEY (aname),
FOREIGN KEY (aband) REFERENCES Band(bname)
);

CREATE TABLE IF NOT EXISTS Song
(
sname char(50) NOT NULL,
swriter char(50),
salbum char(50) NOT NULL,
PRIMARY KEY (sname),
FOREIGN KEY (salbum) REFERENCES Album(aname)
);

CREATE TABLE IF NOT EXISTS Concert
(
CID char(20) NOT NULL,
ctime date,
cplace char(50),
PRIMARY KEY (CID)
);

CREATE TABLE IF NOT EXISTS Joins
(
CID char(20) NOT NULL,
bname char(50) NOT NULL,
PRIMARY KEY (CID, bname),
FOREIGN KEY (CID) REFERENCES Concert(CID),
FOREIGN KEY (bname) REFERENCES Band(bname)
);

CREATE TABLE IF NOT EXISTS Performs
(
CID char(20) NOT NULL,
sname char(50) NOT NULL,
performer char(50) NOT NULL,
PRIMARY KEY (CID, sname),
FOREIGN KEY (CID) REFERENCES Concert(CID),
FOREIGN KEY (sname) REFERENCES Song(sname),
FOREIGN KEY (performer) REFERENCES Band(bname)
);

INSERT INTO Band(bname, year, bleader)
VALUES
('The Band Perry',2009,'Kimberly Perry'),
('Linkin Park',1996,'Emily Armstrong'),
('Imagine Dragons',2008,'Dan Reynolds'),
('The Cardigans',1992,'Nina Persson');

INSERT INTO member (mname, mwork, msex, birthdate,bname)
VALUES
('Dan Reynolds', 'lead singer', 'male', '1987-07-14','Imagine Dragons'),
('Ben McKee', 'bassist', 'male', '1985-04-07','Imagine Dragons'),
('Wayne Thurmon', 'guitarist', 'male', '1984-06-15','Imagine Dragons'),
('Dan Platzman', 'drummer', 'male', '1987-08-03','Imagine Dragons'),
('Kimberly Perry', 'lead singer', 'female', '1983-07-12','The Band Perry'),
('Reid Perry', 'guitarist', 'male', '1988-11-17','The Band Perry'),
('Neil Perry', 'drummer', 'male', '1990-07-23','The Band Perry'),
('Emily Armstrong', 'lead singer', 'female', '1986-05-06','Linkin Park'),
('Mike Shinoda', 'keyboarder', 'male', '1977-02-11','Linkin Park'),
('Phoenix Farrell', 'bassist', 'male', '1977-02-08','Linkin Park'),
('Bradford Delson', 'guitarist', 'male', '1977-12-07','Linkin Park'),
('Colin Brittain', 'drummer', 'male', '1986-12-29','Linkin Park'),
('Joseph Hahn', 'DJ', 'male', '1977-03-15','Linkin Park'),
('Nina Persson', 'lead singer', 'female', '1974-09-06','The Cardigans'),
('Peter Svensson', 'guitarist', 'male', '1974-10-18','The Cardigans'),
('Bengt Lagersson', 'drummer', 'male', '1973-07-05','The Cardigans'),
('Johansson', 'keyboarder', 'male', '1973-02-23','The Cardigans');

INSERT INTO Album (aname, company, atime, aband)
VALUES
('If I Die Young', 'NULL','2024-06-10','The Band Perry'),
('Meteora', 'Warner Bros.','2003-03-25','Linkin Park'),
('Hybrid Theory', 'Warner Bros.','2000-10-24','Linkin Park'),
('Continued Silence', 'UMG','2012-02-14','Imagine Dragons'),
('Shorts EP', 'Interscope','2015-05-04','Imagine Dragons'),
('Long Gone Before Daylight', 'Universal Music AB','2003-01-01','The Cardigans'),
('Fisrt Band On The Moon', 'Universal Music AB','1996-01-01','The Cardigans');

INSERT INTO Song (sname, swriter, salbum)
VALUES
('If I Die Young', 'Kimberly Perrson','If I Die Young'),
('Numb', 'Linkin Park','Meteora'),
('Lying from You', 'Linkin Park','Meteora'),
('In the End', 'Bradford','Hybrid Theory'),
('Demons', 'Alex Da','Continued Silence'),
('My Fault','Alex Da','Continued Silence'),
('Shorts', 'Imagine Dragons','Shorts EP'),
('Communication','The Cardigans','Long Gone Before Daylight'),
('Lovefool','The Cardigans','Fisrt Band On The Moon'),
('Losers','The Cardigans','Fisrt Band On The Moon');

INSERT INTO Concert (CID, cplace, ctime)
VALUES
(1,'Coachella', '2024-12-28'),
(2,'Bangkok', '2025-05-20'),
(3,'London', '2025-08-22'),
(4,'Los Angeles','2026-03-06');

INSERT INTO Joins (CID, bname)
VALUES
(1,'The Band Perry'),
(1,'Linkin Park'),
(2,'Linkin Park'),
(2,'Imagine Dragons'),
(2,'The Cardigans'),
(3,'The Band Perry'),
(3,'Imagine Dragons'),
(3,'The Cardigans'),
(4,'Imagine Dragons'),
(4,'The Cardigans');


INSERT INTO Performs (CID, sname, performer)
VALUES
(1,'If I Die Young','The Band Perry'),
(1,'Lovefool','The Band Perry'),
(1,'Numb','Linkin Park'),
(1,'Lying from You','Linkin Park'),
(2,'Lying from You','Linkin Park'),
(2,'My Fault','Imagine Dragons'),
(2,'Shorts','Imagine Dragons'),
(2,'Communication','The Cardigans'),
(2,'Losers','The Cardigans'),
(3,'If I Die Young','The Band Perry'),
(3,'Numb','The Band Perry'),
(3,'Shorts','Imagine Dragons'),
(3,'Demons','Imagine Dragons'),
(3,'Communication','The Cardigans'),
(4,'Demons','Imagine Dragons'),
(4,'My Fault','Imagine Dragons'),
(4,'Shorts','Imagine Dragons'),
(4,'If I Die Young','The Cardigans'),
(4,'Losers','The Cardigans');