package com.example.carrec2.rec;

import android.graphics.Bitmap;

import com.example.carrec2.ui.main_fake.fake.FakeFragment;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;
import static org.opencv.core.Core.FILLED;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.putText;
import static org.opencv.imgproc.Imgproc.rectangle;

public class ClassYOLO_Plate {
    public static Net net=null;
    private static int inpWidth = 416;
    private static int inpHeight = 416;
    private static float confThreshold = 0.3f;
    private static float nmsThreshold = 0.45f;
    private static ArrayList<String> classes = new ArrayList<>();
    static String classesFile = "coco.names";
    public static void init(){
        readClasses(classes, classesFile);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        net.setPreferableTarget(Dnn.DNN_TARGET_CPU);
    }
    public static Mat Go(Mat re){

        Mat blob = Dnn.blobFromImage(re, 1 / 255.0, new Size(inpWidth, inpHeight), new Scalar(0, 0, 0), true, false);

        Mat draw_re=re.clone();
//        Mat re=dst.clone();
        net.setInput(blob);
        List<Mat> outs = new ArrayList<>();
        beforeForward = System.currentTimeMillis();
        net.forward(outs, getOutputsNames(net));
        afterForward = System.currentTimeMillis();
        Outtime();
        start = System.currentTimeMillis();
//        System.out.println("recNu_MainAAAAAAA="+recNu);
        postprocess(draw_re, outs);
        afterpostpro = System.currentTimeMillis();

        int w = draw_re.width(), h = draw_re.height();
        MyUtils.image_out = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(draw_re, MyUtils.image_out);
        return draw_re;

    }
    private static void postprocess(Mat frame, List<Mat> outs) {
        List<Integer> classIds = new ArrayList<>();
        List<Float> confidences = new ArrayList<>();
        List<Rect> boxes = new ArrayList<>();
        List<Float> objconf = new ArrayList<>();
        for (int i = 0; i < outs.size(); ++i) {
            for (int j = 0; j < outs.get(i).rows(); ++j) {
                Mat scores = outs.get(i).row(j).colRange(5, outs.get(i).row(j).cols());
                Core.MinMaxLocResult r = Core.minMaxLoc(scores);
                if (r.maxVal > confThreshold) {
                    Mat bb = outs.get(i).row(j).colRange(0, 5);
                    float[] data = new float[1];
                    bb.get(0, 0, data);

                    int centerX = (int) (data[0] * frame.cols());

                    bb.get(0, 1, data);

                    int centerY = (int) (data[0] * frame.rows());

                    bb.get(0, 2, data);

                    int width = (int) (data[0] * frame.cols());

                    bb.get(0, 3, data);

                    int height = (int) (data[0] * frame.rows());

                    int left = centerX - width / 2;
                    int top = centerY - height / 2;

                    bb.get(0, 4, data);
                    objconf.add(data[0]);

                    confidences.add((float) r.maxVal);
                    classIds.add((int) r.maxLoc.x);
                    boxes.add(new Rect(left, top, width, height));
                }
            }
        }
        MatOfRect boxs = new MatOfRect();

        boxs.fromList(boxes);
        MatOfFloat confis = new MatOfFloat();
        confis.fromList(objconf);
        MatOfInt idxs = new MatOfInt();
        Dnn.NMSBoxes(boxs, confis, confThreshold, nmsThreshold, idxs);
        Rect box_car = new Rect(0,0,0,0);
        Rect box_plate=new Rect(0,0,0,0);
        Float box_car_max_confidences= (float) 0,box_plate_max_confidences= (float) 0;
        if (idxs.total() > 0) {
            int[] indices = idxs.toArray();
            for (int idx : indices) {
                Rect box = boxes.get(idx);
                if (0 == classIds.get(idx)) {
                    if(box_plate_max_confidences<confidences.get(idx)){
                        box_plate_max_confidences=confidences.get(idx);
                        box_plate=box;
                    }
                }else if(1 == classIds.get(idx)){
                    if(box_car_max_confidences<confidences.get(idx)){
                        box_car_max_confidences=confidences.get(idx);
                        box_car=box;
                    }
                }
            }
            if(box_car_max_confidences>0){
                MyUtils.Advanced_Car_recognition(box_car);
                drawPred(0, box_car_max_confidences, box_car.x, box_car.y, box_car.x + box_car.width,
                        box_car.y + box_car.height, frame);
            }
            if(box_plate_max_confidences>0){
                MyUtils.Advanced_Plate_recognition(box_plate);
//                int y = Math.max(box_plate.y - box_plate.height * 4, 0);
//                drawPred(-1, box_plate_max_confidences, box_plate.x, y, box_plate.x + box_plate.width, box_plate.y, frame);
                drawPred(0, box_plate_max_confidences, box_plate.x, box_plate.y, box_plate.x + box_plate.width,
                        box_plate.y + box_plate.height, frame);
            }
        }

    }

    private static List<String> getOutputsNames(Net net) {
        ArrayList<String> names = new ArrayList<>();
        if (names.size() == 0) {
            //Get the indices of the output layers, i.e. the layers with unconnected outputs
            List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
            //get the names of all the layers in the network
            List<String> layersNames = net.getLayerNames();

            // Get the names of the output layers in names
            for (int i = 0; i < outLayers.size(); ++i) {
                String layer = layersNames.get(outLayers.get(i).intValue() - 1);
                names.add(layer);
            }
        }
        return names;
    }

    private static long start = System.currentTimeMillis();
    private static long afterForward,afterpostpro,beforeForward;//获取结束时间
    private static void Outtime(){
        try {
            long diff = afterForward - beforeForward;//转换为秒数
            long diff2 = afterpostpro - start;//转换为秒数
            long out = beforeForward - afterpostpro;//转换为秒数
            System.out.println("time spand : " + diff+"   @@@  postTime: "+diff2+"   @@@  out: "+out);
        } catch (Exception e) {
            System.out.println("Got an exception!");
        }
    }
    private static void drawPred(int classId, float conf, int left, int top, int right, int bottom, Mat frame) {
        //Draw a rectangle displaying the bounding box
        rectangle(frame, new Point(left, top), new Point(right, bottom), new Scalar(255, 178, 50), 3);

        //Get the label for the class name and its confidence
        String label = String.format("%.2f", conf);
        if (classes.size() > 0) {
            if(classId==-1){
                label="";
            }else{
                label = classes.get(classId) + ":" + label;
            }
            System.out.println(label);
        }

        //Display the label at the top of the bounding box
        int[] baseLine = new int[1];
        Size labelSize = Imgproc.getTextSize(label, FONT_HERSHEY_SIMPLEX, 0.5, 1, baseLine);
        top = Math.max(top, (int) labelSize.height);
        rectangle(frame, new Point(left, top - round(1.5 * labelSize.height)),
                new Point(left + round(1.5 * labelSize.width), top + baseLine[0]), new Scalar(255, 255, 255), FILLED);
        putText(frame, label, new Point(left, top), FONT_HERSHEY_SIMPLEX, 0.75, new Scalar(0, 0, 0), 1);
    }
    private static void readClasses(ArrayList<String> classes, String file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(MyUtils.context.getAssets().open(file)))) {

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                classes.add(mLine);
            }
        } catch (IOException e) {
            //log the exception
        }
        //log the exception
    }
}
