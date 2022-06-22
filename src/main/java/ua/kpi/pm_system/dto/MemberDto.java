package ua.kpi.pm_system.dto;

public class MemberDto {
    private String username;
    private boolean manager;
    private long userId;
    private long projectId;

    public MemberDto(String username, boolean manager, long userId, long projectId) {
        this.username = username;
        this.manager = manager;
        this.userId = userId;
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }
}
