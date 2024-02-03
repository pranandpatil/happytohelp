package ap.service.happytohelp

import android.app.Application
import android.content.Context

class HappyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //FirebaseMessaging.getInstance().subscribeToTopic("Happy")

    }
}