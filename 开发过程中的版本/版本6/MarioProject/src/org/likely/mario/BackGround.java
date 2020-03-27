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
    //通过集合来保存
    //全部的敌人
    private List allEnemy = new ArrayList();
    //全部的障碍物
    private List<Obstruction> allObstruction = new ArrayList<Obstruction>();

    public List<Obstruction> getAllObstruction() {
        return allObstruction;
    }

    //被消灭的敌人
    private List removedEnemy = new ArrayList();
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
                this.allObstruction.add(new Obstruction(i*60,540,9));
            }
            //绘制砖块和问号
            this.allObstruction.add(new Obstruction(120,360,4));
            this.allObstruction.add(new Obstruction(300,360,0));
            this.allObstruction.add(new Obstruction(360,360,4));
            this.allObstruction.add(new Obstruction(420,360,0));
            this.allObstruction.add(new Obstruction(480,360,4));
            this.allObstruction.add(new Obstruction(540,360,0));
            this.allObstruction.add(new Obstruction(420,180,4));
            //绘制水管
            this.allObstruction.add(new Obstruction(660,540,6));
            this.allObstruction.add(new Obstruction(720,540,5));
            this.allObstruction.add(new Obstruction(660,480,8));
            this.allObstruction.add(new Obstruction(720,480,7));
        }
        //绘制第二个场景
        if(sort == 2){
            //绘制地面
            for(int i=0; i<15 ;i++) {
                if (i != 10) {
                    this.allObstruction.add(new Obstruction(i * 60, 540, 9));
                }
            }
            //绘制水管
            this.allObstruction.add(new Obstruction(60,540,6));
            this.allObstruction.add(new Obstruction(120,540,5));
            this.allObstruction.add(new Obstruction(60,480,6));
            this.allObstruction.add(new Obstruction(120,480,5));
            this.allObstruction.add(new Obstruction(60,420,8));
            this.allObstruction.add(new Obstruction(120,420,7));

            this.allObstruction.add(new Obstruction(240,540,6));
            this.allObstruction.add(new Obstruction(300,540,5));
            this.allObstruction.add(new Obstruction(240,480,6));
            this.allObstruction.add(new Obstruction(300,480,5));
            this.allObstruction.add(new Obstruction(240,420,6));
            this.allObstruction.add(new Obstruction(300,420,5));
            this.allObstruction.add(new Obstruction(240,360,8));
            this.allObstruction.add(new Obstruction(300,360,7));
        }
    }
    //重置方法
    //将所有障碍物和敌人返回到原有坐标，并将其状态也修改
    public void reset(){

    }

    //getter
    public BufferedImage getBgImage() {
        return bgImage;
    }

    public List<Obstruction> getRemovedObstruction() {
        return removedObstruction;
    }

    public int getSort() {
        return sort;
    }
}
