package com.assignment.root.movielistingapp.application;

import com.assignment.root.movielistingapp.utils.Constants;
import com.kelltontech.application.BaseApplication;
import com.kelltontech.volley.ext.RequestManager;

/**
 * Created by user on 8/8/17.
 */

public class RequestManagerInitialise extends BaseApplication {
    @Override
    protected void initialize() {
        RequestManager.initializeWith(getApplicationContext(), new RequestManager.Config(Constants.FILE_LOCATION, 5242880, 4));
    }
}
