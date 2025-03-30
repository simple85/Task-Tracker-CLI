public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: task-cli <command> [arguments]\nType /help to list all commands");
            return;
        }

        TaskManager taskManager = new TaskManager();
        String command = args[0];

        switch (command) {
            case "add":
                if(args.length !=2) {
                    System.out.println("Command failed.\nCorrect usage: task-cli add [\"enter some task description here\"]");
                    return;
                }
                taskManager.addTask(args[1]);
                break;
                
            case "update":
                if(args.length !=3) {
                    System.out.println("Command failed.\nCorrect usage: task-cli update [task-id] [\"enter new task description here\"]");
                    return;
                }
                taskManager.updateTask(Integer.parseInt(args[1]), args[2]);
                break;
                
            case "delete":
                if(args.length !=2) {
                    System.out.println("Command failed.\nCorrect usage: task-cli delete [task-id]");
                    return;
                }
                taskManager.deleteTask(Integer.parseInt(args[1]));
                break;
                
            case "mark-in-progress":
                if(args.length!=2) {
                    System.out.println("Command failed.\nCorrect usage: task-cli mark-in-progress [task-id]");
                    return;
                }
                taskManager.markStatus(Integer.parseInt(args[1]), args[0]);
                break;
                
            case "mark-done":
                if(args.length!=2) {
                    System.out.println("Command failed.\nCorrect usage: task-cli mark-done [task-id]");
                    return;
                }
                taskManager.markStatus(Integer.parseInt(args[1]), args[0]);
                break;
                
            case "list":
                if(args.length>2) {
                    System.out.println("Command failed.\nCorrect usage: task-cli list\nCorrect usage: task-cli list [task-status]");
                    return;
                }
                else if(args.length==1) {
                    taskManager.listTasks();
                    return;
                }
                taskManager.listTasks(args[1]);
                break;
                
            case "/help":
                if(args.length>1) {
                    System.out.println("Command failed.\nCorrect usage: task-cli /help");
                    return;
                }
                System.out.println("""
                        task-cli add ["enter some task description here"] | creates a task with the entered description\s
                        task-cli update [task-id] ["enter new task description here"] | updates the description of the entered task id\s
                        task-cli delete [task-id] | deletes the task belonging to entered task id\s
                        task-cli mark-in-progress [task-id] | updates the status of the task to "in-progress" belonging to entered task id\s
                        task-cli mark-done [task-id] | updates the status of the task to "done" belonging to entered task id\s
                        task-cli list or task-cli list all | lists all tasks\s
                        task-cli list [task-status] | lists tasks that matches with entered task-status""");
                break;

            default:
                System.out.println("Unknown command " + command);
                
        }
    }
}
