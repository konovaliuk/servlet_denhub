package ua.kpi.pm_system.services;

import ua.kpi.pm_system.dao.DaoFactory;
import ua.kpi.pm_system.dao.interfaces.ITaskDao;
import ua.kpi.pm_system.dao.interfaces.IUserDao;
import ua.kpi.pm_system.dto.TaskDto;
import ua.kpi.pm_system.entities.*;
import ua.kpi.pm_system.exceptions.ServiceException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TaskService {
    private final ITaskDao taskDao = DaoFactory.getTaskDao();
    private final IUserDao userDao = DaoFactory.getUserDao();

    public Task createTask(String title, String instructions, Date dateCreated, Date deadline, long idProject,
                           long idCreator, long idAssigned, TaskPriority taskPriority)  throws ServiceException{
        Task task = null;
        try {
            task = taskDao.create(title, instructions, dateCreated, deadline, idProject, idCreator, idAssigned, taskPriority);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (task == null) {
            throw new ServiceException("Something go wrong.");
        }
        return task;
    }

    public void updateTask(long taskId, String title, String instructions, Date deadline, long idAssigned,
                           TaskPriority taskPriority, TaskStatus taskStatus) throws ServiceException {
        try {
            Task task = taskDao.findTaskById(taskId);
            if (task == null) {
                throw new ServiceException("Task with id=" + taskId + " doesn't exist.");
            }
            task.setTitle(title);
            task.setInstructions(instructions);
            task.setDeadline(deadline);
            task.setIdAssigned(idAssigned);
            task.setTaskStatus(taskStatus);
            task.setTaskPriority(taskPriority);
            taskDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TaskDto getTaskDto(Task task) {
        TaskDto taskDto = null;
        try {
            String usernameCreator = userDao.findById(task.getIdCreator()).getUsername();
            String usernameAssigned = userDao.findById(task.getIdAssigned()).getUsername();
            taskDto = new TaskDto(task.getId(), task.getTitle(), task.getInstructions(),
                    task.getDateCreated(), task.getDeadline(), task.getIdCreator(), usernameCreator, task.getIdAssigned(), usernameAssigned,
                    task.getTaskPriority(), task.getTaskStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskDto;
    }

    public TaskDto getTaskById(long id) {
        TaskDto taskDto = null;
        try {
            Task task = taskDao.findTaskById(id);
            taskDto = getTaskDto(task);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskDto;
    }

    public Map<String, List<TaskDto>> getProjectTasks(Project currentProject, User currentUser, boolean isManager) {
        List<Task> projectTasks = new ArrayList<>();
        Map<String, List<TaskDto>> taskGrid = new HashMap<>();
        try {
            if (isManager || currentProject.isShowAll()) {
                projectTasks = taskDao.projectTasks(currentProject.getId());
            }
            else {
                projectTasks = taskDao.userProjectTasks(currentProject.getId(), currentUser.getId());
            }
            for (Task task: projectTasks) {
                String status = task.getTaskStatus().toString().toLowerCase();
                if (!taskGrid.containsKey(status)) {
                    taskGrid.put(status, new ArrayList<>());
                }
                taskGrid.get(status).add(getTaskDto(task));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskGrid;
    }

    public void deleteTask(long taskId) throws ServiceException{
        Task task = null;
        try{
            task = taskDao.findTaskById(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (task == null) {
            throw new ServiceException("Task with id=" + taskId + " doesn't exist.");
        }
        try {
            taskDao.delete(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
