
#cmake_minimum_required (VERSION 2.6)
cmake_minimum_required(VERSION 3.4.1)

PROJECT(ANPR)
SET(PROJECT_VERSION "0.1")

# Opencv Package

SET(OpenCV_DIR "../../../../opencv2411/sdk/native/jni")
FIND_PACKAGE( OpenCV REQUIRED )
IF (${OpenCV_VERSION} VERSION_LESS 2.3.0)
	MESSAGE(FATAL_ERROR "OpenCV version is not compatible : ${OpenCV_VERSION}")
ENDIF()

set(PKG_CONFIG_PATH ${PKG_CONFIG_PATH}　../../../../opencv2411/sdk/native/jni)

SET(SRC
 main.cpp
 DetectRegions.cpp
 OCR.cpp
 Plate.cpp
)

#ADD_EXECUTABLE( ${PROJECT_NAME} ${SRC} )
add_library( ${PROJECT_NAME} SHARED ${SRC} )
TARGET_LINK_LIBRARIES( ${PROJECT_NAME}  ${OpenCV_LIBS} )

#ADD_EXECUTABLE( trainSVM trainSVM.cpp )
add_library( trainSVM SHARED trainSVM.cpp )
TARGET_LINK_LIBRARIES( trainSVM  ${OpenCV_LIBS} )

#ADD_EXECUTABLE( trainOCR trainOCR.cpp OCR.cpp )
add_library( trainOCR SHARED trainOCR.cpp OCR.cpp )
TARGET_LINK_LIBRARIES( trainOCR  ${OpenCV_LIBS} )

#ADD_EXECUTABLE( evalOCR evalOCR.cpp OCR.cpp )
add_library( evalOCR SHARED evalOCR.cpp OCR.cpp )
TARGET_LINK_LIBRARIES( evalOCR  ${OpenCV_LIBS} )

SET(RESOURCES
	SVM.xml
	OCR.xml
	test
)
foreach(f ${RESOURCES})
file( COPY ${f} DESTINATION ${CMAKE_CURRENT_BINARY_DIR}/)
endforeach(f)

find_library ( log-lib log )

#add_library ( anpr SHARED main.cpp )
#target_link_libraries ( anpr ${log-lib} )
