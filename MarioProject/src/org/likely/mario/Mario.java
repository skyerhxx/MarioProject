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
    //定义一个垂直方向的速度
    private int ymove = 0;


    //状态
    private String status;
    //显示的图片
    private BufferedImage showImage;
    //生命数和分数
    private int score;
    private int life;

    //当前移动中显示的图片索引
    private int moving = 0;

    //上升的时间
    private int upTime = 0;

    //标记Mario是否死亡
    private boolean isDead = false;

    //标记玩家是否完成了全部的游戏
    private boolean isClear = false;

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
        //如果当前已经是跳跃状态，应该保留原有状态，而不能修改为移动状态
        if (this.status.indexOf("jumping") != -1){
            this.status = "left--jumping";
        }else{
            this.status = "left--moving";
        }
    }

    public void rightMove() {
        //改变速度
        xmove = 5;
        //改变当前状态
        if (this.status.indexOf("jumping") != -1){
            this.status = "right--jumping";
        }else{
            this.status = "right--moving";
        }
    }

    public void leftStop() {
        this.xmove = 0;
        //改变当前状态
        if (this.status.indexOf("jumping") != -1){
            this.status = "left--jumping";
        }else{
            this.status = "left--standing";
        }

    }

    public void rightStop() {
        this.xmove = 0;
        //改变当前状态
        if (this.status.indexOf("jumping") != -1){
            this.status = "right--jumping";
        }else{
            this.status = "right--standing";
        }
    }

    public void jump() {
        if(this.status.indexOf("jumping") == -1){
            if(this.status.indexOf("left") != -1){
                this.status = "left--jumping";
            }
            else{
                this.status = "right--jumping";
            }
            ymove = -5;
            upTime = 36;
        }
    }
    //加入下落的方法
    public void down(){
        if(this.status.indexOf("left") != -1){
            this.status = "left--jumping";
        }
        else{
            this.status = "right--jumping";
        }
        ymove = 5;
    }

    //mario的死亡方法
    public void dead(){
        this.life--;
        if(this.life == 0){
            this.isDead = true;
        }else{
            this.bg.reset();
            this.x = 0;
            this.y = 480;
        }
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isClear() {
        return isClear;
    }

    public void run() {
        while (true) {
            if(this.bg.isFlag() && this.x >= 520){
                this.bg.setOver(true);
                if(this.bg.isDown()){
                    //降旗后，Mario开始向城门移动
                    this.status = "right--moving";
                    if(this.x < 580){
                        //Mario只向右移动
                        x += 5;
                    }else{
                        if(y < 480){
                            //Mario向下移动
                            y += 5;
                        }
                        x += 5;
                        if(x >= 780){
                            //游戏结束，已经完成了全部的场景
                            this.isClear = true;
                        }
                    }
                }else {
                    //如果为最后一个场景，同时mario的x坐标到达了550，则自动控制游戏结束时的移动
                    if (this.y < 420) {
                        this.y += 5;
                    }
                    if (this.y >= 420) {
                        this.y = 420;
                        this.status = "right--standing";
                    }
                }
            }else {
                //判断当前mario是否与障碍物碰撞
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
                        if (ob.getType() != 3) {
                            canRight = false;
                        }
                    }
                    //不允许继续向左移动
                    if (ob.getX() == this.x - 60
                            && (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)) {
                        if (ob.getType() != 3) {
                            canLeft = false;
                        }
                    }
                    //如果Mario当前处在一个障碍物上面
                    if (ob.getY() == this.y + 60
                            && (ob.getX() + 60 > this.x && ob.getX() - 60 < this.x)) {
                        if (ob.getType() != 3) {
                            onLand = true;
                        }
                    }
                    //判断当前mario是否在跳跃中顶到了一个障碍物
                    if (ob.getY() == this.y - 60
                            && (ob.getX() + 50 > this.x && ob.getX() - 50 < this.x)) {
                        //对于砖块的处理
                        if (ob.getType() == 0) {
                            //将该砖块从场景中移除
                            this.bg.getAllObstruction().remove(ob);
                            //将被移除的砖块保存到一个集合中
                            this.bg.getRemovedObstruction().add(ob);
                        }
                        //对于问号和隐藏砖块的处理
                        if ((ob.getType() == 4 || ob.getType() == 3) && upTime > 0) {
                            ob.setType(2);
                            ob.setImage();
                        }


                        upTime = 0;
                    }
                }
                //加入Mario对敌人的判断
                for (int i = 0; i < this.bg.getAllEnemy().size(); i++) {
                    Enemy e = this.bg.getAllEnemy().get(i);

                    if (e.getX() + 50 > this.x && e.getX() - 50 < this.x
                            && (e.getY() + 60 > this.y && e.getY() - 60 < this.y)) {
                        //mario死亡
                        this.dead();
                    }
                    //从正上方落下
                    if (e.getY() == this.y + 60
                            && (e.getX() + 60 > this.x && e.getX() - 60 < this.x)) {
                        if (e.getType() == 1) {
                            e.dead();
                            this.upTime = 10;
                            this.ymove = -5;
                        }
                        //花的话mario死亡
                        else if (e.getType() == 2) {
                            this.dead();

                        }
                    }
                }

                if (onLand && upTime == 0) {
                    if (this.status.indexOf("left") != -1) {
                        if (xmove != 0) {   //横向移动有速度
                            this.status = "left--moving";
                        } else {
                            this.status = "left--standing";
                        }
                    } else {
                        if (xmove != 0) {   //横向移动有速度
                            this.status = "right--moving";
                        } else {
                            this.status = "right--standing";
                        }
                    }
                } else {
                    //表示当前为上升的状态
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        this.down();
                    }
                    y += ymove;
                }

                if (this.y > 600) {
                    this.dead();
                }

                if ((canLeft && xmove < 0) || (canRight && xmove > 0)) {
                    //改变坐标
                    x += xmove;
                    if (x < 0) {
                        x = 0;
                    }
                }
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

            if (this.status.indexOf("jumping") != -1) {
                temp += 4;
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

