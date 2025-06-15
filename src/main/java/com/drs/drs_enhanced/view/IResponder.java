package com.drs.drs_enhanced.view;

public interface IResponder {
    public void resetFields();
    public void handleLogoutFrom_responder();
    public void handleAssignTeamToIncident();
    public void handleAddNewSupply();
    public void handleSendAlertToRegion();
    public void handleRemoveAlertFromRegion();
    public void handleassign_suppliessuccess();
    public void handleSendNearbyShelter();
    public void handleSendNotification();
}
