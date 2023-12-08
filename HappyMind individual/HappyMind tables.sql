CREATE DATABASE HappyMind;
USE HappyMind;
CREATE TABLE Entry (
entryIndex int NOT NULL AUTO_INCREMENT,
entryContext varchar(120) NOT NULL,
PRIMARY KEY (entryIndex));

CREATE TABLE Goal (
goalID int NOT NULL AUTO_INCREMENT,
goalSize int NOT NULL,
PRIMARY KEY(goalID)
);

INSERT INTO Goal(goalSize) VALUES(5);

INSERT INTO Entry(entryContext) VALUES("I am happy about eating oranges.");
DELETE FROM Entry WHERE entryIndex =2;

UPDATE Entry SET entryContext = "new context" WHERE entryIndex = 2;

SELECT entryIndex FROM Entry;
SELECT * FROM Entry;
SELECT * FROM Goal;


