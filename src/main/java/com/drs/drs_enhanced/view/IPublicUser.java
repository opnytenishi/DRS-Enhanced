package com.drs.drs_enhanced.view;

import com.drs.drs_enhanced.model.User;


public interface IPublicUser {
    public void setLoggedInUser(User loggedInUser);
    public void handleRequestHelpButtonClick();
    public void handleLogoutFrom_public_user();
    public void handleReload();
    public void resetFields();
}
