package com.woodys.api.plugin.extension

import org.gradle.api.Project

interface OnApiExtensionListener {

    void onPublicationAdded(Project childProject, Publication publication)

}
