package migration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

public class TimestampStorageImpl implements TimestampStorage {
    private final File filePath = new File("./timestamp-storage.json");
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private final HashMap<String, String> timestampMap;

    public TimestampStorageImpl() throws IOException {
        if (filePath.exists()) {
            this.timestampMap = mapper.readValue(filePath, HashMap.class);
        } else {
            filePath.createNewFile();
            timestampMap = new HashMap<>();
            flushDataToFile(mapper.writeValueAsString(timestampMap));
        }
    }

    @Override
    public Optional<Instant> getTimestamp(String databaseId) {
        String timestamp = timestampMap.get(databaseId);
        if (timestamp == null)
            return Optional.empty();
        return Optional.of(Instant.parse(timestamp));
    }

    @Override
    public void updateTimestamp(String databaseId, Instant updatedTime) {
        timestampMap.put(databaseId, updatedTime.toString());
        try {
            flushDataToFile(mapper.writeValueAsString(timestampMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flushDataToFile(String data) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
        out.write(data);
        out.flush();
    }
}
