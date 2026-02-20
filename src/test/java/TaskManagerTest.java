import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class TaskManagerTest {

    @TempDir
    Path tempDir;

    private Path file;
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        file = tempDir.resolve("tasks.json");
        manager = new TaskManager(file);
    }

    @Test
    void createsEmptyStorageFileOnFirstRun() throws Exception {
        assertThat(file).exists();
        assertThat(new JSONArray(Files.readString(file)).length()).isZero();
    }

    @Test
    void addTaskAssignsIncrementingIdsAndDefaultStatus() {
        manager.addTask("Buy groceries");
        manager.addTask("Walk the dog");

        assertThat(manager.getTasks())
                .extracting(Task::getId, Task::getDescription, Task::getStatus)
                .containsExactly(
                        org.assertj.core.groups.Tuple.tuple(1, "Buy groceries", "to-do"),
                        org.assertj.core.groups.Tuple.tuple(2, "Walk the dog", "to-do"));
    }

    @Test
    void tasksArePersistedAndReloaded() {
        manager.addTask("Buy groceries");
        manager.markStatus(1, "done");

        TaskManager reloaded = new TaskManager(file);

        assertThat(reloaded.getTasks()).singleElement().satisfies(task -> {
            assertThat(task.getDescription()).isEqualTo("Buy groceries");
            assertThat(task.getStatus()).isEqualTo("done");
            assertThat(task.getCreatedAt()).isNotNull();
        });
    }

    @Test
    void updateTaskChangesDescriptionAndTouchesUpdatedAt() throws Exception {
        manager.addTask("Old");
        Task task = manager.getTasks().get(0);
        var before = task.getUpdatedAt();
        Thread.sleep(5);

        manager.updateTask(1, "New");

        assertThat(task.getDescription()).isEqualTo("New");
        assertThat(task.getUpdatedAt()).isAfter(before);
    }

    @Test
    void deleteTaskRemovesOnlyTheMatchingTask() {
        manager.addTask("A");
        manager.addTask("B");
        manager.addTask("C");

        manager.deleteTask(1);

        assertThat(manager.getTasks()).extracting(Task::getDescription).containsExactly("B", "C");
        assertThat(new TaskManager(file).getTasks()).hasSize(2);
    }

    @Test
    void idsStayUniqueAfterDeletion() {
        manager.addTask("A");
        manager.addTask("B");
        manager.deleteTask(1);

        manager.addTask("C");

        assertThat(manager.getTasks()).extracting(Task::getId).containsExactly(2, 3);
    }

    @Test
    void unknownIdReportsTaskNotFound() {
        String out = captureOutput(() -> {
            manager.updateTask(42, "x");
            manager.deleteTask(42);
            manager.markStatus(42, "done");
        });

        assertThat(out.lines()).containsExactly("Task not found", "Task not found", "Task not found");
    }

    @Test
    void listTasksFiltersByStatus() {
        manager.addTask("Todo task");
        manager.addTask("Done task");
        manager.markStatus(2, "done");

        String done = captureOutput(() -> manager.listTasks("done"));
        String all = captureOutput(() -> manager.listTasks("all"));

        assertThat(done).contains("Done task").doesNotContain("Todo task");
        assertThat(all.lines()).hasSize(2);
    }

    private static String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }
}
