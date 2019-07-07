DROP TABLE IF EXISTS Accounts;

DROP TABLE IF EXISTS Transactions;
 
CREATE TABLE Accounts (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  acc_number number(12) NOT NULL,
  acc_sortcode number(6) NOT NULL,
  acc_name VARCHAR(250) NOT NULL,
  acc_display_name VARCHAR(250),
  acc_type VARCHAR(250),
  acc_bank_society varchar(2),
  acc_balance DECIMAL(12,2)
);

CREATE TABLE Transactions (
  trans_id INT AUTO_INCREMENT  PRIMARY KEY,
  trans_from_acc_number number(12) NOT NULL,
  trans_to_acc_number number(12) NOT NULL,
  trans_amount DECIMAL(12,2) NOT NULL,
  trans_status VARCHAR(1),
  trans_type VARCHAR(100),
  trans_reference VARCHAR(250)
);
 
INSERT INTO Accounts (acc_number, acc_sortcode, acc_name, acc_display_name, acc_type, acc_bank_society, acc_balance) VALUES
  (123456,110101,'MR Test User 1','TU1', 'CURRENT','GB',900.00),
  (123789,110101,'MR Test User 2','TU2', 'CURRENT','GB',1000.00); 
 
