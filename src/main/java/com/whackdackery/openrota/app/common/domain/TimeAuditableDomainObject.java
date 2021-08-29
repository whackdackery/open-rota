package com.whackdackery.openrota.app.common.domain;


import java.time.OffsetDateTime;

public abstract class TimeAuditableDomainObject {
    OffsetDateTime created;
    OffsetDateTime updated;
}
