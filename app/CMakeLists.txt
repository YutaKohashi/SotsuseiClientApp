cmake_minimum_required(VERSION 3.4.1)

add_library( native-lib
             SHARED
             src/main/cpp/native-lib.cpp )

find_library( log-lib
              log )

target_link_libraries( native-lib
                       ${log-lib} )

include_directories(./opencv2411/sdk/native/jni/include)
add_library( lib_opencv SHARED IMPORTED )
set_target_properties(lib_opencv PROPERTIES IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java.so)

include_directories(src/main/cpp/anpr)
add_subdirectory(src/main/cpp/anpr)