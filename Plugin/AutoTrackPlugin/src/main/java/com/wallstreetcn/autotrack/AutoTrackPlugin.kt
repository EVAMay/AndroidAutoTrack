package com.wallstreetcn.autotrack

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.kronos.plugin.base.utils.getTaskNamePrefix
import com.kronos.plugin.base.utils.getVariantManager
import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoTrackPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val isApp = project.plugins.hasPlugin(AppPlugin::class.java)
        if (isApp) {
            val appExtension = project.extensions.getByType(AppExtension::class.java)
            val scanTransform = DataScanTransform()
            appExtension.registerTransform(scanTransform)
            val scopeManager = getVariantManager(project)
            scopeManager.variantScopes.forEach {
                val taskName = it.getTaskName(scanTransform.getTaskNamePrefix())
                val task = project.tasks.getByName(taskName)
                appExtension.registerTransform(NewAutoTackTransform(), task)
            }
        }
    }
}