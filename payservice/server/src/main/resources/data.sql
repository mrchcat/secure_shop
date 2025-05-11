INSERT INTO currencies(currency)
VALUES ('RUR'), ('USD'),('EUR'),('CNY');

INSERT INTO clients(client_uuid,name)
VALUES ('db1673e9-bf97-4dfa-acdb-88a280d4d144','Anna'),('73c474e3-264f-4d8d-8f5d-4f566eb341a0','Boris'),('623ff0e5-2069-4051-88dc-fea52a85ffab','Strawberry Ltd');

INSERT INTO accounts(account,client_id,balance,currency_id)
VALUES ('4213412313',1,10000,1),('2132131311',2,12000,1),('54353545534',3,0,1);
