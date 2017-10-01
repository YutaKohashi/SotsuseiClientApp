package jp.yuta.kohashi.sotsuseiclientapp.ui.notification

/**
 * Author : yutakohashi
 * Project name : SotsuseiClientApp
 * Date : 29 / 09 / 2017
 */

class NotificationHelper private constructor() {

    companion object {
        private var helper: NotificationHelper? = null

        fun instance() {
            helper = NotificationHelper()
        }
    }

    
}