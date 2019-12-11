package com.okay.api.plugin.extension

import org.gradle.api.Project

interface OnApiExtensionListener {

    void onPublicationAdded(Project childProject, Publication publication)

}
