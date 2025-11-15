package com.vince.renderer.math;

public class Vec3f {
    public float x, y, z;
    public Vec3f() { this(0,0,0); }
    public Vec3f(float x, float y, float z){ this.x=x; this.y=y; this.z=z; }

    public Vec3f add(Vec3f o){ return new Vec3f(x+o.x, y+o.y, z+o.z); }
    public Vec3f sub(Vec3f o){ return new Vec3f(x-o.x, y-o.y, z-o.z); }
    public Vec3f scale(float s){ return new Vec3f(x*s, y*s, z*s); }

    public static Vec3f cross(Vec3f a, Vec3f b){
        return new Vec3f(
            a.y*b.z - a.z*b.y,
            a.z*b.x - a.x*b.z,
            a.x*b.y - a.y*b.x
        );
    }

    public static float dot(Vec3f a, Vec3f b){ return a.x*b.x + a.y*b.y + a.z*b.z; }
    public Vec3f normalize(){
        float len=(float)Math.sqrt(x*x+y*y+z*z);
        return len==0? new Vec3f(): new Vec3f(x/len,y/len,z/len);
    }
    
    @Override
    public String toString() {
    	return "("+x+", "+y+", "+z+")";
    }
}
