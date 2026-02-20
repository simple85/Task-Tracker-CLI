import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest {

    @Test
    void newTaskStartsAsToDo() {
        Task task = new Task(1, "Write tests");

        assertThat(task.getStatus()).isEqualTo("to-do");
        assertThat(task.getCreatedAt()).isEqualTo(task.getUpdatedAt());
    }

    @Test
    void jsonRoundTripPreservesAllFields() {
        Task original = new Task(7, "Ship it");
        original.setStatus("in-progress");

        JSONObject json = new JSONObject(original.toJSON().toString());
        Task copy = Task.fromJSON(json);

        assertThat(copy).usingRecursiveComparison().isEqualTo(original);
    }
}
