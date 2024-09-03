

DROP TABLE COMMANDER;

DROP TABLE BONCOMMANDE;

DROP TABLE LIVRAISON;

DROP TABLE ADRLIVRAISON;

DROP TABLE CLIENT;

DROP TABLE ARTICLE;

DROP TABLE CARTECB;

DROP TABLE PTRELAIS;

CREATE TABLE CLIENT (
    NUMCLIENT DECIMAL(6),
    CODEAV DECIMAL(12),
    CIVILITE VARCHAR(3),
    NOM VARCHAR(30),
    PRENOM VARCHAR(30),
    ADRESSE VARCHAR(50),
    CODEPOSTAL DECIMAL(5),
    VILLE VARCHAR(30),
    TELEPHONE DECIMAL(10),
    TPORTABLE DECIMAL(10),
    DATEN VARCHAR (30),
    EMAIL VARCHAR(30),
    CONSTRAINT PK_CLIENT PRIMARY KEY (NUMCLIENT),
    CONSTRAINT CK_CLIENT_NUMCLIENT CHECK (NUMCLIENT > 0),
    CONSTRAINT CK_CLIENT_CIVILITE CHECK (CIVILITE IN ('Mme', 'M.'))
    
);

CREATE TABLE ARTICLE (
    CODEA DECIMAL(8),
    NOMA VARCHAR(30),
    NUMPAGE DECIMAL(3),
    TAILLE VARCHAR(12),
    PRIXU DECIMAL(5),
    CONSTRAINT PK_ARTICLE PRIMARY KEY (CODEA),
    CONSTRAINT CK_ARTICLE_CODEA CHECK (CODEA > 0),
    CONSTRAINT CK_ARTICLE_PRIXU CHECK (PRIXU > 0),
    CONSTRAINT CK_ARTICLE_NUMPAGE CHECK (NUMPAGE > 0)
);

CREATE TABLE ADRLIVRAISON (
    CODEADRLIV DECIMAL(6),
    ADRESSELIV VARCHAR(50),
    CODEPOSTALLIV DECIMAL(5),
    VILLELIV VARCHAR(50),
    NUMCLIENT DECIMAL(6),
    CONSTRAINT PK_ADRLIVRAISON PRIMARY KEY (CODEADRLIV),
    CONSTRAINT FK_ADRLIVRAISON_NUMCLIENT FOREIGN KEY (NUMCLIENT) REFERENCES CLIENT(NUMCLIENT),
    CONSTRAINT CK_ADRLIVRAISON_CODEADRLIV CHECK (CODEADRLIV > 0)
);

CREATE TABLE PTRELAIS (
    NUMPTRELAIS DECIMAL(6),
    NOMPTRELAIS VARCHAR(30),
    ADRESSEPTRELAIS VARCHAR(50),
    CODEPPTRELAIS DECIMAL (5),
    VILLEPTRELAIS VARCHAR (30),
    CONSTRAINT PK_PTRELAIS PRIMARY KEY (NUMPTRELAIS),
    CONSTRAINT CK_PTRELAIS_NUMPTRELAIS CHECK (NUMPTRELAIS > 0)
);

CREATE TABLE LIVRAISON (
    CODELIV DECIMAL(6),
    MODELIV VARCHAR(30),
    FRAISLIV DECIMAL(3),
    CODEADRLIV DECIMAL(6),
    NUMPTRELAIS DECIMAL(6),
    CONSTRAINT PK_LIVRAISON PRIMARY KEY (CODELIV),
    CONSTRAINT FK_LIVRAISON_ADRLIV FOREIGN KEY (CODEADRLIV) REFERENCES ADRLIVRAISON(CODEADRLIV),
    CONSTRAINT FK_LIVRAISON_NUMPTRELAIS FOREIGN KEY (NUMPTRELAIS) REFERENCES PTRELAIS(NUMPTRELAIS),
    CONSTRAINT CK_LIVRAISON_FRAISLIV CHECK (FRAISLIV IN (NULL, 5.50, 7.50, 6.00)),
    CONSTRAINT CK_LIVRAISON_MODELIV CHECK (MODELIV IN ('Standard Domicile', 'Rapide Domicile', 'Rapide Postal', 'Rapide Pickup')),
    CONSTRAINT CK_LIVRAISON_CODELIV CHECK (CODELIV > 0)
);

CREATE TABLE CARTECB (
    NUMCB DECIMAL(16),
    DATEEXPIR VARCHAR(30),
    CRYPTO DECIMAL(3),
    CONSTRAINT PK_CARTECB PRIMARY KEY (NUMCB),
    CONSTRAINT CK_CARTECB_NUMCB CHECK (NUMCB > 0)
);

