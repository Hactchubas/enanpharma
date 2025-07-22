-- Add quantity column to product table
ALTER TABLE product ADD COLUMN quantity INTEGER NOT NULL DEFAULT 0;

-- Update existing products to have a default quantity of 0
UPDATE product SET quantity = 0 WHERE quantity IS NULL;