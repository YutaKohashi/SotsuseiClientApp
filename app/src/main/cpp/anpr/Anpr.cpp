//
// Created by YutaKohashi on 2017/11/19.
//

#include "Anpr.h"

string Anpr::getFilename(string s) {

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
string Anpr::tostr(const T &t) {
    ostringstream os;
    os << t;
    return os.str();
}

string Anpr::anpr(char *filepath) {
    Mat input_image;

    input_image = imread(filepath, CV_LOAD_IMAGE_COLOR);
    if (!input_image.data) exit(0);

    string filename_whithoutExt = getFilename(filepath);

    //Detect posibles plate regions
    DetectRegions detectRegions;
    detectRegions.setFilename(filename_whithoutExt);
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

string Anpr::anpr() {
    char *targetFilePath = (char *) "/data/data/jp.yuta.kohashi.sotsuseiclientapp/files/testimg.jpg";
    return anpr(targetFilePath);
}
