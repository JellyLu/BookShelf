ALTER TABLE wo_book
ADD COLUMN category_code VARCHAR(10) NULL;
ALTER TABLE wo_book
ADD CONSTRAINT FK_CATEGORY_CODE FOREIGN KEY(category_code) REFERENCES wo_category(code);