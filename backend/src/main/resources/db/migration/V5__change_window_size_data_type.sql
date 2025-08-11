ALTER TABLE policy
DROP
COLUMN window_size;

ALTER TABLE policy
    ADD window_size INT NULL;