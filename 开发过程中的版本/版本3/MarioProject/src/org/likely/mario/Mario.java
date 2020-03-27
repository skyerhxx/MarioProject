package org.likely.mario;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    //坐标
    private int x;
    private int y;

    //定义一个场景对象，保存当前Mario所在的场景
    private BackGround bg;

    //加入线程
    private Thread t = null; //在构造方法中实现这个对象

    //定义一个速度属性
    private int xmove = 0;


    //状态
    private String status;
    //显示的图片
    private BufferedImage showImage;
    //生命数和分数
    private int score;
    private int life;

    //当前移动中显示的图片索引
    private int moving = 0;

    //构造方法
    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        //初始化Mario图片
        this.showImage = StaticValue.allMarioImage.get(0);
        this.score = 0;
        this.life = 3;

        t = new Thread(this);
        t.start();

        this.status = "right--standing";

    }

    public void leftMove() {
        //改变速度
        xmove = -5;
        //改变当前状态
        this.status = "left--moving";
    }

    public void rightMove() {
        //改变速度
        xmove = 5;
        //改变当前状态
        this.status = "right--moving";
    }

    public void leftStop() {
        this.xmove = 0;
        this.status = "left--standing";
    }

    public void rightStop() {
        this.xmove = 0;
        this.status = "right--standing";
    }

    public void jump() {

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

    public void setBg(BackGround bg) {
        this.bg = bg;
    }

    public void run() {
        while (true) {
            //判断当前mario是否与障碍物碰撞
            //定义标记
            boolean canLeft = true;
            boolean canRight = true;
            for (int i = 0; i < this.bg.getAllObstruction().size(); i++) {
                Obstruction ob = this.bg.getAllObstruction().get(i);
                //不允许继续向右移动
                if (ob.getX() == this.x + 60 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)) {  //差一个图片的宽度
                    canRight = false;
                }
                //不允许继续向左移动
                if (ob.getX() == this.x - 60 && (ob.getY() + 60 > this.y && ob.getY() - 60 < this.y)) {
                    canLeft = false;
                }
            }

            if ((canLeft && xmove < 0) || (canRight && xmove > 0)) {
                //改变坐标
                x += xmove;

            }

            //定义一个图片取得的初始索引数
            int temp = 0;
            //当前为面向左
            if (this.status.indexOf("left") != -1) {
                temp += 5;
            }


            //判断当前是否移动
            if (this.status.indexOf("moving") != -1) {
                temp += this.moving;
                moving++;
                if (moving == 4) {
                    moving = 0;
                }
            }
            //改变显示图片
            this.showImage = StaticValue.allMarioImage.get(temp);


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

