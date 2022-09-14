package ploopy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import ploopy.task.Task;
import ploopy.task.TaskList;

/**
 * Creates, loads from, writes to file to store
 * tasks so the user may preserve data in their hard drive.
 */
public class Storage {
    private static final String FOLDER_PATH = "./data/";
    private static final String FILE_NAME = "PloopyDatabase.txt";
    private static final String FILE_PATH = FOLDER_PATH + FILE_NAME;
    private static File folder;
    private static File file;

    /**
     * Constructor that takes in a UI object
     *
     * @throws PloopyException If a file storage error occurs.
     */
    public Storage() throws PloopyException {
        folder = new File(FOLDER_PATH);
        file = new File(FILE_PATH);
        //this.taskList = taskList;
        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch (SecurityException e) {
                throw new PloopyException("IO");
            }
        }

        try {
            boolean successful = !file.createNewFile();
        } catch (IOException e) {
            throw new PloopyException("IO");
        }
    }

    /**
     * Formats the provided task, so it can be written to the hard drive.
     *
     * @param task Task to be formatted.
     * @return Formatted string representing the task.
     */
    public String formatLineToWrite(Task task) {
        final String sep = "_";
        String type = task.getType();
        String done = task.isDone() ? "1" : "0";
        String name = task.getName();
        String date = task.getDateForFileWrite();
        int priority = task.getPriorityForFile();
        String line = String.format("%s%s%s%s%s",
                type, sep, done, sep, name);
        if (type.equals("T")) {
            line += sep + priority;
        } else {
            line += sep + date + sep + priority;
        }
        return line;
    }

    /**
     * Opens storage file to read from it and add tasks to the taskList.
     *
     * @param taskList TaskList the tasks are being added to.
     * @throws PloopyException If an error occurs during the file opening or reading.
     */
    public void loadFile(TaskList taskList) throws PloopyException {
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNext()) {
                taskList.addTasksFromFile(fileReader.nextLine());
            }
        } catch (IOException e) {
            throw new PloopyException("IO");
        }
    }

    /**
     * Rewrites file from beginning using updated taskList.
     *
     * @param taskList Updated list of tasks to read from.
     * @throws PloopyException If an error occurs during the file opening or reading.
     */
    public void rewriteFile(ArrayList<Task> taskList) throws PloopyException {
        try {
            FileWriter fileDelete = new FileWriter(FILE_PATH, false);
            fileDelete.write("");
            fileDelete.close();
            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            for (Task task : taskList) {
                fileWriter.write(formatLineToWrite(task) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new PloopyException("IO");
        }
    }

    /**
     * Adds a new task to storage file.
     *
     * @param task Task to be stored.
     * @throws PloopyException If an error occurs during the file opening or reading.
     */
    public void writeToFile(Task task) throws PloopyException {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            fileWriter.write(formatLineToWrite(task) + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new PloopyException("IO");
        }
    }
}
