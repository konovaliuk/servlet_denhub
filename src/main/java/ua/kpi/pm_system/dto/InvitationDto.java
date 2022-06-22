package ua.kpi.pm_system.dto;

public class InvitationDto {
    private long invitationId;
    private String inviterUsername;
    private String projectTitle;

    public InvitationDto(String inviterUsername, String projectTitle, long invitationId) {
        this.invitationId = invitationId;
        this.inviterUsername = inviterUsername;
        this.projectTitle = projectTitle;
    }

    public long getInvitationId() {
        return invitationId;
    }

    public String getInviterUsername() {
        return inviterUsername;
    }

    public void setInviterUsername(String inviterUsername) {
        this.inviterUsername = inviterUsername;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
