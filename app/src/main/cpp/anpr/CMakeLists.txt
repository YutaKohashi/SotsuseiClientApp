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

add_library( ${PROJECT_NAME} SHARED ${SRC} )
TARGET_LINK_LIBRARIES( ${PROJECT_NAME}  ${OpenCV_LIBS} )

find_library ( log-lib log )
