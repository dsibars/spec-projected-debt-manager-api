-- Baseline migration
CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload TEXT NOT NULL,
    metadata TEXT NOT NULL,
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    retry_count INTEGER DEFAULT 0
);

CREATE INDEX idx_outbox_events_tenant_id ON outbox_events(tenant_id);
CREATE INDEX idx_outbox_events_processed_at ON outbox_events(processed_at);
