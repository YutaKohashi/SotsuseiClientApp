//
// Created by YutaKohashi on 2017/11/19.
//

#include <cv.h>
#include <highgui.h>
#include <cvaux.h>
#include <ml.h>
//
#include <getopt.h>
#include <iostream>
#include <jni.h>
#include <string>
#include <vector>
#include "OCR.h"
#include "DetectRegions.h"

using namespace std;
using namespace cv;


//**
//region anpr
//**
//
string getFilename(string s) {

    char sep = '/';
    char sepExt = '.';

#ifdef _WIN32
    sep = '\\';
#endif

    size_t i = s.rfind(sep, s.length());
    if (i != string::npos) {
        string fn = (s.substr(i + 1, s.length() - i));
        size_t j = fn.rfind(sepExt, fn.length());
        if (i != string::npos) {
            return fn.substr(0, j);
        } else {
            return fn;
        }
    } else {
        return "";
    }
}

template<typename T>
string tostr(const T &t) {
    ostringstream os;
    os << t;
    return os.str();
}

string anpr(char *filepath) {
    int result;
    Mat input_image;

    const string OCR_FILE_PATH = "/data/data/jp.yuta.kohashi.sotsuseiclientapp.debug/files/OCR.xml";
    const string SVM_FILE_PATH = "/data/data/jp.yuta.kohashi.sotsuseiclientapp.debug/files/SVM.xml";

    input_image = imread(filepath, CV_LOAD_IMAGE_COLOR);
    if (!input_image.data) exit(0);

    string filename_whithoutExt = getFilename(filepath);

    //Detect posibles plate regions
    DetectRegions detectRegions;
    detectRegions.setFilename(filename_whithoutExt);
    detectRegions.saveRegions = true;
    vector<Plate> posible_regions = detectRegions.run(input_image);

    //SVM for each plate region to get valid car plates
    //Read file storage.
    FileStorage fs;
    fs.open(SVM_FILE_PATH, FileStorage::READ);
    Mat SVM_TrainingData;
    Mat SVM_Classes;
    fs["TrainingData"] >> SVM_TrainingData;
    fs["classes"] >> SVM_Classes;
    //Set SVM params
    CvSVMParams SVM_params;
    SVM_params.svm_type = CvSVM::C_SVC;
    SVM_params.kernel_type = CvSVM::LINEAR; //CvSVM::LINEAR;
    SVM_params.degree = 0;
    SVM_params.gamma = 1;
    SVM_params.coef0 = 0;
    SVM_params.C = 1;
    SVM_params.nu = 0;
    SVM_params.p = 0;
    SVM_params.term_crit = cvTermCriteria(CV_TERMCRIT_ITER, 1000, 0.01);
    //Train SVM
    CvSVM svmClassifier(SVM_TrainingData, SVM_Classes, Mat(), Mat(), SVM_params);

    //For each possible plate, classify with svm if it's a plate or no
    vector<Plate> plates;
    for (int i = 0; i < posible_regions.size(); i++) {
        Mat img = posible_regions[i].plateImg;
        Mat p = img.reshape(1, 1);
        p.convertTo(p, CV_32FC1);

        int response = (int) svmClassifier.predict(p);
        if (response == 1)
            plates.push_back(posible_regions[i]);
    }

    OCR ocr(OCR_FILE_PATH);
    ocr.saveSegments = true;

    ocr.filename = filename_whithoutExt;
    for (int i = 0; i < plates.size(); i++) {
        Plate plate = plates[i];
        string plateNumber = ocr.run(&plate);
        string licensePlate = plate.str();
        string result = licensePlate + "," + tostr(plate.position.height) + "," +
                        tostr(plate.position.width) + "," + tostr(plate.position.x) + "," +
                        tostr(plate.position.y);
        return result.c_str();
    }
    return "-1";

}

string anpr() {
    char *targetFilePath = (char *) "/data/data/jp.yuta.kohashi.sotsuseiclientapp.debug/files/testimg.jpg";
    return anpr(targetFilePath);
}

//**
//endregion
//**

extern "C" JNIEXPORT jstring

JNICALL
Java_jp_yuta_kohashi_sotsuseiclientapp_ui_illegalparking_AnprManager_anpr(JNIEnv *env,
                                                                          jobject /* this */) {
    string result = anpr();
    return env->NewStringUTF(result.c_str());
}
