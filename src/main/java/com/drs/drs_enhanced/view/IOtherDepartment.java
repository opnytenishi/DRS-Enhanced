package com.drs.drs_enhanced.view;

import com.drs.drs_enhanced.model.User;

public interface IOtherDepartment {

    public void setLoggedInUser(User loggedInUser);

    public void handleMarkAsCompleted();

    public void handleLogoutFrom_department();

    public void handleReload();

    public void handleNextIncident();

    public void handlePreviousIncident();
}
