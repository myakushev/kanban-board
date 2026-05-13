ALTER TABLE task ADD COLUMN is_system_task BOOLEAN DEFAULT FALSE;
ALTER TABLE task ADD COLUMN external_id VARCHAR(255);
UPDATE task SET is_system_task = FALSE WHERE is_system_task IS NULL;
CREATE INDEX idx_task_external_id ON task(external_id);