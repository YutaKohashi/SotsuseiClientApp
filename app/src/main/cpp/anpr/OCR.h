//
// Created by YutaKohashi on 2017/12/01.
//

#ifndef OCR_h
#define OCR_h

#include <string.h>
#include <vector>

#include "Plate.h"

#include <cv.h>
#include <highgui.h>
#include <cvaux.h>
#include <ml.h>

using namespace std;
using namespace cv;


#define HORIZONTAL    1
#define VERTICAL    0

class CharSegment {
public:
    CharSegment();

    CharSegment(Mat i, Rect p);

    Mat img;
    Rect pos;
};

class OCR {
public:
    string filename;
    static const int numCharacters;
    static const char strCharacters[];
    OCR(string trainFile);
    OCR();
    string run(Plate *input);
    int charSize;
    Mat preprocessChar(Mat in);
    int classify(Mat f);
    void train(Mat trainData, Mat trainClasses, int nlayers);
    Mat features(Mat input, int size);

private:
    vector<CharSegment> segment(Plate input);
    Mat getVisualHistogram(Mat *hist, int type);
    Mat ProjectedHistogram(Mat img, int t);
    bool verifySizes(Mat r);
    CvANN_MLP ann;
    CvKNearest knnClassifier;
    int K;
};

#endif
