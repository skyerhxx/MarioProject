package org.likely.mario;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //当前场景的显示图片
    private BufferedImage bgImage = null;

    //场景的顺序
    private int sort;
    //当前是否为最后一个场景
    private boolean flag;

    //定义一个结束游戏的标记
    private boolean isOver = false;
    //定义降旗结束的标记
    private boolean isDown = false;

    //通过集合来保存
    //全部的敌人
    private List<Enemy> allEnemy = new ArrayList<Enemy>();
    //全部的障碍物
    private List<Obstruction> allObstruction = new ArrayList<Obstruction>();


    //被消灭的敌人
    private List<Enemy> removedEnemy = new ArrayList<Enemy>();
    //被消灭的障碍物
    private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();


    //构造方法
    //sort用来控制场景是哪个场景
    //flag是当前是否为最后一个场景
    public BackGround(int sort, boolean flag){
        this.sort = sort;
        this.flag = flag;
        //最后一个场景要使用firststageend.gif，其他场景使用firststage.gif
        if(flag){
            bgImage = StaticValue.endImage;
        }
        else{
            bgImage = StaticValue.bgImage;
        }
        if(sort == 1){
            //绘制地面
            for(int i=0; i<15 ;i++){
                this.allObstruction.add(new Obstruction(i*60,540,9,this));
            }
            //绘制砖块和问号
            this.allObstruction.add(new Obstruction(120,360,4,this));
            this.allObstruction.add(new Obstruction(300,360,0,this));
            this.allObstruction.add(new Obstruction(360,360,4,this));
            this.allObstruction.add(new Obstruction(420,360,0,this));
            this.allObstruction.add(new Obstruction(480,360,4,this));
            this.allObstruction.add(new Obstruction(540,360,0,this));
            this.allObstruction.add(new Obstruction(420,180,4,this));
            //绘制水管
            this.allObstruction.add(new Obstruction(660,540,6,this));
            this.allObstruction.add(new Obstruction(720,540,5,this));
            this.allObstruction.add(new Obstruction(660,480,8,this));
            this.allObstruction.add(new Obstruction(720,480,7,this));
            //加入隐藏的砖块
            this.allObstruction.add(new Obstruction(660,300,3,this));

            //绘制敌人
            this.allEnemy.add(new Enemy(600,480,true,1,this));
            this.allEnemy.add(new Enemy(690,540,true,2,420,540,this));

        }
        //绘制第二个场景
        if(sort == 2){
            //绘制地面
            for(int i=0; i<15 ;i++) {
                if (i != 10) {
                    this.allObstruction.add(new Obstruction(i * 60, 540, 9,this));
                }
            }
            //绘制水管
            this.allObstruction.add(new Obstruction(60,540,6,this));
            this.allObstruction.add(new Obstruction(120,540,5,this));
            this.allObstruction.add(new Obstruction(60,480,6,this));
            this.allObstruction.add(new Obstruction(120,480,5,this));
            this.allObstruction.add(new Obstruction(60,420,8,this));
            this.allObstruction.add(new Obstruction(120,420,7,this));

            this.allObstruction.add(new Obstruction(240,540,6,this));
            this.allObstruction.add(new Obstruction(300,540,5,this));
            this.allObstruction.add(new Obstruction(240,480,6,this));
            this.allObstruction.add(new Obstruction(300,480,5,this));
            this.allObstruction.add(new Obstruction(240,420,6,this));
            this.allObstruction.add(new Obstruction(300,420,5,this));
            this.allObstruction.add(new Obstruction(240,360,8,this));
            this.allObstruction.add(new Obstruction(300,360,7,this));
        }
        //绘制第三个场景
        if(sort == 3){
            //绘制地面
            for(int i=0; i<15 ;i++) {
                this.allObstruction.add(new Obstruction(i * 60, 540, 9,this));
            }
            this.allObstruction.add(new Obstruction(550,180,11,this));
            this.allObstruction.add(new Obstruction(520,480,2,this));
        }


    }
    //重置方法
    //将所有障碍物和敌人返回到原有坐标，并将其状态也修改
    public void reset(){
        //将已经移除的障碍物和敌人放回到全部的内容中
        this.allEnemy.addAll(this.removedEnemy);
        this.allObstruction.addAll(this.removedObstruction);
        //调用所有障碍物和敌人的重置方法
        for(int i=0;i<this.allEnemy.size();i++){
            this.allEnemy.get(i).reset();
        }
        for(int i=0;i<this.allObstruction.size();i++){
            this.allObstruction.get(i).reset();
        }
    }

    //getter
    public BufferedImage getBgImage() {
        return bgImage;
    }

    public List<Obstruction> getAllObstruction() {
        return allObstruction;
    }

    public List<Obstruction> getRemovedObstruction() {
        return removedObstruction;
    }

    public int getSort() {
        return sort;
    }

    public List<Enemy> getAllEnemy() {
        return allEnemy;
    }

    public List<Enemy> getRemovedEnemy() {
        return removedEnemy;
    }

    public boolean isFlag() {
        return flag;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    //使敌人开始移动{
    public void enemyStartMove(){
        for(int i=0;i<this.allEnemy.size();i++){
            this.allEnemy.get(i).startMove();
        }
    }
}