CREATE TABLE BONCOMMANDE (
    NUMBC DECIMAL(6),
    DATEBC VARCHAR(30) ,
    TYPEPAIEMENT VARCHAR(30),
    NUMCLIENT DECIMAL(6),
    NUMCB DECIMAL(16),
    CODELIV DECIMAL(6),
    CONSTRAINT PK_BONCOMMANDE PRIMARY KEY (NUMBC),
    CONSTRAINT FK_BONCOMMANDE_NUMCLIENT FOREIGN KEY (NUMCLIENT) REFERENCES CLIENT(NUMCLIENT),
    CONSTRAINT FK_BONCOMMANDE_NUMCB FOREIGN KEY (NUMCB) REFERENCES CARTECB(NUMCB), 
    CONSTRAINT FK_BONCOMMANDE_CODELIV FOREIGN KEY (CODELIV) REFERENCES LIVRAISON(CODELIV)                                                                       
);

CREATE TABLE COMMANDER (
    CODEA DECIMAL(8),
    NUMBC DECIMAL(6),
    QUANTITE DECIMAL(3),
    CONSTRAINT PK_COMMANDER PRIMARY KEY (CODEA, NUMBC),
    CONSTRAINT FK_COMMANDER_CODEA FOREIGN KEY (CODEA) REFERENCES ARTICLE(CODEA),
    CONSTRAINT FK_COMMANDER_NUMBC FOREIGN KEY (NUMBC) REFERENCES BONCOMMANDE(NUMBC),
    CONSTRAINT CK_COMMANDER_QUANTITE CHECK (QUANTITE > 0)
);

INSERT INTO Client (numclient,codeav,civilite,nom,prenom,adresse,codepostal,ville,telephone,tportable,daten,email)
      VALUES (324620, NULL, 'Mme', 'Aztakes', 'Helene', 'Rue Ferrari', 31000, 'Toulouse', 0502030405, 0601020304, '1969/01/01','haztakes@gmail.com');
      
INSERT INTO Client (numclient,codeav,civilite,nom,prenom,adresse,codepostal,ville,telephone,tportable,daten,email)
      VALUES (325220, NULL, 'M.', 'Assein', 'Marc', 'Place du chene', 31000, 'Toulouse', 0503070809, 0602030415, '1957/08/18','marcassein@gmail.com');
      
INSERT INTO Client (numclient,codeav,civilite,nom,prenom,adresse,codepostal,ville,telephone,tportable,daten,email)
      VALUES (333100, NULL, 'M.', 'Terrieur', 'Alain', 'Avenue Principale', 31000, 'Toulouse', 0503040506, 0603040506, '1960/07/14','alainter4@gmail.com');

INSERT INTO Article (codea,noma,numpage,taille,prixu)
    VALUES ('55433111','Taie doreiller', '12', '50*70', '1039');
    
INSERT INTO Article (codea,noma,numpage,taille,prixu)  
    VALUES ('55467108', 'Taie de traversin', '12', '85*185', '1119');
    
INSERT INTO Article (codea,noma,numpage,taille,prixu)
    VALUES('55463103', 'Drap plat', '12', '240*300', '3439' );

INSERT INTO Article (codea,noma,numpage,taille,prixu)   
    VALUES ('55467109', 'Drap housse', '12', '140*190', '2479');

INSERT INTO Article (codea,noma,numpage,taille,prixu)   
    VALUES ('56952031', 'Couverture Thermodactyl', '16', '220*240', '6449');
    
    INSERT INTO Article (codea,noma,numpage,taille,prixu)   
    VALUES ('56952032', 'Couverture Thermodactyl', '16', '220*240', '6449');

INSERT INTO Article (codea,noma,numpage,taille,prixu)   
    VALUES ('55463000', 'Drap plat', '12', '240*300', '1839');

INSERT INTO Article (codea,noma,numpage,taille,prixu)   
    VALUES ('51790004', 'Housse de couette', '13', '260*240', '8299');

INSERT INTO Article (codea,noma,numpage,taille,prixu)   
    VALUES ('56952033', 'Couverture Thermodactyl', '16', '260*240', '7449');
    
    INSERT INTO AdrLivraison (codeadrliv,adresseliv,codepostalliv,villeliv,numclient)
    VALUES ('123456','Rue Ferrari', '31000', 'Toulouse', '324620');

INSERT INTO AdrLivraison (codeadrliv,adresseliv,codepostalliv,villeliv,numclient)
    VALUES('123457','Place du chene', '31000', 'Toulouse', '325220');

INSERT INTO AdrLivraison (codeadrliv,adresseliv,codepostalliv,villeliv,numclient)
    VALUES('123458','Avenue Pricipale', '31400', 'Toulouse', '333100 ');
    
    INSERT INTO PtRelais (numptrelais,nomptrelais,adresseptrelais,codepptrelais,villeptrelais)
    VALUES ('909032', 'VETJ CIG', '7 GRAND RUE', '31000', 'Toulouse');  

