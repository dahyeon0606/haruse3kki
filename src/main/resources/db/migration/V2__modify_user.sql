ALTER TABLE user DROP COLUMN email;
ALTER TABLE user ADD UNIQUE (provider_id);