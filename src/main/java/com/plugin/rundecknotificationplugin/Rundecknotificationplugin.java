package com.plugin.rundecknotificationplugin;

import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import java.util.*;

@Plugin(service="Notification",name="rundecknotificationplugin")
@PluginDescription(title="RundeckNotificationPlugin", description="My plugin description")
public class Rundecknotificationplugin implements NotificationPlugin{

    @PluginProperty(name = "example",title = "Example String",description = "Example description")
    private String example;

    public boolean postNotification(String trigger, Map executionData, Map config) {
        System.err.printf("Trigger %s fired for %s, configuration: %s",trigger,executionData,config);
        System.err.println();
        System.err.printf("Local field example is: %s",example);
        return true;
    }

}