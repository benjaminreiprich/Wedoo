INSERT INTO user_table (name) values ('Bob');
INSERT INTO user_table (name) values ('Alice');
INSERT INTO user_table (name) values ('Carol');


INSERT INTO company (name, balance) values ('Company1', 1000.0);
INSERT INTO company (name, balance) values ('Company2', 100.0);

INSERT INTO giftcard (user_id, company_id, amount, creation_date, expiry_date)
       VALUES (1,1,50,DATE '2022-01-23',DATE '2023-01-23');
INSERT INTO giftcard (user_id, company_id, amount, creation_date, expiry_date)
       VALUES (1,2,100,DATE '2022-01-23',DATE '2023-01-23');
INSERT INTO giftcard (user_id, company_id, amount, creation_date, expiry_date)
       VALUES (1,2,100,DATE '2021-01-23',DATE '2022-01-23');

INSERT INTO meal (user_id, company_id, amount, creation_date)
       VALUES (1,1,50,DATE '2022-01-23');
INSERT INTO meal (user_id, company_id, amount, creation_date)
       VALUES (1,2,100,DATE '2022-01-23');
INSERT INTO meal (user_id, company_id, amount, creation_date)
       VALUES (1,2,100,DATE '2019-01-23');

