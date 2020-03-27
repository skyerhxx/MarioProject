package org.likely.mario;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable{
    //x轴y轴坐标
    private int x;
    private int y;

    //线程用来完成游戏结束时降旗的操作
    private Thread t = new Thread(this);

    //类型和初始类型
    private int type;
    private int startType;
    //显示图片
    private BufferedImage showImage = null;
    //当前障碍物所在的场景
    private BackGround bg;


    //构造方法
    public Obstruction(int x,int y,int type,BackGround bg){
        this.x = x;
        this.y = y;
        this.type = type;
        this.startType = type;
        this.bg = bg;
        setImage();
        if(this.type == 11){
            t.start();
        }
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

    public BufferedImage getShowImage() {
        return showImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void run() {
        while(true){
            if(this.bg.isOver()){
                if(this.y < 420){
                    this.y += 5;
                }else{
                    this.bg.setDown(true);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
