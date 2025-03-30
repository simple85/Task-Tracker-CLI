import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final String FILE_NAME = "tasks.json";
    private List<Task> tasks;

    TaskManager() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        try {
        if(!file.exists()) {
            file.createNewFile();
            saveTasks();
        }
            String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            if(content.isEmpty()) {
                content = "[]";
            }
            JSONArray myArray = new JSONArray(content);
            for(int i = 0; i<myArray.length(); i++) {
                tasks.add(Task.fromJSON(myArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.out.println("Could not load due to the error:" + e.getMessage());;
        }

    }

    private void saveTasks() {
        JSONArray myArray = new JSONArray();
        for(Task task : tasks) {
            myArray.put(task.toJSON());
        }
        try {
            Files.write(Paths.get(FILE_NAME), myArray.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Could not save due to the error:" + e.getMessage());;
        }
    }

    void addTask(String description) {
        int id = tasks.size() + 1;
        Task newTask = new Task(id, description);
        tasks.add(newTask);
        saveTasks();
        System.out.println("Task created (ID: " + id + ")");
    }

    void updateTask(int id, String newDescription) {
        for(Task task : tasks) {
            if(task.getId() == id) {
                task.setDescription(newDescription);
                task.setUpdatedAt(LocalDateTime.now());
                saveTasks();
                System.out.println("Task updated");
                return;
            }
        }
        System.out.println("Task not found");
    }
    void deleteTask(int id) {
        for(Task task : tasks) {
            if(task.getId() ==id) {
                tasks.remove(task);
                saveTasks();
                System.out.println("Task deleted");
                return;
            }
            else System.out.println("Task not found");
        }
    }
    void markStatus(int id, String status) {
        for(Task task : tasks) {
            if(task.getId() == id) {
                task.setStatus(status);
                task.setUpdatedAt(LocalDateTime.now());
                saveTasks();
                System.out.println("Task marked as: " + status);
                return;
            }
            else System.out.println("Task not found");
        }
    }
    void listTasks() {
        for(Task task : tasks) {
            System.out.println("ID: " + task.getId() + " | STATUS: " + task.getStatus() +
                    " | TASK: " + task.getDescription() + " | UPDATED: " + task.getUpdatedAt() +
                    " | CREATED: " + task.getCreatedAt());
        }
    }
    void listTasks(String searchStatus) {
        for(Task task : tasks) {
            if(task.getStatus().equals(searchStatus) || searchStatus.equals("all")) {
                System.out.println("ID: " + task.getId() + " | STATUS: " + task.getStatus() +
                        " | TASK: " + task.getDescription() + " | UPDATED: " + task.getUpdatedAt() +
                        " | CREATED: " + task.getCreatedAt());
            }
        }
    }
}
