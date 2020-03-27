package org.likely.mario;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{

    //坐标
    private int x;
    private int y;
    //初始坐标
    private int startX;
    private int startY;
    //类型
    private int type;
    //显示图片
    private BufferedImage showImage;
    //移动方向
    private boolean isLeftOrUp = true;
    //移动的极限范围
    private int upMax = 0;
    private int downMax = 0;
    //定义线程
    private Thread t = new Thread(this);
    //定义图片状态
    private int imageType = 0;
    //定义一个场景对象
    private BackGround bg;

    //创建普通敌人
    public Enemy(int x, int y, boolean isLeft, int type, BackGround bg){
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.isLeftOrUp = isLeft;
        this.type = type;
        this.bg = bg;
        if(type == 1){
            this.showImage = StaticValue.allTriangleImage.get(0);
        }
        t.start();
    }
    //创建食人花
    public Enemy(int x, int y, boolean isUp, int type, int upMax, int downMax, BackGround bg){
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.isLeftOrUp = isUp;
        this.type = type;
        this.upMax = upMax;
        this.downMax = downMax;
        this.bg = bg;
        if(type == 2){
            this.showImage = StaticValue.allFlowerImage.get(0);
        }
        t.start();
    }

    @Override
    public void run() {
        while(true){
            //对于不同的类型要做不同的处理
            if(type == 1){
                if(this.isLeftOrUp){
                    this.x -= 2;
                }else{
                    this.x += 2;
                }
                if(imageType == 0 ) {
                    imageType = 1;
                }else{
                    imageType = 0;
                }

                //定义标记
                boolean canLeft = true;
                boolean canRight = true;
                //定义判断当前Mario是否处于障碍物上的标记
                boolean onLand = false;
                for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
                    Obstruction ob = this.bg.getAllObstruction().get(i);
                    //不允许继续向右移动
                    if (ob.getX() == this.x + 60
                            && (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {  //差一个图片的宽度
                        canRight = false;
                    }
                    //不允许继续向左移动
                    if (ob.getX() == this.x - 60
                            && (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
                        canLeft = false;
                    }
                }
                if(this.isLeftOrUp && !canLeft || this.x == 0){
                    this.isLeftOrUp = false;
                }else if(!this.isLeftOrUp && !canRight || this.x == 840){
                    this.isLeftOrUp = true;
                }


                this.showImage = StaticValue.allTriangleImage.get(imageType);
            }

            if(type == 2){
                if(this.isLeftOrUp){
                    this.y -= 2;
                }else{
                    this.y += 2;
                }
                if(imageType == 0 ) {
                    imageType = 1;
                }else{
                    imageType = 0;
                }
                if(this.isLeftOrUp && this.y == this.upMax){
                    this.isLeftOrUp = false;
                }
                if(!this.isLeftOrUp && this.y == this.downMax){
                    this.isLeftOrUp = true;
                }
                this.showImage = StaticValue.allFlowerImage.get(imageType);

            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset(){

    }
    public void dead(){
        //将显示的图片修改为死亡的图片
        this.showImage = StaticValue.allTriangleImage.get(2);
        this.bg.getAllEnemy().remove(this);
        this.bg.getRemovedEnemy().add(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShowImage() {
        return showImage;
    }

    public BackGround getBg() {
        return bg;
    }

    public int getType() {
        return type;
    }
}
