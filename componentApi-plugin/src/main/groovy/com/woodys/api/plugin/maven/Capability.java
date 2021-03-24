package com.woodys.api.plugin.maven;

import org.gradle.api.Incubating;

@Incubating
public interface Capability {
    String getGroup();

    String getName();

    String getVersion();
}
