package org.likely.mario;

import java.awt.image.BufferedImage;

public class Obstruction {
    //x轴y轴坐标
    private int x;
    private int y;
    //类型和初始类型
    private int type;
    private int startType;
    //显示图片
    private BufferedImage showImage = null;

    //构造方法
    public Obstruction(int x,int y,int type){
        this.x = x;
        this.y = y;
        this.type = type;
        setImage();
    }


    //重置方法
    public void reset(){
        //先修改类型为原始的类型
        this.type = startType;
        //改变显示图片
        this.setImage();
    }
    //根据类型改变显示图片
    public void setImage(){
        showImage = StaticValue.allObstructionImage.get(type);
    }




}
