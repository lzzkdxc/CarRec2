package com.example.carrec2.rec;

import android.graphics.Bitmap;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

public class ClassCOLOR extends Thread{
    public static Module module = null;
    public static Bitmap bitmap_plate;
    public static TextView textView;
    @Override
    public void run() {
        Bitmap bmp1 = MyUtils.scaleBitmap(bitmap_plate, 224, 224);
        assert bmp1 != null;
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bmp1,
                new float[]{0, 0, 0}, new float[]{1, 1, 1});
        final float[] oo = inputTensor.getDataAsFloatArray();
        //            MyUtils.saveBitmap(Environment.getExternalStorageDirectory().getPath()+"logo",bmp1);
        assert module != null;
        Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();

        final float[] scores = outputTensor.getDataAsFloatArray();
        int maxx = f(scores);
        MyUtils.showTextView(textView,MyUtils.COLORclasses[maxx]);
//        System.out.println("recNu_LOGOBBBBB="+nownu);
    }
    static private int f(float[] a){
        float max=-Float.MAX_VALUE;
        int maxnu=0;
//        System.out.println(a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]+" "+a[5]+" "+a[6]);
        for (int i = 0; i < a.length; i++) {
            if(a[i]>max){
                max=a[i];
                maxnu=i;
            }
        }
        return maxnu;
    }
    public static void go(){
        Bitmap bmp1 = MyUtils.scaleBitmap(bitmap_plate, 224, 224);
        assert bmp1 != null;
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bmp1,
                new float[]{0, 0, 0}, new float[]{1, 1, 1});
        final float[] oo = inputTensor.getDataAsFloatArray();
        //            MyUtils.saveBitmap(Environment.getExternalStorageDirectory().getPath()+"logo",bmp1);
        assert module != null;
        Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();

        final float[] scores = outputTensor.getDataAsFloatArray();
        int maxx = f(scores);
        MyUtils.showTextView(textView,MyUtils.COLORclasses[maxx]);
        MyUtils.color_out=MyUtils.COLORclasses[maxx];
    }
}
