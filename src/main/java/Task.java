import org.json.JSONObject;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = "to-do";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    JSONObject toJSON() {
        JSONObject myObject = new JSONObject();

        myObject.put("id", id);
        myObject.put("description", description);
        myObject.put("status", status);
        myObject.put("createdAt", createdAt);
        myObject.put("updatedAt", updatedAt);

        return myObject;
    }

    static Task fromJSON(JSONObject myObject) {
        Task task = new Task(myObject.getInt("id"), myObject.getString("description"));
        task.status = myObject.getString("status");
        task.createdAt = LocalDateTime.parse(myObject.getString("createdAt"));
        task.updatedAt = LocalDateTime.parse(myObject.getString("updatedAt"));

        return task;
    }

    int getId() {
        return id;
    }
    void setId(int id) {
        this.id = id;
    }
    String getDescription() {
        return description;
    }
    void setDescription(String description) {
        this.description = description;
    }
    String getStatus() {
        return status;
    }
    void setStatus(String status) {
        this.status = status;
    }
    LocalDateTime getCreatedAt() {
        return createdAt;
    }
    void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
