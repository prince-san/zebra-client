package migration;

import java.time.Instant;
import java.util.Optional;

public interface TimestampStorage {
    Optional<Instant> getTimestamp(String databaseId);

    void updateTimestamp(String databaseId, Instant updatedTime);
}