INSERT INTO PtRelais (numptrelais,nomptrelais,adresseptrelais,codepptrelais,villeptrelais)
    VALUES('811400', 'NULL', '5 ALLES JEAN JAURES', '31000', 'Toulouse');

INSERT INTO Livraison (codeliv,modeliv,fraisliv,codeadrliv,numptrelais)
    VALUES ('000001', 'Standard Domicile', NULL, '123456', '909032');
    
INSERT INTO Livraison (codeliv,modeliv,fraisliv,codeadrliv,numptrelais)
    VALUES('000002', 'Rapide Postal', NULL, '123457', '811400');

INSERT INTO Livraison (codeliv,modeliv,fraisliv,codeadrliv,numptrelais)
    VALUES('000003', 'Rapide Postal', NULL, '123458', '811400');

INSERT INTO Livraison (codeliv,modeliv,fraisliv,codeadrliv,numptrelais)
    VALUES('000004', 'Rapide Domicile', '7,50','123457' , '811401');
    
INSERT INTO Livraison (codeliv,modeliv,fraisliv,codeadrliv,numptrelais)
    VALUES('000005', 'Standard Domicile', NULL,'123458' , '811400');
    
INSERT INTO CarteCB (numcb,dateexpir,crypto)
    VALUES ('000123456789000', '2023-10-10', '130');

INSERT INTO CarteCB (numcb,dateexpir,crypto)
    VALUES('000123456789001', '2024-10-10', '123');

INSERT INTO CarteCB (numcb,dateexpir,crypto)
    VALUES('000112233445000', '2025-01-23', '124');
    
    INSERT INTO BonCommande(numbc,datebc,typepaiement,numclient,numcb,codeliv)
    VALUES ('202041',NULL, 'carte bancaire','324620','000123456789000', '000001');

INSERT INTO BonCommande(numbc,datebc,typepaiement,numclient,numcb,codeliv)
    VALUES ('202042',NULL, 'carte bancaire','324620', '000123456789000', '000002');

    INSERT INTO BonCommande(numbc,datebc,typepaiement,numclient,numcb,codeliv)
    VALUES ('202044',NULL, 'chèque bancaire','325220', NULL, '000003');

    INSERT INTO BonCommande(numbc,datebc,typepaiement,numclient,numcb,codeliv)
    VALUES  ('202043',NULL, 'chèque bancaire','324620', NULL, '000004');

    INSERT INTO BonCommande(numbc,datebc,typepaiement,numclient,numcb,codeliv)
    VALUES ('202045',NULL, 'carte bancaire','333100', '000112233445000', '000005');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES ('55433111', '202041', '2');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES('55467108','202041', '1');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES('55463103','202041', '1');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES('55467109','202041', '1');
    
INSERT INTO Commander (codea,numbc,quantite)
    VALUES('56952031','202042 ', '1');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES ('56952032','202044', '2');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES('55463000','202043', '2');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES('51790004','202045', '1');

INSERT INTO Commander (codea,numbc,quantite)
    VALUES('56952033','202045', '2');
    


SELECT
    COUNT(*)
FROM
    BONCOMMANDE;
    

SELECT
    SUM(QUANTITE*PRIXU) AS MONTANTTOTAL,
    COUNT(*)            AS NOMBRECOMMANDES
FROM
    COMMANDER,
    ARTICLE
WHERE
    COMMANDER.CODEA = ARTICLE.CODEA;
    
SELECT NOMA, SUM(QUANTITE) as total_sales
FROM BONCOMMANDE JOIN ARTICLE ON BONCOMMANDE.CODEA = ARTICLE.CODEA
GROUP BY NOMA;

SELECT NOM, PRENOM, COUNT(*) AS NOMBRECOMMANDES FROM BONCOMMANDE JOIN CLIENT ON BONCOMMANDE.NUMCLIENT = CLIENT.NUMCLIENT GROUP BY NOM, PRENOM;

SELECT
    VILLE,
    COUNT(*)            AS NOMBRECOMMANDES,
    SUM(QUANTITE*PRIXU) AS MONTANTTOTAL
FROM
    BONCOMMANDE,
    CLIENT,
    COMMANDER,
    ARTICLE
WHERE
    BONCOMMANDE.NUMCLIENT = CLIENT.NUMCLIENT
    AND COMMANDER.NUMBC = BONCOMMANDE.NUMBC
    AND COMMANDER.CODEA = ARTICLE.CODEA
GROUP BY
    VILLE;


SELECT
    PRIXU
FROM
    ARTICLE
WHERE
    CODEA = '56952031';


SELECT
    NUMCLIENT,
    QUANTITE
FROM
    BONCOMMANDE,
    COMMANDER
WHERE
    BONCOMMANDE.NUMBC = COMMANDER.NUMBC
    AND CODEA = '56952031';






