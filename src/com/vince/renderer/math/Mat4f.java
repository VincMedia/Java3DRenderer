package com.vince.renderer.math;

public class Mat4f {
    public float[] m = new float[16];

    public Mat4f() { identity(); }

    public Mat4f identity() {
        for(int i=0;i<16;i++) m[i]=0;
        m[0]=m[5]=m[10]=m[15]=1;
        return this;
    }

    public static Mat4f translation(float x,float y,float z){
        Mat4f r=new Mat4f();
        r.m[12]=x; r.m[13]=y; r.m[14]=z;
        return r;
    }

    public static Mat4f rotationY(float angle){
        Mat4f r=new Mat4f();
        float c=(float)Math.cos(angle), s=(float)Math.sin(angle);
        r.m[0]=c;  r.m[2]=s;
        r.m[8]=-s; r.m[10]=c;
        return r;
    }

    public static Mat4f perspective(float fov,float aspect,float near,float far){
        Mat4f r=new Mat4f();
        float f=(float)(1.0/Math.tan(fov/2.0));
        r.m[0]=f/aspect;
        r.m[5]=f;
        r.m[10]=(far+near)/(near-far);
        r.m[11]=-1;
        r.m[14]=(2*far*near)/(near-far);
        r.m[15]=0;
        return r;
    }

    public Mat4f mul(Mat4f o){
        Mat4f r=new Mat4f();
        for(int row=0;row<4;row++){
            for(int col=0;col<4;col++){
                r.m[col+row*4] =
                    m[row*4]*o.m[col] +
                    m[row*4+1]*o.m[col+4] +
                    m[row*4+2]*o.m[col+8] +
                    m[row*4+3]*o.m[col+12];
            }
        }
        return r;
    }

    public static Mat4f rotationX(float angle) {
        Mat4f r = new Mat4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);

        r.m[5]  = c;
        r.m[6]  = s;
        r.m[9]  = -s;
        r.m[10] = c;

        return r;
    }

    public static Mat4f rotationZ(float angle) {
        Mat4f r = new Mat4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);

        r.m[0] = c;
        r.m[1] = s;
        r.m[4] = -s;
        r.m[5] = c;

        return r;
    }

    public static Mat4f scale(float x, float y, float z) {
        Mat4f r = new Mat4f();

        r.m[0]  = x;
        r.m[5]  = y;
        r.m[10] = z;

        return r;
    }


}
