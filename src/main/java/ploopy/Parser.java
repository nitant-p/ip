package ploopy;

import ploopy.ui.TextUI;

/**
 * Understands the user's input.
 *
 */
public class Parser {

    /**
     * Interprets the user's input and calls the correct TaskList method.
     *
     * @param input Input provided by User
     * @param taskList Associated taskList for subsequent methods.
     * @throws PloopyException if input is invalid.
     */
    public static String parseInput(String input, TaskList taskList) throws PloopyException {
        if (input.isBlank() || input.isEmpty()) {
            throw new PloopyException("blank");
        }
        String[] inputSequence = input.split(" ");
        String command = inputSequence[0].toLowerCase();
        switch (command) {
        case "mark":
            if (!isIncompleteCommand(input, "mark".length())) {
                assert inputSequence.length > 1 : "isIncompleteCommand is not working";
                return taskList.markTask(Integer.parseInt(inputSequence[1]));
            }
            break;
        case "unmark":
            if (!isIncompleteCommand(input, "unmark".length())) {
                assert inputSequence.length > 1 : "isIncompleteCommand is not working";
                return taskList.unmarkTask(Integer.parseInt(inputSequence[1]));
            }
            break;
        case "list":
            if (input.trim().length() == "list".length()) {
                return taskList.displayList();
            }
            break;
        case "delete":
            if (!isIncompleteCommand(input, "delete".length())) {
                assert inputSequence.length > 1 : "isIncompleteCommand is not working";
                return taskList.deleteTask(Integer.parseInt(inputSequence[1]));
            }
            break;
        case "todo":
            return parseTask("todo", input, inputSequence, taskList);
        case "deadline":
            return parseTask("deadline", input, inputSequence, taskList);
        case "event":
            return parseTask("event", input, inputSequence, taskList);
        case "find":
            if (!isIncompleteCommand(input, "find".length())) {
                assert inputSequence.length > 1 : "isIncompleteCommand is not working";
                return taskList.findTasks(input.split("find ")[1]);
            }
            break;
        case "bye":
            return TextUI.bye();
        default:
            throw new PloopyException("nonsense");

        }
        return null;
    }

    private static String parseTask(String taskType, String fullInput,
                                    String[] inputSequence, TaskList taskList) throws PloopyException {

        String name, date;
        if (!isIncompleteCommand(fullInput, taskType.length())) {
            assert inputSequence.length > 1 : "isIncompleteCommand is not working";
            date = getDate(fullInput, taskType);
            name = getName(fullInput, taskType);
            return taskList.createTask(taskType, name, date);
        } else {
            throw new PloopyException(taskType);
        }
    }

    private static String getDate(String input, String taskType) {
        if (!taskType.equals("todo")) {
            String dateString = input.split(" /")[1];
            String[] separate = dateString.split(" ");
            String result = separate[1] + " " + separate[2];
            assert result.split("/").length == 3 : "Date not correctly extracted";
            return result;
        } else {
            return "";
        }
    }


    private static String getName(String input, String taskType) {
        if (!taskType.equals("todo")) {
            return taskType.split(taskType + " ")[1].split(" /")[0];
        } else {
            return input.split("todo ")[1];
        }
    }

    private static boolean isIncompleteCommand(String command, int size) throws PloopyException {
        assert !(command.isEmpty() || command.isBlank()) : "Input is empty";
        if (command.trim().length() == size) {
            throw new PloopyException(command);
        } else {
            return false;
        }
    }
}
