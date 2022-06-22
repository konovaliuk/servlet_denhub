package ua.kpi.pm_system.dto;

import ua.kpi.pm_system.entities.TaskPriority;
import ua.kpi.pm_system.entities.TaskStatus;

import java.sql.Date;

public class TaskDto {
    private long id;
    private String title;
    private String instructions;
    private Date dateCreated;
    private Date deadline;
    private long idCreator;
    private String usernameCreator;
    private long idAssigned;
    private String usernameAssigned;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;

    public TaskDto(long id, String title, String instructions, Date dateCreated, Date deadline, long idCreator, String usernameCreator,
                   long idAssigned, String usernameAssigned, TaskPriority taskPriority, TaskStatus taskStatus) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.dateCreated = dateCreated;
        this.deadline = deadline;
        this.idCreator = idCreator;
        this.usernameCreator = usernameCreator;
        this.idAssigned = idAssigned;
        this.usernameAssigned = usernameAssigned;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public long getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(long idCreator) {
        this.idCreator = idCreator;
    }

    public String getUsernameCreator() {
        return usernameCreator;
    }

    public void setUsernameCreator(String usernameCreator) {
        this.usernameCreator = usernameCreator;
    }

    public long getIdAssigned() {
        return idAssigned;
    }

    public void setIdAssigned(long idAssigned) {
        this.idAssigned = idAssigned;
    }

    public String getUsernameAssigned() {
        return usernameAssigned;
    }

    public void setUsernameAssigned(String usernameAssigned) {
        this.usernameAssigned = usernameAssigned;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
