INSERT INTO currencies(currency)
VALUES ('RUR'), ('USD'),('EUR'),('CNY');

INSERT INTO clients(client_uuid,name)
VALUES ('266cfbef-67c0-44d4-a1d1-f722cc7b9df0','Pavel Viktorovich'),
       ('db1673e9-bf97-4dfa-acdb-88a280d4d144','Anna'),
       ('73c474e3-264f-4d8d-8f5d-4f566eb341a0','Boris'),
       ('623ff0e5-2069-4051-88dc-fea52a85ffab','Strawberry Ltd'),
       ('a4d28856-07eb-4c43-aa68-c3c0edfbe9e1','new');


INSERT INTO accounts(account,client_id,balance,currency_id)
VALUES ('4213412313',1,1000000,1),
       ('2132131311',2,12000,1),
       ('2132121411',3,12000,1),
       ('54353545534',4,0,1),
       ('1231243123',5,10000,1);
