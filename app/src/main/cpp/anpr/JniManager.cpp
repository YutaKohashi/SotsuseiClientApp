//
// Created by YutaKohashi on 2018/02/06.
//

#include <jni.h>
#include "Anpr.h"

extern "C" JNIEXPORT jstring

JNICALL
Java_jp_yuta_kohashi_sotsuseiclientapp_ui_illegalparking_AnprManager_anpr(JNIEnv *env,
                                                                          jobject /* this */) {

//    Anpr a = Anpr();
    string result = Anpr().anpr();
    return env->NewStringUTF(result.c_str());
}
