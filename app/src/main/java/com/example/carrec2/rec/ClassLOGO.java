package com.example.carrec2.rec;

import android.graphics.Bitmap;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

public class ClassLOGO extends Thread{
    public static Module module = null;
    public static Bitmap bitmap_plate;
    public static TextView textView;
    @Override
        public void run() {
            Bitmap bmp1 = MyUtils.scaleBitmap(bitmap_plate, 112, 112);
            //            Utile.bitmapToFloatBuffer(bmp1,  112, 112,  floatBuffer, 0);
            //            Utile.bitmapToFloatBuffer(bmp1,0,0,112,112,TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,TensorImageUtils.TORCHVISION_NORM_STD_RGB,);
            //            final Tensor inputTensor =  Tensor.fromBlob(floatBuffer, new long[]{1, 1, 112, 112});
            assert bmp1 != null;
            final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bmp1,
                    TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);
            //            MyUtils.saveBitmap(Environment.getExternalStorageDirectory().getPath()+"logo",bmp1);
            assert module != null;
            Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
            final float[] scores = outputTensor.getDataAsFloatArray();
            int[] maxx = MyUtils.numMax(scores, 94);
            MyUtils.showTextView(textView,MyUtils.classes[maxx[0]]);
    }
    public static void go(){
        Bitmap bmp1 = MyUtils.scaleBitmap(bitmap_plate, 112, 112);
        //            Utile.bitmapToFloatBuffer(bmp1,  112, 112,  floatBuffer, 0);
        //            Utile.bitmapToFloatBuffer(bmp1,0,0,112,112,TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,TensorImageUtils.TORCHVISION_NORM_STD_RGB,);
        //            final Tensor inputTensor =  Tensor.fromBlob(floatBuffer, new long[]{1, 1, 112, 112});
        assert bmp1 != null;
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bmp1,
                TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);
        //            MyUtils.saveBitmap(Environment.getExternalStorageDirectory().getPath()+"logo",bmp1);
        assert module != null;
        Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
        final float[] scores = outputTensor.getDataAsFloatArray();
        int[] maxx = MyUtils.numMax(scores, 94);
        MyUtils.showTextView(textView,MyUtils.classes[maxx[0]]);
        MyUtils.logo_out=MyUtils.classes[maxx[0]];
    }
}
