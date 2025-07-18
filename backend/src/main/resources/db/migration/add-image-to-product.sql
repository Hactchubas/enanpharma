-- Add image support to product table
-- This migration adds image_url column to store file paths/URLs

ALTER TABLE product ADD COLUMN image_url VARCHAR(500);