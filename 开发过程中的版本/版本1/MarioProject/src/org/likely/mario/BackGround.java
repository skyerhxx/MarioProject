package org.likely.mario;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //当前场景的显示图片
    private BufferedImage bgImage = null;

    //getter
    public BufferedImage getBgImage() {
        return bgImage;
    }


    //场景的顺序
    private int sort;
    //当前是否为最后一个场景
    private boolean flag;
    //通过集合来保存
    //全部的敌人
    private List allEnemy = new ArrayList();
    //全部的障碍物
    private List<Obstruction> allObstruction = new ArrayList<Obstruction>();
    //被消灭的敌人
    private List removedEnemy = new ArrayList();
    //被消灭的障碍物
    private List removedObstruction = new ArrayList();


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
            //建立整个地面
            for(int i=0; i<15 ;i++){
                this.allObstruction.add(new Obstruction(i*60,540,9));
            }


        }
    }
    //重置方法
    //将所有障碍物和敌人返回到原有坐标，并将其状态也修改
    public void reset(){

    }


}
