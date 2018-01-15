//
// Created by YutaKohashi on 2017/12/01.
//

#ifndef SOTSUSEICLIENTAPP_ANPR_H
#define SOTSUSEICLIENTAPP_ANPR_H

#include <cv.h>
#include <highgui.h>
#include <cvaux.h>
#include <ml.h>
#include <iostream>
#include <jni.h>
#include <string>
#include <vector>
#include "OCR.h"
#include "DetectRegions.h"


using namespace std;
using namespace cv;

#define OCR_FILE_PATH   "/data/data/jp.yuta.kohashi.sotsuseiclientapp/files/OCR.xml"
#define SVM_FILE_PATH    "/data/data/jp.yuta.kohashi.sotsuseiclientapp/files/SVM.xml"

class Anpr{
public:
    string anpr();
    string anpr(char *filepath);

private:
    string getFilename(string s);
};


#endif //SOTSUSEICLIENTAPP_ANPR_H
